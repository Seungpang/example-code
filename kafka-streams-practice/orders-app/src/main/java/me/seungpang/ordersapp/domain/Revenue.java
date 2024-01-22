package me.seungpang.ordersapp.domain;

import java.math.BigDecimal;

public record Revenue(String locationId,
                      BigDecimal finalAmount) {
}
