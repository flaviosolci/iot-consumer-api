CREATE TABLE sensor_event (
	"timestamp" timestamp NOT NULL,
	sensor_id integer NOT NULL,
	name varchar NULL,
	type varchar NOT NULL,
	"value" double precision NOT NULL,
	PRIMARY KEY("timestamp", sensor_id)
);
COMMENT ON TABLE sensor_event IS 'Hold the date for all the sensors events';

COMMENT ON COLUMN sensor_event.timestamp IS 'When the event occurred';
COMMENT ON COLUMN sensor_event.sensor_id IS 'Sensor ID (unique to each sensor)';
COMMENT ON COLUMN sensor_event.type IS 'Type of the event triggered. Ex: Temperature';
COMMENT ON COLUMN sensor_event.value IS 'The value measured by the sensor';