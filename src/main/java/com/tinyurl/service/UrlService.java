package com.tinyurl.service;

import com.tinyurl.entity.ShortUrl;
import com.tinyurl.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final ShortUrlRepository repo;

    public String save(String originalUrl) {
        String code = UUID.randomUUID().toString().substring(0, 6);
        repo.save(new ShortUrl(code, originalUrl));
        return code;
    }

    public String getOriginalUrl(String code) {
        return repo.findById(code)
                .map(ShortUrl::getOriginalUrl)
                .orElse(null);
    }
}