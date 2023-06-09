### PR
compile and run test
```bash
$ mvn clean package assembly:single
```  

run (default: localhost 8080)
```bash
$ java -jar target/pr1-with-deps.jar
```  

run with parameters
```bash
$ java -Dcom.nolano.pr1.ipaddress=localhost -Dcom.nolano.pr1.port=8080 -Dcom.nolano.pr1.threads=10 -jar target/pr1-with-deps.jar
```  

run jmh benchmark
```bash
$  java -cp target/pr1-with-deps.jar org.openjdk.jmh.Main
........
Result "com.nolano.pr1.peformance.EngineV1Benchmark.testPerformance":
  1801.176 ±(99.9%) 34.839 ops/s [Average]
  (min, avg, max) = (1235.066, 1801.176, 2017.077), stdev = 147.509
  CI (99.9%): [1766.337, 1836.014] (assumes normal distribution)


# Run complete. Total time: 00:06:44

Benchmark                     Mode  Cnt     Score    Error  Units
MyBenchmark.testPerformance  thrpt  200  1801.176 ± 34.839  ops/s
```  
### Rest API
```bash
$ curl http://127.0.0.1:8080/point -d '{"x":"455456", "y":"456"}'  # Add a point to the space
$ curl http://127.0.0.1:8080/lines/2 # Get all line segments passing through at least N points
$ curlhttp://127.0.0.1:8080/space # Get all points in the space
$ curl -X DELETE http://127.0.0.1:8080/space # Remove all points from the space
```  

### Logging configuration
just edit the file:
```bash
vi src/main/resources/simplelogger.properties
```  



