package com.chapter2;

public class NoneDiscountPolicy implements DisCountPolicy {

    @Override
    public Money calculateDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}
