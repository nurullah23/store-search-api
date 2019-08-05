package com.nurullah.storesearchapi.models.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBooksResponseItem {

    private String title;
    private List<String> authors;

    @SuppressWarnings("unchecked")
    @JsonProperty("volumeInfo")
    private void getVolumeDetails(Map<String, Object> volumeInfo) {
        this.title = (String) volumeInfo.get("title");
        this.authors = (List<String>) volumeInfo.get("authors");
    }
}
