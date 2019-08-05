

### Running the application

Run the following command to serve the application with default values (from `localhost:8080`)

    mvn spring-boot:run
    
You can add 2 optional parameters to change port and path

    --server.port=9090
    --server.servlet.context-path=/nurullah
    
You can add these as run arguments as follows

    nurullah$ mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=9090,--server.servlet.context-path=/nurullah

which (if both are provided) will server the application from `localhost:9090/nurullah`
   
### Health Check

Health check can be made via;

    localhost:8080/actuator/health

### Metrics 

Available metrics can be seen under

    localhost:8080/actuator/metrics
    
Then each metric can be monitored via;

    localhost:8080/actuator/metrics/{METRIC_NAME}

#### Running tests

    mvn test
