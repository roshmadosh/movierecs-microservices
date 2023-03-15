# Microservices for Movie Recommendations Application  

Spring Boot microservices for a movie recommendations application.  

### Service Discovery Microservice  

`discovery-service/` is the service discovery microservice, and is implemented as a [Spring Cloud Netflix](https://cloud.spring.io/spring-cloud-netflix/reference/html/) Eureka server. If running locally, you must run this application first.  

### Fetch Microservice
`fetch-service/` makes 3rd party API calls to the [The Movie Database](https://www.themoviedb.org/documentation/api?language=en-US) API.  

An API token for TMDB must be set as an environment variable for this service to work properly.  

This service also makes use of the [Spring Cloud Azure](https://learn.microsoft.com/en-us/azure/developer/java/spring-framework/developer-guide-overview) dependencies. You must be logged in through the Azure CLI to read and write files to the blob storage containers. 