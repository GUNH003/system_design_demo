# System Design Demo
This demo showcases a high-throughput, horizontally scalable system implementing a **write-aside cache** architecture. The Servlet serves as an API endpoint, conforming to the **UPIC SkiDataAPI (v2.0)** (`https://app.swaggerhub.com/apis/cloud-perf/SkiDataAPI/2.0`), supporting both POST and GET operations for skier lift ride data.
## Key Features
* High-throughput writes using an asynchronous Producer-Consumer pattern via RabbitMQ.
* Decoupled architecture allowing for horizontal scalability.
* Fast GET responses through a read-what-you-write caching strategy with fallback to MongoDB.
* Improved consistency with optional leader-based MongoDB cluster for stronger write guarantees.
## Setup Instructions
### I. Local Deployment Setup
1. Start services on localhost:
* Start Redis, RabbitMQ and MongoDB services on localhost.
* Start `Servlet` and `MQConsumer`.
* Run `PostClent`.
2. Configuration:
* The default setup is for local environment. Update all TODO fields with approriate local values.
### II. Cloud Deployment Setup
1. Provision and deploy the following components:
* Application Load Balancer (ALB) for routing.
* Servlet instances on Tomcat servers, and add them to ALB target group if applicable.
* Dedicated Redis instance.
* Dedicated RabbitMQ instance.
* Dedicated MQConsumer instance.
* MongoDB instances (standalone or clustered).
2. Configurations:
Ensure all all TODO fields are correctly updated:  
* **Servlet**  
    * MongoDB settings (`MongoPool`): URL, database name, and collection name.
    * RabbitMQ settings (`RabbitmqPool`): exchange name, routing key, queue name, and URI string.
    * Redis settings (`RedisPool`): host and port.
2. **PostClient**
    * Target URL (e.g., Tomcat server or ALB) in `SimulationRunner`.
3. **MQConsumer**
    * MongoDB, Redis, and RabbitMQ settings in `ConsumerRunner`.
## Running Instructions
1. Ensure RabbitMQ, Redis and MongoDB services are running.
2. Verify configuration parameters in `PostClient`, `Servlet`, and `MQConsumer`. 
3. Start `MQConsumer`.  
4. Start `PostClient`.  
## Testing
### POST requests
Execution statistics are logged in `./logs` directory of `PostClient`.
### GET requests
Use the provided **JMeter** `.jmx` script in the `./Testing` directory to benchmark GET performance.
