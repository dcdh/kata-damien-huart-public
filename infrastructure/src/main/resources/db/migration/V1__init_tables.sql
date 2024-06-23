CREATE SEQUENCE t_limits_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE t_limits_seq OWNER TO username;

CREATE SEQUENCE t_sensor_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE t_sensor_seq OWNER TO username;

CREATE TABLE t_limits (
    id integer NOT NULL,
    coldlimit integer,
    warmlimit integer
);

ALTER TABLE t_limits OWNER TO username;

CREATE TABLE t_sensor (
    id integer NOT NULL,
    takenat timestamp with time zone,
    takentemperature integer,
    sensorstate character varying(255)
);

ALTER TABLE t_sensor OWNER TO username;

ALTER TABLE ONLY t_limits
ADD CONSTRAINT t_limits_pkey PRIMARY KEY (id);

ALTER TABLE ONLY t_sensor
ADD CONSTRAINT t_sensor_pkey PRIMARY KEY (id);
