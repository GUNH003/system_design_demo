# Load Test

## Test Endpoints
Apache JMeter is used to evaluate the performance of four GET API endpoints under simulated load conditions. Each test produced detailed performance metrics and HTML reports.

* GET `/resorts/{resortID}/seasons/{seasonID}/day/{dayID}/skiers`  
  Get number of unique skiers at a given resort on a spefici day in a specific season.  
* GET `/skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}`  
  Get the ski day vertical for a skier at a given resort in a specific season.  
* GET `/skiers/{skierID}/vertical?resort={resortID}`  
  Get the total vertical for the skier at the specified resort for all seasons.   
* GET `/skiers/{skierID}/vertical?resort={resortID}&season={seasonID}`  
  Get the total vertical for the skier for a specified season at the specified resort.  

## Test Configuration
Each endpoint was tested using a dedicated JMeter Thread Group with the following parameters:
* Number of Threads (Users): 128
* Ramp-Up Period: 10 seconds
* Loop Count (Iterations): 500

Refer to the LiftRideGetTest.jmx file for detailed configuration.

**Note:** The default configuration targets localhost. For cloud or distributed deployment, update the server URL and port in the .jmx file accordingly.