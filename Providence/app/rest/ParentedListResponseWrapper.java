package rest;

import java.util.Collection;

public class ParentedListResponseWrapper<T, D> extends ListResponseWrapper {

    private D parent;

    public ParentedListResponseWrapper(Collection<?> items, D parent) {
        super(items);
        setParent(parent);
    }

    public ParentedListResponseWrapper(Collection<?> items, D parent, Long limit, Long offset, Long total) {
        super(items, limit, offset, total);
        setParent(parent);
    }

    public D getParent() {
        return parent;
    }

    public void setParent(D parent) {
        this.parent = parent;
    }
}
