# Infra

## Postgres

### dump Postgres
> pg_dumpall -U username

### connection db
> psql -U username sensor

### ZonedDateTime

> Hibernate will generate a column having this property `timestamp without time zone`.
> 
> **FIX** define it manually to `timestamp with time zone` in flyway script
> https://stackoverflow.com/a/44485311/2570225
