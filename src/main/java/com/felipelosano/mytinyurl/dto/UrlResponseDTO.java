package com.felipelosano.mytinyurl.dto;

import java.time.OffsetDateTime;

public record UrlResponseDTO(Long id, String longUrl, String shortUrl, OffsetDateTime createdAt) {
}
