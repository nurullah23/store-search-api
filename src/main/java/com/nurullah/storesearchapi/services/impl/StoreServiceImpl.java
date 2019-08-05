package com.nurullah.storesearchapi.services.impl;

import com.nurullah.storesearchapi.models.SearchResponse;
import com.nurullah.storesearchapi.models.StoreItem;
import com.nurullah.storesearchapi.services.SearchService;
import com.nurullah.storesearchapi.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StoreServiceImpl implements StoreService {

    private final SearchService albumService;
    private final SearchService bookService;
    private final int maxResultLimit;

    @Autowired
    public StoreServiceImpl(@Value("${search.limit}") int maxResultLimit,
                            @Qualifier("ITunesAlbum") SearchService albumService,
                            @Qualifier("GoogleBooks") SearchService bookService) {
        this.maxResultLimit = maxResultLimit;
        this.albumService = albumService;
        this.bookService = bookService;
    }

    @Override
    public SearchResponse search(String query) {
        List<StoreItem> results = Stream.of(
                albumService.search(query, maxResultLimit),
                bookService.search(query, maxResultLimit))
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(StoreItem::getTitle))
                .collect(Collectors.toList());

        return SearchResponse.builder()
                .results(results)
                .count(results.size())
                .build();
    }
}
