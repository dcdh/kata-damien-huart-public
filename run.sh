#!/bin/sh
docker-compose -f docker-compose-run.yaml up && docker-compose -f docker-compose-run.yaml rm --force