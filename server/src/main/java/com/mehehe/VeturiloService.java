package com.mehehe;

import com.google.gson.Gson;
import com.mehehe.model.BikeHistory;
import com.mehehe.model.Station;
import com.mehehe.model.StationHistory;
import com.mehehe.model.gson.Place;
import com.mehehe.model.gson.Region;
import com.mehehe.repository.VeturiloRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VeturiloService {

    private final VeturiloRepository veturiloRepository;

    VeturiloService(VeturiloRepository veturiloRepository) {
        this.veturiloRepository = veturiloRepository;
    }

    public Optional<Station> getStation(Integer id) {
        return veturiloRepository.findOne(id);
    }

    public List<Station> getStations() {
        return veturiloRepository.findAll();
    }

    public void reloadStations() {
        Gson gson = new Gson();
        Region[] regions = gson.fromJson(getJsonContentFromWeb(), Region[].class);
        Place[] places = regions[0].getPlaces();
        List<Station> stations = Stream
            .of(places)
            .map(Station::new)
            .sorted(Comparator.comparing(x -> x.name))
            .collect(Collectors.toList());
        veturiloRepository.save(stations);
    }

    private String getJsonContentFromWeb() {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            url = new URL("https://www.veturilo.waw.pl/mapa-stacji/?a=" + LocalDateTime.now());
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ioe) {
                // nothing to see here
            }
        }
        String toString = sb.toString();
        int begin = toString.indexOf("var NEXTBIKE_PLACES_DB = '");
        int end = toString.indexOf("</script>", begin);
        return toString.substring(begin + 26, end - 6);
    }

    public String getStationHistoryAsString(Integer id) {
        return veturiloRepository.findOneStationHistory(id)
                .map(StationHistory::toString)
                .collect(Collectors.joining("\n"));
    }

    public Stream<StationHistory> getStationHistory(Integer id) {
        return veturiloRepository.findOneStationHistory(id);
    }

    public Stream<BikeHistory> getBikeHistory(Integer id) {
        return veturiloRepository.findOneBikeHistory(id);
    }
}
