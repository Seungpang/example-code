package com.example.eventsystem.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    protected Coupon() {
    }

    public Coupon(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
