package me.seungpang.bank.application.web;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DepositRequest {

    private BigDecimal depositAmount;

    public DepositRequest(final BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }
}
