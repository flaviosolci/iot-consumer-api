package br.com.iot.consumer.api.controller.request;

import br.com.iot.consumer.api.model.search.AggregateFunctionType;
import br.com.iot.consumer.api.model.search.SensorEventGroupBy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AggregateEventsFilter {

    @Valid
    @NotNull(message = "{mandatory.filter}")
    private Filter filter;
    @Valid
    @NotNull(message = "{mandatory.aggregate}")
    private AggregateEvents aggregate;

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public AggregateEvents getAggregate() {
        return aggregate;
    }

    public void setAggregate(AggregateEvents aggregate) {
        this.aggregate = aggregate;
    }

    @Override
    public String toString() {
        return "AggregateEventsFilter{" +
                "filter=" + filter +
                ", aggregate=" + aggregate +
                '}';
    }

    public static class AggregateEvents {

        @NotEmpty(message = "{mandatory.aggregate.request.groupBy}")
        private List<@NotNull(message = "{invalid.aggregate.request.groupBy}") SensorEventGroupBy> groupBy;
        @NotNull(message = "{invalid.aggregate.request.type}")
        private AggregateFunctionType type;

        public List<SensorEventGroupBy> getGroupBy() {
            return groupBy;
        }

        public String getGroupByCommaDelimited() {
            return groupBy.stream().map(SensorEventGroupBy::name)
                    .collect(Collectors.joining(","));
        }

        public void setGroupBy(List<SensorEventGroupBy> groupBy) {
            this.groupBy = groupBy;
        }

        public AggregateFunctionType getType() {
            return type;
        }

        public void setType(AggregateFunctionType type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "AggregateEvents{" +
                    "groupBy=" + groupBy +
                    ", type=" + type +
                    '}';
        }
    }

    public static class Filter {

        @NotNull(message = "{mandatory.filter.request.startDate}")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime startDate;
        @NotNull(message = "{mandatory.filter.request.endDate}")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime endDate;
        private Long sensorId;
        private Long clusterId;

        private final Map<String, Long> optionalFields = new HashMap<>(2);

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

        public void setSensorId(Long sensorId) {
            optionalFields.put("sensor_id", sensorId);
            this.sensorId = sensorId;
        }

        public void setClusterId(Long clusterId) {
            optionalFields.put("cluster_id", clusterId);
            this.clusterId = clusterId;
        }

        public Map<String, Long> getOptionalFields() {
            return optionalFields;
        }

        @Override
        public String toString() {
            return "Filter{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", sensorId=" + sensorId +
                    ", clusterId=" + clusterId +
                    '}';
        }
    }
}
