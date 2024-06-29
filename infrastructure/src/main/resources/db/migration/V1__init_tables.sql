CREATE SEQUENCE t_limits_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE t_taken_temperature_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE t_limits (
    id integer NOT NULL,
    coldlimit integer,
    warmlimit integer
);

CREATE TABLE t_taken_temperature (
    id integer NOT NULL,
    takenat timestamp with time zone,
    takentemperature integer
);

ALTER TABLE ONLY t_limits
ADD CONSTRAINT t_limits_pkey PRIMARY KEY (id);

ALTER TABLE ONLY t_taken_temperature
ADD CONSTRAINT t_taken_temperature_pkey PRIMARY KEY (id);
