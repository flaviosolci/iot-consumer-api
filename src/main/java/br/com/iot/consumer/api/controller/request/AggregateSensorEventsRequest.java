package br.com.iot.consumer.api.controller.request;

import br.com.iot.consumer.api.model.search.AggregateFunctionType;
import br.com.iot.consumer.api.model.search.SensorEventSortField;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AggregateSensorEventsRequest {

    private static Set<String> VALID_GROUP_BY = Set.of("name", "type");

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;
    @NotEmpty
    private List<String> groupBy;
    private SensorEventSortField sortBy = SensorEventSortField.NAME;
    private Sort.Direction direction = Sort.Direction.ASC;
    private AggregateFunctionType functionType;
    private Long sensorId;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public List<String> getGroupBy() {
        return groupBy;
    }

    public String getGroupByCommaDelimited() {
        return groupBy.stream().collect(Collectors.joining(","));
    }

    public void setGroupBy(List<String> groupBy) {
        this.groupBy = groupBy;
    }

    public SensorEventSortField getSortBy() {
        return sortBy;
    }

    public void setSortBy(SensorEventSortField sortBy) {
        this.sortBy = sortBy;
    }

    public Sort.Direction getDirection() {
        return direction;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }

    public AggregateFunctionType getFunctionType() {
        return functionType;
    }

    public void setFunctionType(AggregateFunctionType functionType) {
        this.functionType = functionType;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public String decideFunction() {
        return null;
    }
}
