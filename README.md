# Couchbase Java Multi Cluster Integration Demo

This project demonstrates the integration of the Java MCA library with spring / spring boot.

The "glue" code is found under the `internal` package, but the rest under the main `com.couchbase.spring.mca` is just 
application level code that can be customized.

The code demos how to use both the template and the repository to perform operations which are
backed by the underlying MCA library. Coordinator and failure detector can (and should) be customized
in the `MultiClusterDatabaseConfiguration` class.

```java
@Configuration
public class MultiClusterDatabaseConfiguration extends AbstractMultiClusterConfiguration {

  @Bean
  @Override
  public String bucketName() {
    return "travel-sample";
  }

  @Override
  public String userName() {
    return "Administrator";
  }

  @Override
  public String userPass() {
    return "password";
  }

  @Bean
  @Override
  public Coordinator coordinator() {
    return Coordinators.isolated(new IsolatedCoordinator.Options());
  }

  @Bean
  public FailureDetectorFactory<? extends FailureDetector> failureDetectorFactory() {
    return FailureDetectors.nodeHealth(coordinator(), NodeHealthFailureDetector.options());
  }

}
```

the beans can then be autowired as usual:

```java
@SpringBootApplication
@Controller
public class Application {

  @Autowired
  private CouchbaseTemplate template;

  @Autowired
  private AirportRepository repository;

  @GetMapping("/airports/{id}")
  public ResponseEntity<Airport> read(@PathVariable String id) {
    Airport airport =  template.findById("airport_" + id, Airport.class);
    return new ResponseEntity<>(airport, HttpStatus.OK);
  }

  @GetMapping("/airports")
  public ResponseEntity<Iterable<Airport>> readAll() {
    return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
```