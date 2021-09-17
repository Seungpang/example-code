package com.chapter2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DiscountPolicy {

    private List<DisCountCondition> conditions = new ArrayList<>();

    public DiscountPolicy(DisCountCondition... conditions) {
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
