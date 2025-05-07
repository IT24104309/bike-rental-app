package lk.sliit.bike_rental_api.models;

import lk.sliit.bike_rental_api.enums.ComparatorOp;

class DiscountCondition {
    private String field;
    private ComparatorOp op;
    private Object value;

    public DiscountCondition(String field, ComparatorOp op, Object value) {
        this.field = field;
        this.op = op;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public ComparatorOp getOp() {
        return op;
    }

    public Object getValue() {
        return value;
    }
}

