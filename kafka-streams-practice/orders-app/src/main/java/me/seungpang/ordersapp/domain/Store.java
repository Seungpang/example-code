package me.seungpang.ordersapp.domain;

public record Store(String locationId,
                    Address address,
                    String contactNum) {
}
