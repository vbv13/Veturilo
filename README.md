# VeturiloBike

How to start the VeturiloBike application
---

1. Run `mvn clean install` to build your application
2. Start application with `java -jar target/dropwizard.bike-1.0-SNAPSHOT.jar server config.yml`
3. To check that your application is running enter url `http://localhost:8080`


How to start the VeturiloBike frontend application
---

1. Go to client directory
2. Run `npm install`
3. Run `npm start`
4. React application should be running at `http://localhost:3000/`

To correct working of app(showing stations with graphs) recommended is having installed and enabled plug into browser: Allow CORS: Access-Control-Allow-Origin.


Mock of view is in file mockOfView.jpg in main directory.
Screen from view of the app is available in screenFromViewOfApp.jpg.

Description
---
Application consist of four veturilo stations. We are showing to the user name of the station, amount of free bikes available at it and also graph which show amount of available bikes from time. At the right side we are sharing with user map of stations from veturilo service.
View of the app was build on React. Graphs were build by using CanvasJS.
Data share to the frontend was served by backend through cyclic polling.
Backend stack: Java, Dropwizard, PostgreSQL.

Running mock
---

1. Import mock for example into SoapUI. From veturilo\client\backend directory.
2. Run it. It's running on port 8089. So in veturilo\client\src\StationsAPI.js change const api = "http://localhost:8080" to 8089.
3. Run React app. And see the results on the http://localhost:3000