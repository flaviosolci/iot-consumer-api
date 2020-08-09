package br.com.iot.consumer.api.controller.request;

import br.com.iot.consumer.api.model.search.AggregateFunctionType;
import br.com.iot.consumer.api.model.search.SensorEventGroupBy;
import br.com.iot.consumer.api.model.search.SensorEventSortField;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AggregateEventsFilter {

    @Valid
    private Filter filter = new Filter();
    @Valid
    private AggregateEvents aggregate = new AggregateEvents();

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
                "startDate=" + filter.getStartDate() +
                ", endDate=" + filter.getEndDate() +
                ", sensorId=" + filter.getSensorId() +
                ", aggregate=" + aggregate +
                '}';
    }

    public static class AggregateEvents {

        @NotEmpty
        private List<@NotNull SensorEventGroupBy> groupBy;
        @NotNull
        private SensorEventSortField sortBy = SensorEventSortField.NAME;
        @NotNull
        private Sort.Direction direction = Sort.Direction.ASC;
        @NotNull
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

        public void setType(AggregateFunctionType type) {
            this.type = type;
        }

        public AggregateFunctionType getType() {
            return type;
        }

        @Override
        public String toString() {
            return "AggregateEvents{" +
                    "groupBy=" + groupBy +
                    ", sortBy=" + sortBy +
                    ", direction=" + direction +
                    ", type=" + type +
                    '}';
        }
    }

    public static class Filter {

        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime startDate;
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime endDate;
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

        public Long getSensorId() {
            return sensorId;
        }

        public void setSensorId(Long sensorId) {
            this.sensorId = sensorId;
        }

        @Override
        public String toString() {
            return "Filter{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", sensorId=" + sensorId +
                    '}';
        }
    }
}
