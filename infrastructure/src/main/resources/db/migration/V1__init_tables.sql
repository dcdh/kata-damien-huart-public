CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.hibernate_sequence OWNER TO username;

CREATE TABLE public.t_limits (
    id integer NOT NULL,
    coldlimit integer,
    warnlimit integer
);

ALTER TABLE public.t_limits OWNER TO username;

CREATE TABLE public.t_sensor (
    id integer NOT NULL,
    sensedat timestamp with time zone,
    sensedtemperature integer,
    sensorstate character varying(255)
);

ALTER TABLE public.t_sensor OWNER TO username;

SELECT pg_catalog.setval('public.hibernate_sequence', 3, true);

ALTER TABLE ONLY public.t_limits
ADD CONSTRAINT t_limits_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.t_sensor
ADD CONSTRAINT t_sensor_pkey PRIMARY KEY (id);
