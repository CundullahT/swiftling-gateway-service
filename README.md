# SwiftLing App - Spring Cloud Gateway with Keycloak Authentication

## Overview
This project is a Spring Cloud Gateway application that routes API requests to SwiftLing applications various microservices registered in a Netflix Eureka service registry. It utilizes Keycloak for OAuth2 authentication and integrates with other services like Zipkin for tracing and Spring Cloud Config for configuration management.

## Prerequisites
Ensure the following dependencies are installed and configured:
- **Java 21 or later**
- **Maven 3.9+** (compatible with Java 21)
- **Docker** (for running Zipkin and Keycloak via containers)
- **Docker Compose** (for running Keycloak if using standalone Docker installations)
- **Keycloak Server** (OAuth2 Provider) (Keycloak Docker Compose Example = https://github.com/CundullahT/swiftling-keycloak-docker-compose.git)
- **Netflix Eureka Server** (Service Discovery) (Example Eureka Server Repository = https://github.com/CundullahT/swiftling-discovery-service.git)
- **Spring Cloud Config Server** (Configuration Management) (Example Config Server Repository = https://github.com/CundullahT/swiftling-config-service.git)
- **Zipkin Server** (Distributed Tracing) **NOTE:** You can find the needed Docker command at the end of this document.

## Environment Variables
The following environment variables must be set for the application to function properly:

| Variable Name                 | Description                                                                          |
|-------------------------------|--------------------------------------------------------------------------------------|
| `KEYCLOAK_SERVICE`            | Base URL of the Keycloak server (e.g., `http://localhost:8080`)                      |
| `LOG_TRACE_SERVICE`           | Base URL of the Zipkin server (e.g., `http://localhost:9411`)                        |
| `SWIFTLING_CONFIG_SERVICE`    | Base URL of the Spring Cloud Config Server (e.g., `http://localhost:8888`)           |
| `SWIFTLING_DISCOVERY_SERVICE` | Base URL of the Eureka server (e.g., `http://localhost:8761`)                        |
| `SWIFTLING_PROFILE`           | Active Spring profile (e.g., `local`, `dev`, `prod`)                                 |
| `ENV`                         | The environment in which the application is running (e.g., `local`, `dev`, `prod`).  |

## Running the Application
1. Clone the repository:
   ```sh
   git clone <repository_url>
   cd <project_directory>
   ```
2. Set the required environment variables (example for Linux/macOS):
   ```sh
   export KEYCLOAK_SERVICE=your_keycloak_url
   export LOG_TRACE_SERVICE=your_zipkin_url
   export SWIFTLING_CONFIG_SERVICE=your_config_server_url
   export SWIFTLING_DISCOVERY_SERVICE=your_eureka_server_url
   export SWIFTLING_PROFILE=dev
   export ENV=dev
   ```
   For Windows (Command Prompt):
   ```cmd
   set KEYCLOAK_SERVICE=your_keycloak_url
   set LOG_TRACE_SERVICE=your_zipkin_url
   set SWIFTLING_CONFIG_SERVICE=your_config_server_url
   set SWIFTLING_DISCOVERY_SERVICE=your_eureka_server_url
   set SWIFTLING_PROFILE=dev
   set ENV=dev
   ```
3. Build the project using Maven:
   ```sh
   mvn clean package
   ```
4. Run the Spring Cloud Gateway application:
   ```sh
   java -jar target/*.jar
   ```
   OR using Spring Boot:
   ```sh
   mvn spring-boot:run
   ```

## API Gateway Routing
The API Gateway routes all requests to microservices registered in Eureka. The requests must include the microservice name in the path, followed by the actual service endpoint. Example:
- **User Service**: `/swiftling-user-service/api/v1/account/signup`
- **Phrase Service**: `/swiftling-phrase-service/api/v1/phrase/add-phrase`
- **Quiz Service**: `/swiftling-quiz-service/api/v1/quiz/result`
- **Stats Service**: `/swiftling-stats-service/api/v1/stats/daily-streak`

## Swagger API Documentation
This application provides a consolidated Swagger documentation page that aggregates all microservice API docs:
- **Swagger UI**: `http://localhost:8762/swagger-ui.html`
- **API Docs**: `http://localhost:8762/api-docs`

## Default Configuration
- The gateway runs on **port 8762**
- Uses **OAuth2 authentication via Keycloak**
- Routes requests dynamically based on Eureka-registered microservices
- Integrates with Zipkin for tracing

## Additional Information
- This application is built using **Spring Boot 3.4.4**
- Ensure that all dependent services (Keycloak, Eureka, Config Server, Zipkin) are running before starting the gateway.
- Logs and tracing information will be sent to Zipkin if configured correctly.
- The authentication flow follows OAuth2, and access tokens must be included in requests for authentication.
- To run Zipkin using Docker, execute the following command:
  ```sh
  docker run -d -p 9411:9411 openzipkin/zipkin
  ```
- For an example **Keycloak** setup using Docker Compose, refer to this private repository:
  https://github.com/CundullahT/swiftling-keycloak-docker-compose.git
- The Gateway may take some time to retrieve and display the Swagger docs from microservices.

## License
This project is licensed under [MIT License](LICENSE).
