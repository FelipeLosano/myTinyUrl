package com.felipelosano.mytinyurl.controller;

import com.felipelosano.mytinyurl.dto.UrlRequestDTO;
import com.felipelosano.mytinyurl.dto.UrlResponseDTO;
import com.felipelosano.mytinyurl.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UrlController {

  private final UrlService urlService;

  @GetMapping
  @Operation(description = "Endpoint responsible for list all URLs")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "URLs listed successfully"),
          @ApiResponse(responseCode = "500", description = "Internal Error")
  })
  public ResponseEntity<List<UrlResponseDTO>> getAll() {
    List<UrlResponseDTO> dtos = urlService.findAll();
    return ResponseEntity.status(HttpStatus.OK).body(dtos);
  }

  @GetMapping("/{shortUrl}")
  @Operation(description = "Endpoint responsible for getting  URL by shortURL")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "URL listed successfully"),
          @ApiResponse(responseCode = "400", description = "Request error"),
          @ApiResponse(responseCode = "500", description = "Internal error")
  })
  public ResponseEntity<UrlResponseDTO> getByShortUrl(@PathVariable String shortUrl) {
    UrlResponseDTO response = urlService.findByShortUrl(shortUrl);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping
  @Operation(description = "Endpoint responsible for getting  URL by shortURL")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "URL created successfully"),
          @ApiResponse(responseCode = "400", description = "Request error"),
          @ApiResponse(responseCode = "500", description = "Internal error")
  })
  public ResponseEntity<UrlResponseDTO> create(@RequestBody UrlRequestDTO dto) {
    UrlResponseDTO response = urlService.save(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping
  @Operation(description = "Endpoint responsible for getting  URL by shortURL")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "URL update successfully"),
          @ApiResponse(responseCode = "400", description = "Request error"),
          @ApiResponse(responseCode = "500", description = "Internal error")
  })
  public ResponseEntity<UrlResponseDTO> update(@RequestBody UrlRequestDTO dto, Long id) {
    UrlResponseDTO response = urlService.update(dto, id);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @DeleteMapping
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "URL deleted successfully"),
          @ApiResponse(responseCode = "400", description = "Request error"),
          @ApiResponse(responseCode = "500", description = "Internal error")
  })
  public ResponseEntity<UrlResponseDTO> delete(Long id) {
    urlService.delete(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
