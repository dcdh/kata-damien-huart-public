#!/bin/sh
./mvnw package -Dnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true
