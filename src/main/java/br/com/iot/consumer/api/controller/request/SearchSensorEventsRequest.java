package br.com.iot.consumer.api.controller.request;

import br.com.iot.consumer.api.model.sorting.SensorEventSortField;

import java.time.OffsetDateTime;

public class SearchSensorEventsRequest {

    private FilterEvents filter;
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

        private OffsetDateTime from;
        private OffsetDateTime to;
        private String eventType;

        public OffsetDateTime getFrom() {
            return from;
        }

        public void setFrom(OffsetDateTime from) {
            this.from = from;
        }

        public OffsetDateTime getTo() {
            return to;
        }

        public void setTo(OffsetDateTime to) {
            this.to = to;
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
                    "from=" + from +
                    ", to=" + to +
                    ", eventType='" + eventType + '\'' +
                    '}';
        }
    }

    public static class PageAndSortEvents {

        private Integer limit;
        private Integer offset;
        private SensorEventSortField sortBy;

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
