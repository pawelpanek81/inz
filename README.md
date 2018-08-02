# Web application supporting car servicing

## Running in maven:
### Prerequirements:
Open `C:/Windows/System32/drivers/etc/hosts`

append:
```
127.0.0.1 config-server
127.0.0.1 eureka-server
127.0.0.1 auth-server
127.0.0.1 zuul-server
192.168.99.100 db-postgres-account-service
```

Compile all modules, and start in order:
1. config-server
1. eureka-server
1. zuul-server
1. others

## Running in docker:
```cmd
docker-compose up -d
```