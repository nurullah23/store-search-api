package com.nurullah.storesearchapi.services.impl;

import com.nurullah.storesearchapi.models.external.ITunesAlbumResponse;
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
@Qualifier("ITunesAlbum")
public class ITunesAlbumSearchService implements SearchService {

    private static final String BASE_URL = "https://itunes.apple.com/search?term={term}&limit={limit}&entity={type}";
    private static final String ENTITY_ALBUM = "album";

    private final RestTemplate restTemplate;

    @Autowired
    public ITunesAlbumSearchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<StoreItem> search(String query, int limit) {
        try {
            ResponseEntity<ITunesAlbumResponse> response = restTemplate.getForEntity(BASE_URL, ITunesAlbumResponse.class, query, limit, ENTITY_ALBUM);
            return Optional.ofNullable(response)
                    .filter(HttpEntity::hasBody)
                    .map(HttpEntity::getBody)
                    .map(ITunesAlbumResponse::toStoreItems)
                    .orElse(Collections.emptyList());
        }
        catch (Exception e) {
            log.error("An error occurred while searching ITunes albums: " + query, e);
        }

        return Collections.emptyList();
    }
}
