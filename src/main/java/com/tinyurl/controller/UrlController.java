package com.tinyurl.controller;

import com.tinyurl.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<Map<String, String>> shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("url");
        String shortCode = urlService.save(originalUrl);
        String shortUrl = "http://localhost:8080/" + shortCode;
        return ResponseEntity.ok(Map.of("shortUrl", shortUrl));
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> redirect(@PathVariable String code) {
        String originalUrl = urlService.getOriginalUrl(code);
        if (originalUrl == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(302).header("Location", originalUrl).build();
    }
}