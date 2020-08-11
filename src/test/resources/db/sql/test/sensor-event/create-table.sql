CREATE TABLE sensor_event (
	"timestamp" TIMESTAMP WITH TIME ZONE NOT NULL,
	sensor_id integer NOT NULL,
	cluster_id bigint NULL,
	name varchar NULL,
	type varchar NOT NULL,
	"value" double precision NOT NULL
);