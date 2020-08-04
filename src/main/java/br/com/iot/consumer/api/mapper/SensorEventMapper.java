package br.com.iot.consumer.api.mapper;

import br.com.iot.consumer.api.model.entity.SensorEventEntity;
import br.com.iot.consumer.api.model.event.SensorEvent;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface SensorEventMapper {

    default SensorEventEntity toEntity(SensorEvent event) {
        return new SensorEventEntity(Instant.ofEpochMilli(event.getTimestamp()).atOffset(ZoneOffset.UTC), event.getId(), event.getType(), event.getValue(), event.getName());
    }
}
