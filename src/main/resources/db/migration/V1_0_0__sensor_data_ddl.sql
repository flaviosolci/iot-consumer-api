CREATE TABLE sensor_event (
	timestamp timestamptz NOT NULL,
	sensor_id bigint NOT NULL,
	cluster_id bigint NULL,
	name varchar NULL,
	type varchar NOT NULL,
	value double precision NOT NULL
);
COMMENT ON TABLE sensor_event IS 'Hold the date for all the sensors events';

COMMENT ON COLUMN sensor_event.timestamp IS 'When the event occurred';
COMMENT ON COLUMN sensor_event.sensor_id IS 'Sensor ID (unique to each sensor)';
COMMENT ON COLUMN sensor_event.cluster_id IS 'In case the sensor is in cluster';
COMMENT ON COLUMN sensor_event.type IS 'Type of the event triggered. Ex: Temperature';
COMMENT ON COLUMN sensor_event.value IS 'The value measured by the sensor';

SELECT create_hypertable('sensor_event', 'timestamp', 'sensor_id' , 4,  chunk_time_interval => INTERVAL '1 day');

CREATE INDEX sensor_event_type_idx ON sensor_event (type, timestamp DESC);
CREATE INDEX sensor_event_name_idx ON sensor_event (name, timestamp DESC);
CREATE INDEX sensor_event_cluster_id_idx ON sensor_event (timestamp DESC, cluster_id)  WHERE cluster_id IS NOT NULL;