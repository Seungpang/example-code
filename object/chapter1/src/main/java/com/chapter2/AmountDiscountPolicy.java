package com.chapter2;

public class AmountDiscountPolicy extends DefaultDisCountPolicy {

    private Money discountAmount;


    public AmountDiscountPolicy(Money discountAmount, DisCountCondition... conditions) {
        super(conditions);
        this.discountAmount = discountAmount;
    }

    @Override
    protected Money getDisCountAmount(Screening screening) {
        return null;
    }
}
