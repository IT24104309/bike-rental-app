package lk.sliit.bike_rental_api.models;

import lk.sliit.bike_rental_api.enums.ComparatorOp;


import java.util.Collection;

public class ConditionEvaluator {
    public static boolean evaluate(DiscountCondition cond, User user) {
        Object userVal = getFieldValue(cond.getField(), user);
        return compare(userVal, cond.getValue(), cond.getOp());
    }

    private static Object getFieldValue(String field, User user) {
        return switch (field) {
            case "rideCount" -> user.getRideCount();
            case "userType" -> user.getUserType();
            case "profiles" -> user.getProfiles();
            case "lastRideDaysAgo" -> user.getDaysSinceLastRide();
            default -> throw new RuntimeException("Unsupported field: " + field);
        };
    }

    private static boolean compare(Object a, Object b, ComparatorOp op) {
        return switch (op) {
            case ComparatorOp.EQUALS -> a.equals(b);
            case ComparatorOp.NOT_EQUALS -> !a.equals(b);
            case ComparatorOp.GREATER_THAN -> ((Comparable) a).compareTo(b) > 0;
            case ComparatorOp.LESS_THAN -> ((Comparable) a).compareTo(b) < 0;
            case ComparatorOp.GREATER_THAN_OR_EQUALS -> ((Comparable) a).compareTo(b) >= 0;
            case ComparatorOp.IN -> ((Collection<?>) b).contains(a);
        };
    }
}