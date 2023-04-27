package me.seungpang.bank.adapter.in.web;

import me.seungpang.bank.application.web.DepositRequest;
import me.seungpang.bank.application.web.DepositResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BankController {

    @PostMapping("/deposit")
    public DepositResponse deposit(@RequestBody DepositRequest request) {
        return new DepositResponse(request.getDepositAmount());
    }
}
