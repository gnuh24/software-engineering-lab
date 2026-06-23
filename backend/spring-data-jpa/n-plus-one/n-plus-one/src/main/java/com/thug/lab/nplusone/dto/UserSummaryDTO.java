package com.thug.lab.nplusone.dto;


public record UserSummaryDTO(
        Long id,
        String name,
        Long orderCount
) {
}
