package com.example.houseutils.controller;

import com.example.houseutils.constants.ActionType;
import com.example.houseutils.policy.BrokeragePolicy;
import com.example.houseutils.policy.BrokeragePolicyFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrokerageQueryController {

    @GetMapping("/api/calc/brokerage")
    public Long calcBrokerage(@RequestParam ActionType actionType,
                            @RequestParam Long price) {
        // 타입 정의 - 매매 / 임대차
        // TODO: 중개수수료 계산하는 로직

        BrokeragePolicy policy = BrokeragePolicyFactory.of(actionType);
        return policy.calculate(price);
    }
}
