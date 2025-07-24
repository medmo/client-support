package dev.guava.client_support.dtos;

public record ClientRequest(
    String name,
    String phoneNumber,
    String email,
    String address
    ) {
}
