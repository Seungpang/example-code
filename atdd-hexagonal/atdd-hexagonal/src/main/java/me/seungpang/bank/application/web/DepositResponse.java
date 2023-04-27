package me.seungpang.bank.application.web;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DepositResponse {

    private BigDecimal depositAmount;

    public DepositResponse(final BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }
}
