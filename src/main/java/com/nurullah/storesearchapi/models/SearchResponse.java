package com.nurullah.storesearchapi.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchResponse {
    private int count;
    private List<StoreItem> results;
}
