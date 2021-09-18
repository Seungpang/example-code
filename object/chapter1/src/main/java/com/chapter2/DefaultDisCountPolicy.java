package com.chapter2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DefaultDisCountPolicy implements DisCountPolicy {

    private List<DisCountCondition> conditions = new ArrayList<>();

    public DefaultDisCountPolicy(DisCountCondition... conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    public Money calculateDiscountAmount(Screening screening) {
        for (DisCountCondition each : conditions) {
            if (each.isSatisfiedBy(screening)) {
                return getDisCountAmount(screening);
            }
        }

        return Money.ZERO;
    }

    abstract protected Money getDisCountAmount(Screening screening);
}
