package com.felipelosano.mytinyurl.service;

import com.felipelosano.mytinyurl.dto.UrlRequestDTO;
import com.felipelosano.mytinyurl.dto.UrlResponseDTO;
import com.felipelosano.mytinyurl.entity.Url;
import com.felipelosano.mytinyurl.exception.BadRequestException;
import com.felipelosano.mytinyurl.exception.UnprocessableEntityException;
import com.felipelosano.mytinyurl.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Random;


@Service
@RequiredArgsConstructor
@Slf4j
public class UrlService {

  private final UrlRepository urlRepository;

  public List<UrlResponseDTO> findAll() {
    log.info("Find all urls");

    return urlRepository.findAll().stream()
            .map(dto -> new UrlResponseDTO(dto.getId(), dto.getLongUrl(), dto.getShortUrl(), dto.getCreatedAt()))
            .toList();
  }

  public UrlResponseDTO findByShortUrl(String url) {
    log.info("Find url by shortUrl {}", url);

    Url responseUrl = urlRepository.findByShortUrl(url);
    if (responseUrl == null) {
      log.error("Can't find url by shortUrl {}", url);
      throw new BadRequestException("Url not found");
    }

    log.info("Url found {}", responseUrl);
    return new UrlResponseDTO(responseUrl.getId(), responseUrl.getLongUrl(), responseUrl.getShortUrl(),
            responseUrl.getCreatedAt());
  }

  public UrlResponseDTO save(UrlRequestDTO dto) {
    log.info("Save url {}", dto);

    Url url = new Url(dto.longUrl(), shortUrlGenerator(), OffsetDateTime.now());

    urlRepository.save(url);

    if (!urlRepository.existsById(url.getId())) {
      log.error("Can't save url {}", dto);
      throw new UnprocessableEntityException("Invalid Request");
    }

    log.info("Url saved {}", url);
    return new UrlResponseDTO(url.getId(), url.getLongUrl(), url.getShortUrl(), url.getCreatedAt());
  }

  public UrlResponseDTO update(UrlRequestDTO dto, Long id) {
    log.info("Update url {}", dto);

    Url url = urlRepository.findById(id).orElse(null);

    if (url == null) {
      log.info("Can't find url by id {}", id);
      throw new BadRequestException("Url not found");
    }

    url.setLongUrl(dto.longUrl());
    Url response = urlRepository.save(url);

    log.info("Url updated {}", response);
    return new UrlResponseDTO(response.getId(), response.getLongUrl(), response.getShortUrl(), response.getCreatedAt());
  }

  public void delete(Long id) {
    log.info("Delete url {}", id);

    if (!urlRepository.existsById(id)) {
      log.error("Can't find url by id {}", id);
      throw new BadRequestException("Invalid ID");
    }

    log.info("Url deleted {}", id);
    urlRepository.deleteById(id);
  }

  public String shortUrlGenerator() {
    log.info("Generate shortUrl");

    Url validUrl;
    String url;

    do {
      url = generateRandomString();
      validUrl = urlRepository.findByShortUrl(url);

    } while (validUrl != null);

    log.info("Url generated {}", url);
    return url;
  }


  private String generateRandomString() {
    log.info("Generate random string");

    String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    StringBuilder salt = new StringBuilder();
    Random rnd = new Random();
    while (salt.length() < 6) {
      int index = (int) (rnd.nextFloat() * SALTCHARS.length());
      salt.append(SALTCHARS.charAt(index));
    }

    log.info("Random string generated {}", salt);
    return salt.toString();

  }


}
