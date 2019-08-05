package com.nurullah.storesearchapi.services;

import com.nurullah.storesearchapi.models.SearchResponse;

public interface StoreService {
    SearchResponse search(String query);
}
