package com.nurullah.storesearchapi.services.impl;

import com.nurullah.storesearchapi.models.external.GoogleBooksResponse;
import com.nurullah.storesearchapi.models.StoreItem;
import com.nurullah.storesearchapi.services.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
@Qualifier("GoogleBooks")
public class GoogleBooksSearchService implements SearchService {

    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q={query}&maxResults={limit}";

    private final RestTemplate restTemplate;

    @Autowired
    public GoogleBooksSearchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }

    @Override
    public List<StoreItem> search(String query, int limit) {
        try {
            ResponseEntity<GoogleBooksResponse> response = restTemplate.getForEntity(BASE_URL, GoogleBooksResponse.class, query, limit);
            return Optional.ofNullable(response)
                    .filter(HttpEntity::hasBody)
                    .map(HttpEntity::getBody)
                    .map(GoogleBooksResponse::toStoreItems)
                    .orElse(Collections.emptyList());
        }
        catch (Exception e) {
            log.error("An error occurred while searching ITunes albums: " + limit, e);
        }

        return Collections.emptyList();
    }
}
