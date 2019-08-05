package com.nurullah.storesearchapi.models.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nurullah.storesearchapi.models.ItemType;
import com.nurullah.storesearchapi.models.StoreItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleBooksResponse implements StoreReadable {

    private static final String AUTHOR_SEPARATOR = ", ";

    @JsonProperty("totalItems")
    private int count;

    @JsonProperty("items")
    private List<GoogleBooksResponseItem> items;

    @Override
    public List<StoreItem> toStoreItems() {
        return Optional.ofNullable(items)
                .map(r -> r.stream()
                        .map(i -> new StoreItem(ItemType.Book, StringUtils.join(i.getAuthors(), AUTHOR_SEPARATOR), i.getTitle()))
                        .collect(toList()))
                .orElse(Collections.emptyList());
    }
}
