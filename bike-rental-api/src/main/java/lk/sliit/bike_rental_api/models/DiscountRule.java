package lk.sliit.bike_rental_api.models;

import lk.sliit.bike_rental_api.enums.DiscountType;

import java.time.LocalDate;
import java.util.List;

public class DiscountRule {
    private String ruleId;
    private String name;
    private DiscountType type;
    private double value;
    private boolean isActive;
    private LocalDate startDate;
    private LocalDate endDate;
    private int priority;
    private int maxUsagePerUser;
    private List<DiscountCondition> conditions;


    public DiscountRule(){

    }

    public DiscountRule(String ruleId, String name, DiscountType type, double value,
                        boolean isActive, LocalDate startDate, LocalDate endDate,
                        int priority, int maxUsagePerUser, List<DiscountCondition> conditions) {
        this.ruleId = ruleId;
        this.name = name;
        this.type = type;
        this.value = value;
        this.isActive = isActive;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priority = priority;
        this.maxUsagePerUser = maxUsagePerUser;
        this.conditions = conditions;
    }

    public boolean isApplicable(User user, LocalDate today) {
        if (!isActive || today.isBefore(startDate) || today.isAfter(endDate)) {
            return false;
        }
        for (DiscountCondition cond : conditions) {
            if (!ConditionEvaluator.evaluate(cond, user)) {
                return false;
            }
        }
        return true;
    }

    public double applyDiscount(double originalAmount) {
        return type == DiscountType.PERCENTAGE
                ? originalAmount * (1 - value / 100)
                : originalAmount - value;
    }

    public int getPriority() { return priority; }
    public String getRuleId() { return ruleId; }
}
