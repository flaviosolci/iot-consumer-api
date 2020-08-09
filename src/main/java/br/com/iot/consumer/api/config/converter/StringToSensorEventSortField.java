package br.com.iot.consumer.api.config.converter;

import br.com.iot.consumer.api.model.search.SensorEventSortField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSensorEventSortField implements Converter<String, SensorEventSortField> {
    private static final Logger LOG = LoggerFactory.getLogger(StringToSensorEventSortField.class);

    @Override
    public SensorEventSortField convert(String source) {
        try {
            LOG.debug("==== Converting {} to SensorEventSortField", source);
            return SensorEventSortField.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            LOG.error("==== Could not parse {} into SensorEventSortField", source);
            return null;
        }
    }
}

