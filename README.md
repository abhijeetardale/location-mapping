This is front end microservice api which is making call to user api.
This service check the uses location using "haversine formula" to calculate the distance from London.

You can run this service using sbt, follow below steps:
1. git clone project
2. go to the project folder (cd location-mapping)
3. sbt run
4. go to browser and use 'localhost:9000/'
5. It will display the result

Test coverage report:
scoverage plugin is used to calculate unit test coverage which can be verified by executing./testCoverage.sh

The latest coverage report is as follows:

[info] Statement coverage.: 97.13%

[info] Branch coverage....: 100.00%

[info] Coverage is above minimum [97.13% > 80.0%]


Reference:
http://www.arubin.org/files/geo_search.pdf

https://www.movable-type.co.uk/scripts/latlong.html
