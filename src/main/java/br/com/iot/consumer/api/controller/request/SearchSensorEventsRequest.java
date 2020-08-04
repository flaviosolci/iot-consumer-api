package br.com.iot.consumer.api.controller.request;

import br.com.iot.consumer.api.model.search.SensorEventSortField;
import br.com.iot.consumer.api.model.search.SortDirection;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class SearchSensorEventsRequest {

    @Valid
    @NotNull
    private FilterEvents filter;
    @Valid
    private PageAndSortEvents page;

    public FilterEvents getFilter() {
        return filter;
    }

    public void setFilter(FilterEvents filter) {
        this.filter = filter;
    }

    public PageAndSortEvents getPage() {
        return page;
    }

    public void setPage(PageAndSortEvents page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "SearchSensorEventsRequest{" +
                "filter=" + filter +
                ", page=" + page +
                '}';
    }

    public static class FilterEvents {

        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime startDate;
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime endDate;
        private String eventType;

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

        @Override
        public String toString() {
            return "FilterEvents{" +
                    "from=" + startDate +
                    ", to=" + endDate +
                    ", eventType='" + eventType + '\'' +
                    '}';
        }
    }

    public static class PageAndSortEvents {

        @Max(value = 50, message = "The field 'limit' must be between 1 and 50.")
        @Min(value = 1, message = "The field 'limit' must be between 1 and 50.")
        private Integer limit = 50;
        @Min(value = 0, message = "The field 'offset' starts at 0")
        private Integer offset = 0;
        private SensorEventSortField sortBy = SensorEventSortField.TIMESTAMP;
        private SortDirection direction = SortDirection.DESC;

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

        @Override
        public String toString() {
            return "PageAndSortEvents{" +
                    "limit=" + limit +
                    ", offset=" + offset +
                    ", sortBy=" + sortBy +
                    '}';
        }
    }

}
