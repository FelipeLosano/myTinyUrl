package com.felipelosano.mytinyurl.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Url {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String longUrl;
  private String shortUrl;
  private OffsetDateTime createdAt;
  private Integer accesses = 0;

  public Url(String longUrl, String shortUrl, OffsetDateTime createdAt) {
    this.longUrl = longUrl;
    this.shortUrl = shortUrl;
    this.createdAt = createdAt;
  }
}
