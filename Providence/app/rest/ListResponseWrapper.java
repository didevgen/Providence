package rest;

import java.util.Collection;

public class ListResponseWrapper {

    private Collection<?> items;
    private Long limit;
    private Long offset;
    private Long total;

    public ListResponseWrapper(Collection<?> items) {
        this(items, (long) items.size(), 0L, (long) items.size());
    }

    public ListResponseWrapper(Collection<?> items, Long limit, Long offset, Long total) {
        this.items = items;
        this.limit = limit;
        this.offset = offset;
        this.total = total;
    }

    public Collection<?> getItems() {
        return items;
    }

    public void setItems(Collection<?> items) {
        this.items = items;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
