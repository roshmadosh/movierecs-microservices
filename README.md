# Microservices for Movie Recommendations Application  

Spring Boot microservices for a movie recommendations application.  

**Note**: All POST requests must be in snake case.
### Service Discovery Microservice  

`discovery-service/` is implemented as a [Spring Cloud Netflix](https://cloud.spring.io/spring-cloud-netflix/reference/html/) Eureka server. If running locally, you must run this application first.  

### Assets Microservice  

`assets-service/` makes 3rd party API calls to the [The Movie Database](https://www.themoviedb.org/documentation/api?language=en-US) API and saves contents to a database.  

TMDB doesn't accept batch requests for movie details, which is why an extra storage step (database) is included.  

The TMDB API key is stored as a local environment variable, under `TMDB_API_KEY`.  

Note that this service might not be able to read environment variables via your IDE. If that's the case, run `mvn spring-boot:run` from the `assets-service/` directory.  

### ML Microservice    

`ml-microservice/` has a `/predict` endpoint that forwards the necessary prediction parameters to the Python REST API that hosts the ML model.  

### Users Microservice  

`users-microservice` is the interface for managing user data, including "favorite" movies used for performing the personalized recommendation.  

### Admin Microservice

`admin-microservice` is a secured endpoint that includes admin-level endpoints. Used for functions such as adding a user to the Users microservice.  

This service makes `RestTemplate` calls to the dockerized Keycloak server. **The auth token must be retrieved from the Docker host ip.** This is because the injected host name for the RestTemplate request appears to only take literal values (i.e. injecting the name of the Docker container doesn't seem to work).
### Gateway Microservice
`gateway-resource-microservice` is a Spring Cloud Gateway app and routes + load-balances requests to the other microservices. It's also an OAuth client (easier to forward Auth token) and resource server (so that auth token required to use gateway). 