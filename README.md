This is front end microservice api which is making call to user api.
This service check the uses location using "haversine formula" to calculate the distance from London.

You can run this service using sbt, follow below steps:
1. git clone project
2. go to the project folder (cd location-mapping)
3. sbt 'run 8890'
4. go to browser and use 'localhost:8890/'
5. It will display the result

Reference:
http://www.arubin.org/files/geo_search.pdf
