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
    @NotNull(message = "{mandatory.filter}")
    private Filter filter;
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

        @NotNull(message = "{mandatory.filter.request.startDate}")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime startDate;
        @NotNull(message = "{mandatory.filter.request.endDate}")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime endDate;
        private String eventType;
        private Long sensorId;
        private Long clusterId;

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

        public Long getClusterId() {
            return clusterId;
        }

        public void setClusterId(Long clusterId) {
            this.clusterId = clusterId;
        }

        @Override
        public String toString() {
            return "Filter{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", eventType='" + eventType + '\'' +
                    ", sensorId=" + sensorId +
                    ", clusterId=" + clusterId +
                    '}';
        }
    }

    public static class PageAndSort {

        @Max(value = 100, message = "{invalid.page.request.limit}")
        @Min(value = 1, message = "{invalid.page.request.limit}")
        private Integer limit = 50;
        @Min(value = 0, message = "{invalid.page.request.offset}")
        private Integer offset = 0;
        @NotNull(message = "{invalid.page.request.sortBy}")
        private SensorEventSortField sortBy = SensorEventSortField.TIMESTAMP;
        @NotNull(message = "{invalid.page.request.direction}")
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
