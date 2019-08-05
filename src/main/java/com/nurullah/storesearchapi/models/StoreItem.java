package com.nurullah.storesearchapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreItem {
    private ItemType type;
    private String author;
    private String title;
}
