package com.chapter2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface DisCountPolicy {

    Money calculateDiscountAmount(Screening screening);
}
