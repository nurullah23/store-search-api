package com.nurullah.storesearchapi.models.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nurullah.storesearchapi.models.ItemType;
import com.nurullah.storesearchapi.models.StoreItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ITunesAlbumResponse implements StoreReadable {

    @JsonProperty("resultCount")
    private int count;

    @JsonProperty("results")
    private List<ITunesAlbumResponseItem> items;

    @Override
    public List<StoreItem> toStoreItems() {
        return Optional.ofNullable(items)
                .map(r -> r.stream()
                        .map(i -> new StoreItem(ItemType.Album, i.getArtist(), i.getAlbumName()))
                        .collect(toList()))
                .orElse(Collections.emptyList());
    }
}

