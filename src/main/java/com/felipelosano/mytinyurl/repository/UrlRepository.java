package com.felipelosano.mytinyurl.repository;

import com.felipelosano.mytinyurl.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
  Url findByShortUrl(String shortUrl);
}
