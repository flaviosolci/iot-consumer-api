package br.com.iot.consumer.api.controller.request;

import br.com.iot.consumer.api.model.search.AggregateFunctionType;
import br.com.iot.consumer.api.model.search.SensorEventGroupBy;
import br.com.iot.consumer.api.model.search.SensorEventSortField;
import org.springframework.data.domain.Sort;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class AggregateEventsFilter {

    @Valid
    @NotNull(message = "{mandatory.filter}")
    private SensorFilter filter;
    @Valid
    @NotNull(message = "{mandatory.aggregate}")
    private AggregateEvents aggregate;

    public SensorFilter getFilter() {
        return filter;
    }

    public void setFilter(SensorFilter filter) {
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
        private List<@NotNull SensorEventGroupBy> groupBy;
        @NotNull(message = "{invalid.aggregate.request.sortBy}")
        private SensorEventSortField sortBy = SensorEventSortField.NAME;
        @NotNull(message = "{invalid.aggregate.request.direction}")
        private Sort.Direction direction = Sort.Direction.ASC;
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

}
