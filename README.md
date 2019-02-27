# Customer Service
The customer service integrates with CRM system and provides api to create, update and delete a customer record at CRM system.

## Local setup
 1. Clone the code to a local directory and run `mvn clean install`. Alternatively, you could update the `maven-settings.xml` 
 and run `mvn clean install -s maven-settings.xml`.
 2. Run the application with `java -jar target/sample-customer-service.jar`. The application starts on port 8080 by default. 
    This can be changed to port ex:9999 by adding a command line parameter `--server.port=9999` to the above `java` command.

## Swagger
The swagger documentation can be accessed at `http://localhost:8080/swagger-ui.html`

## Design
 1. Resource first api design ex: `/cities` will return a collection of cities and `/city/{code}` will return a city. 
 1. HAL formatted responses to enable easy navigation. 
 1. Pagination for collections to support mobile and desktop.
 1. Consistent errors to enable meaningful errors messages and during microservice interaction. 
 1. Domain Driven design - microservices respecting business bounded contexts.

## Security
The current api does not secure the services. Spring security along with either json webtokens or OAuth2 based security mechanism 
along with the relevant infrastructure can be designed and implemented as outlined in future design considerations.

## Future Design considerations
 1. *Config server* to store all the configurations.
 2. *Reverse Proxy* such as Zuul to accommodate for security check layer, any encryption/decryptions for request/response or values from config server etc.   
 3. *Security* OAuth2 security handling service where microservice can include/exclude the resources that are to be secured.
 4. *Service discovery* such as Eureka as the microservice achitecture grows.
 5. Reactive microservices according to the architecture.

