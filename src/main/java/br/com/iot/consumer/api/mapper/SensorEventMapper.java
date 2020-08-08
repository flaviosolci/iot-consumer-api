package br.com.iot.consumer.api.mapper;

import br.com.iot.consumer.api.controller.response.AggregateSensorEventResponse;
import br.com.iot.consumer.api.controller.response.SensorEventResponse;
import br.com.iot.consumer.api.model.dto.AggregateSensorEventDto;
import br.com.iot.consumer.api.model.entity.SensorEventEntity;
import br.com.iot.consumer.api.model.event.SensorEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SensorEventMapper {

    @Mapping(target = "sensorId", source = "id")
    @Mapping(target = "metadata", expression = "java(event.getMetadata() == null ? \"\": event.getMetadata().toString())")
    SensorEventEntity toEntity(SensorEvent event);

    SensorEventResponse toResponse(SensorEventEntity entity);

    AggregateSensorEventResponse toResponse(AggregateSensorEventDto dto);
}
