package com.example.eventsystem.service;

import com.example.eventsystem.producer.CouponCreateProducer;
import com.example.eventsystem.repository.AppliedUserRepository;
import com.example.eventsystem.repository.CouponCountRepository;
import com.example.eventsystem.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {

    private final CouponRepository couponRepository;
    private final CouponCountRepository couponCountRepository;
    private final CouponCreateProducer couponCreateProducer;
    private final AppliedUserRepository appliedUserRepository;


    public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository, CouponCreateProducer couponCreateProducer, AppliedUserRepository appliedUserRepository) {
        this.couponRepository = couponRepository;
        this.couponCountRepository = couponCountRepository;
        this.couponCreateProducer = couponCreateProducer;
        this.appliedUserRepository = appliedUserRepository;
    }

    public void apply(Long userId) {
        long count = couponCountRepository.increment();
        System.out.println(count);
        if (count > 100) {
            return;
        }

        couponCreateProducer.create(userId);
    }

    public void applyV2(Long userId) {
        Long isIssued = appliedUserRepository.add(userId);

        if (isIssued != 1) {
            return;
        }

        long count = couponCountRepository.increment();

        if (count > 100) {
            return;
        }

        couponCreateProducer.create(userId);
    }
}
