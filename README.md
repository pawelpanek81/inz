# Web application supporting car servicing

You can run this application in either local or docker environment.
This document contains instructions how to run application in local and docker environment.
Running in local environment can be done either at the "default" or "dev" profile in Spring.

a) Running in dev profile sets the Config and Eureka server to 127.0.0.1.
It also runs application with H2 embedded database, so you don't have to run external PostgreSQL servers.

b) Running in default profile has similar settings as in docker, so it is good to test before deploying on docker. To run in default profile you have to run databases in docker and edit hosts file to provide proper dns.


## Running locally:

#### Execute regardless of the profile
start rabbitmq in docker:
```cmd
docker-compose up rabbitmq
```

#### Dev profile:
Set dev profile in VM options for each microservice:
```
-Dspring.profiles.active=dev
```

#### Default profile:
Edit hosts - open `C:/Windows/System32/drivers/etc/hosts`

append microservices domain names to ip:
```
127.0.0.1 config-server
127.0.0.1 eureka-server
127.0.0.1 auth-server
127.0.0.1 zuul-server
127.0.0.1 account-service
127.0.0.1 map-service
127.0.0.1 notification-service
127.0.0.1 technical-examination-service
127.0.0.1 car-service
# uncomment databases if running locally, not in docker
# 127.0.0.1 db-postgres-account-service
# 127.0.0.1 db-postgres-map-service
# 127.0.0.1 db-postgres-notification-service
# 127.0.0.1 db-postgres-technical-examination-service
# 127.0.0.1 db-postgres-car-service
```
append database domain names to ip:
```
192.168.99.100 db-postgres-account-service
192.168.99.100 db-postgres-map-service
192.168.99.100 db-postgres-notification-service
192.168.99.100 db-postgres-technical-examination-service
192.168.99.100 db-postgres-car-service
```

start databases in docker:
```cmd
docker-compose run -p 5000:5432 db-postgres-account-service
docker-compose run -p 5001:5432 db-postgres-map-service
docker-compose run -p 5002:5432 db-postgres-notification-service
docker-compose run -p 5003:5432 db-postgres-technical-examination-service
docker-compose run -p 5004:5432 db-postgres-car-service
```

#### Instructions
Firstly Maven clean and install jwt-config/pom.xml,

Then compile modules, and start in order:
1. config-server
1. eureka-server
1. zuul-server
1. others


## Running in docker:
Fire command:
```cmd
docker-compose up -d
```
and wait few minutes

To remove containers:
```cmd
docker-compose down
```

## Information's
### Services and servers ports:
| Name | Port | Application Name |
| --- | --- | --- |
| Config Server | 9090 | config-server |
| Eureka Server (Discovery) | 9091 | eureka-server |
| Zuul Server (API Gateway) | 9092 | zuul-server |
| Auth Server (JWT gen.) | 9093 | auth-server |
| Map Service | 8082 | map-service |
| Account Service | 8083 | account-service |
| Notification Service | 8084 | notification-service |
| Technical Examination Service | 8085 | technical-examination-service |
| Car Service | 8086 | car-service |

### Databases:
| Database name | Docker host | Docker-Compose Exposed Port | User | Password | Docker service name |
| --- | --- | --- | --- | --- | --- |
| account-service-db | 5432 | 5000 | account-db-user | toor | db-postgres-account-service |
| map-service-db | 5432 | 5001 | map-db-user | toor | db-postgres-map-service |
| notification-service-db | 5432 | 5002 | notification-db-user | toor | db-postgres-notification-service |
| technical-examination-service-db | 5432 | 5003 | technical-examination-db-user | toor | db-postgres-technical-examination-service |
| car-service-db | 5432 | 5004 | car-db-user | toor | db-postgres-car-service |

### RabbitMQ:
| Docker service name | Docker Port | Docker-Compose Exposed Port | 
| --- | --- | --- | 
| rabbitmq | 5672 | 5672 | 
| | 15672 | 15672 | 