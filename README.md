# highchart-dropwizard-template

A simple project which stores line-chart data in MySql and displays it using HighCharts and JQuery. It assumes you are running MySql locally and provides a script to populate the DB with dummy data. Db connection info is in the app.yml file. The backend is Dropwizard and serves json responses to the frontend via REST.

build:<br />
`mvn clean package`

start:<br/>
`./scripts/start.sh`<br/>
`./scripts/populateSeedData.sh`


REST endpoint:<br/>
<http://localhost:8080/line-chart?name=line-chart-1&after=2016-08-01&before=2016-08-31>

HighCharts:<br/>
<http://localhost:8080/charts>

