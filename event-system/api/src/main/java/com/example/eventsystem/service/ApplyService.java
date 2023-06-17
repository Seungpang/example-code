package com.example.eventsystem.service;

import com.example.eventsystem.domain.Coupon;
import com.example.eventsystem.producer.CouponCreateProducer;
import com.example.eventsystem.repository.CouponCountRepository;
import com.example.eventsystem.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {

    private final CouponRepository couponRepository;
    private final CouponCountRepository couponCountRepository;
    private final CouponCreateProducer couponCreateProducer;

    public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository, CouponCreateProducer couponCreateProducer) {
        this.couponRepository = couponRepository;
        this.couponCountRepository = couponCountRepository;
        this.couponCreateProducer = couponCreateProducer;
    }

    public void apply(Long userId) {
        long count = couponCountRepository.increment();
        System.out.println(count);
        if (count > 100) {
            return;
        }

        couponCreateProducer.create(userId);
    }
}
