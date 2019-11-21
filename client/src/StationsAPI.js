const api = "http://localhost:8080"

const headers = {
    'Accept': 'application/json'
}

//http://localhost:8089/station/selected/1,2,3
//http://localhost:8089/station/6445/graph-data
//http://localhost:8089/station/6445/free-bikes
//http://localhost:8089/station/selected/1,2,3

export const getSelected = (stationsId) =>
    fetch(`${api}/station/selected/${stationsId}`, { headers })
        .then(res => res.json())
        

export const graphData = (stationId) => 
    fetch(`${api}/station/${stationId}/graph-data`, { headers })
        .then(res => res.json())
        

export const freeBikes = (stationId) => 
    fetch(`${api}/station/${stationId}/free-bikes`, { headers })
        .then(res => res.json())
        