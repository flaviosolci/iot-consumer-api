package br.com.iot.consumer.api.config.converter;

import br.com.iot.consumer.api.model.search.AggregateFunctionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class StringToAggregationFunctionType implements Converter<String, AggregateFunctionType> {

    private static final Logger LOG = LoggerFactory.getLogger(StringToAggregationFunctionType.class);

    @Override
    public AggregateFunctionType convert(String source) {
        try {
            LOG.debug("==== Converting {} to AggregateFunctionType", source);
            return AggregateFunctionType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            LOG.error("==== Could not parse {} into AggregateFunctionType", source);
            return null;
        }
    }


}

