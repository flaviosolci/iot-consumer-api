package br.com.iot.consumer.api.config.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class StringToSortDirection implements Converter<String, Sort.Direction> {
    private static final Logger LOG = LoggerFactory.getLogger(StringToSortDirection.class);

    @Override
    public Sort.Direction convert(String source) {
        try {
            LOG.debug("==== Converting {} to SensorEventSortField", source);
            return Sort.Direction.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            LOG.error("==== Could not parse {} into SensorEventSortField", source);
            return null;
        }
    }
}

