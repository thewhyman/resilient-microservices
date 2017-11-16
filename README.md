# Resilient and Self-healing Cloud Native Microservices Architecture

Building microservices offers great benefits to corporations; however, this comes at a cost of increased operational complexity. Discover how to build resilience into your microservices through the automation of scaling, load balancing, throttling and failure handling using the following technologies and patterns:
 
* PaaS - Pivotal Web Services Platform (PWS)
* API Gateway, Edge Service, Backends for Frontends (BFF) - Netflix Zuul
* REST Client - Feign (with Hateoas enabled)
* Service Registy - Netflix Eureka, Ribbon
* Circuit Breaker - Netflix Hystrix and Turbine

## Architecture
 
![Architecture](Architecuture.png)

![Architecture Resilience](Architecture%20Resilience.png)

## Getting Started

Clone this project into a directory.

### Prerequisites

Need to create an account with Pivotal Web Services.

* [Pivtoal Web Services (PWS)](https://run.pivotal.io) - Cluoud Foundry PaaS.

* Log in though UI and download and install CF CLI.
* Create an ORG and a Space.
* Go to Marketplace and create following services in your space with these names: service-registry, circuit-breaker-dashboard, app-autoscaler 
* Configure app-auotscaler to scale based on CPU (5%-10%) - min 1 and max 4 instances.

* [Blaze Meter](http://www.blazemeter.com) - URL/API Test.

To Run software locally, run eureka and hystrix

```
$ java -jar hystrix-dashboard/target/hystrix-dashboard-1.0.0-SNAPSHOT.jar
```
```
$ java -jar /eureka-server/target/eureka-server-1.0.0-SNAPSHOT.jar
```

### Installing

* Build 
```
$ mvn clean install
```

* Log in into Pivotal Web Service and target your space.
* Deploy
```
$ cf push
```
[NOTE] PWS wouldn't allow same routes to be bound for 2 different application. You may have to change the name of the applications in manifest.yml. Howerver, you don't have to change the name of apps as they are discovered automatically by Service Registry.

## Running the tests

* Call Stock Broker API Gateway
```
$ curl https://stock-broker-api-gateway.cfapps.io/bff/portfolio/
```

* Call Stock Broker Mobile BFF 
```
$ curl https://stock-broker-mobile-bff.cfapps.io/portfolio/AAPL
```

* Call Stock Market Data Service 
```
$ curl https://stock-market-data-service.cfapps.io/stocks
```

* Destroy Stock Market Data Service. Destroys a single instance that handled the request. Watch to see the instance being recreated by PWS.
```
$ curl https://stock-market-data-service.cfapps.io/destroy
```

* Stop Stock Market Data Service. Stops all instances.
```
$ cf stop stock-market-data-service
```

* While Stock Market Data Service is down, call Stock Broker API Gateway. Stock Broker Mobile BFF cirucuit breaker opens and get results from cache. Watch "lastUpdatedTime", it stays same. 
```
$ curl https://stock-broker-api-gateway.cfapps.io/bff/portfolio/
```

* Start Stock Market Data Service. Stops all instances.
```
$ cf start stock-market-data-service
```

* When the Stock Market Data Service comes online, Stock Broker Mobile BFF cirucuit breaker closes and gets latest stock info. "lastUpdatedTime" should update every 5 seconds.
```
$ curl https://stock-broker-api-gateway.cfapps.io/bff/portfolio/
```

*  Check out Actuator endpoints on all these services. You can get Hystrix stream url there to input into Hystrix Dashboard.
```
$ curl https://stock-broker-api-gateway.cfapps.io/actuator
```

* Run Load test in API gateway

Use [Blaze Meter](http://www.blazemeter.com) or run

```
$ while true; do https://stock-broker-api-gateway.cfapps.io/bff/portfolio/; done
```

## Deployment

All applications are deployed to Pivtoal Web Services

* [Pivtoal Web Services](https://run.pivotal.io) - Cluoud Foundry PaaS.
* [CF CLI](https://github.com/cloudfoundry/cli) - Deploy artifacts.


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Anand Vallamsetla** - *Initial work* - [Memeinstigator](https://github.com/memeinstigator)


## Acknowledgments

* 12factors.net

