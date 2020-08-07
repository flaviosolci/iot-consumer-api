package br.com.iot.consumer.api.controller.request;

import br.com.iot.consumer.api.model.search.SensorEventSortField;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class EventsFilter {

    @Valid
    @NotNull
    private Filter filter = new Filter();
    @Valid
    private PageAndSort page = new PageAndSort();

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public PageAndSort getPage() {
        return page;
    }

    public void setPage(PageAndSort page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "SearchSensorEventsRequest{" +
                "filter=" + filter +
                ", page=" + page +
                '}';
    }

    public static class Filter {

        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime startDate;
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime endDate;
        private String eventType;
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

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
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
                    ", eventType='" + eventType + '\'' +
                    ", sensorId=" + sensorId +
                    '}';
        }
    }

    public static class PageAndSort {

        @Max(value = 1000, message = "The field 'limit' must be between 1 and 1000.")
        @Min(value = 1, message = "The field 'limit' must be between 1 and 1000.")
        private Integer limit = 50;
        @Min(value = 0, message = "The field 'page' starts at 0")
        private Integer offset = 0;
        @NotNull
        private SensorEventSortField sortBy = SensorEventSortField.TIMESTAMP;
        @NotNull
        private Sort.Direction direction = Sort.Direction.DESC;

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public Integer getOffset() {
            return offset;
        }

        public void setOffset(Integer offset) {
            this.offset = offset;
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

        @Override
        public String toString() {
            return "PageAndSort{" +
                    "limit=" + limit +
                    ", offset=" + offset +
                    ", sortBy=" + sortBy +
                    ", direction=" + direction +
                    '}';
        }
    }

}
