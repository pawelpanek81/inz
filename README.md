# Web application supporting car servicing

## Running locally:

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
127.0.0.1 db-postgres-account-service
127.0.0.1 db-postgres-map-service
```
append database domain names to ip:
```
192.168.99.100 db-postgres-account-service
192.168.99.100 db-postgres-map-service
```

start databases in docker:
```cmd
docker-compose run -p 5000:5432 db-postgres-account-service
docker-compose run -p 5001:5432 db-postgres-map-service
```

Firstly Maven clean and install jwt-config/pom.xml,

Then compile modules, and start in order:
1. config-server
1. eureka-server
1. zuul-server
1. others

#### Execute independent of local or docker environment
start rabbitmq in docker:
```cmd
docker-compose up rabbitmq
```

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

### Databases:
| Database name | Docker host | Docker-Compose Exposed Port | User | Password | Docker service name |
| --- | --- | --- | --- | --- | --- |
| account-service-db | 5432 | 5000 | account-db-user | toor | db-postgres-account-service |
| map-service-db | 5432 | 5001 | map-db-user | toor | db-postgres-map-service |
