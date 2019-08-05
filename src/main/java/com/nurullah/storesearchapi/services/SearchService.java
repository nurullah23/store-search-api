package com.nurullah.storesearchapi.services;

import com.nurullah.storesearchapi.models.StoreItem;

import java.util.List;

public interface SearchService {
    List<StoreItem> search(String query, int limit);
}
