

CREATE TABLE sensor_event (
	timestamp timestamptz NOT NULL,
	sensor_id integer NOT NULL,
	name varchar NULL,
	type varchar NOT NULL,
	value double precision NOT NULL
);
COMMENT ON TABLE sensor_event IS 'Hold the date for all the sensors events';

COMMENT ON COLUMN sensor_event.timestamp IS 'When the event occurred';
COMMENT ON COLUMN sensor_event.sensor_id IS 'Sensor ID (unique to each sensor)';
COMMENT ON COLUMN sensor_event.type IS 'Type of the event triggered. Ex: Temperature';
COMMENT ON COLUMN sensor_event.value IS 'The value measured by the sensor';

SELECT create_hypertable('sensor_event', 'timestamp', 'sensor_id' , 4,  chunk_time_interval => INTERVAL '1 day');

CREATE INDEX ON sensor_event (type, timestamp DESC);