#!/bin/sh
./mvnw package -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true