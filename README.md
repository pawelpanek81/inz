# Web application supporting car servicing

## Running in maven:

### set profiles in VM options:
```
-Dspring.profiles.active=dev
```

### Alternatively:

### edit hosts:
Open `C:/Windows/System32/drivers/etc/hosts`

append:
```
127.0.0.1 config-server
127.0.0.1 eureka-server
127.0.0.1 auth-server
127.0.0.1 zuul-server
127.0.0.1 account-service
127.0.0.1 db-postgres-account-service
127.0.0.1 db-postgres-map-service
```

you can also run databases in docker:
```
192.168.99.100 db-postgres-account-service
192.168.99.100 db-postgres-map-service
```

start databases in docker:
```cmd
docker-compose run -p 5000:5432 db-postgres-account-service
```

Compile all modules, and start in order:
1. config-server
1. eureka-server
1. zuul-server
1. others

## Running in docker:
fire command:
```cmd
docker-compose up -d
```
and wait few minutes

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
| Database name | Docker host | User | Password | Docker service name |
| --- | --- | --- | --- | --- |
| account-service-db | 5432 | account-db-user | toor | db-postgres-account-service |
| map-service-db | 5432 | map-db-user | toor | db-postgres-map-service |
