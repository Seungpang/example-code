package com.chapter2;

public class AmountDiscountPolicy extends DiscountPolicy{

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
