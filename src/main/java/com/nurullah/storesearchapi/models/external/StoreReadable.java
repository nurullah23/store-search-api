package com.nurullah.storesearchapi.models.external;

import com.nurullah.storesearchapi.models.StoreItem;

import java.util.List;

public interface StoreReadable {
    List<StoreItem> toStoreItems();
}
