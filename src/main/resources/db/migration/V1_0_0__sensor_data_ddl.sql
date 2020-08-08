CREATE TABLE sensor_event (
	timestamp timestamptz NOT NULL,
	sensor_id integer NOT NULL,
	name varchar NULL,
	type varchar NOT NULL,
	value double precision NOT NULL,
	metadata jsonb NULL
);
COMMENT ON TABLE sensor_event IS 'Hold the date for all the sensors events';

COMMENT ON COLUMN sensor_event.timestamp IS 'When the event occurred';
COMMENT ON COLUMN sensor_event.sensor_id IS 'Sensor ID (unique to each sensor)';
COMMENT ON COLUMN sensor_event.type IS 'Type of the event triggered. Ex: Temperature';
COMMENT ON COLUMN sensor_event.value IS 'The value measured by the sensor';
COMMENT ON COLUMN sensor_event.metadata IS 'Additional info about the sensor or event';

SELECT create_hypertable('sensor_event', 'timestamp', 'sensor_id');

CREATE INDEX type_timestamp_idx ON sensor_event (type, timestamp DESC);

CREATE INDEX clusterId_idx ON metadata(((data->>'clusterId')::integer))  WHERE data ? 'clusterId';