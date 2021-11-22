# How to

## Jvm build and run

1. run to prepare application containers `docker pull registry.access.redhat.com/ubi8/ubi-minimal:8.4 && docker pull debezium/postgres:11-alpine`
1. run `mvn clean install verify` to build everything;
1. run `docker build -f infrastructure/src/main/docker/Dockerfile.jvm -t com.harvest.katadamienhuart/sensor-jvm-service infrastructure/` to build docker image
1. run `docker-compose -f docker-compose-jvm-run.yaml up && docker-compose -f docker-compose-jvm-run.yaml rm --force` to start the stack and next remove container after
1. access swagger ui via `http://0.0.0.0:8080/q/swagger-ui/`

## Native build and run

1. run to prepare application containers `docker pull quay.io/quarkus/quarkus-distroless-image:1.0 && docker pull debezium/postgres:11-alpine`
1. run `mvn clean install verify -P native` to build everything;
1. run `docker build -f infrastructure/src/main/docker/Dockerfile.native-distroless -t com.harvest.katadamienhuart/sensor-native-service infrastructure/` to build docker image
1. run `docker-compose -f docker-compose-native-run.yaml up && docker-compose -f docker-compose-native-run.yaml rm --force` to start the stack and next remove container after
1. access swagger ui via `http://0.0.0.0:8080/q/swagger-ui/`