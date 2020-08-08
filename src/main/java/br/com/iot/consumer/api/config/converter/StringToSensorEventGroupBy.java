package br.com.iot.consumer.api.config.converter;

import br.com.iot.consumer.api.model.search.SensorEventGroupBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class StringToSensorEventGroupBy implements Converter<String, SensorEventGroupBy> {
    private static final Logger LOG = LoggerFactory.getLogger(StringToSensorEventGroupBy.class);

    @Override
    public SensorEventGroupBy convert(String source) {
        try {
            LOG.debug("==== Converting {} to AggregateFunctionType", source);
            return SensorEventGroupBy.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            LOG.error("==== Could not parse {} into SensorEventGroupBy", source);
            return null;
        }
    }


}

