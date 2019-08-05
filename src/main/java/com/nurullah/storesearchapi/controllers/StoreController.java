package com.nurullah.storesearchapi.controllers;

import com.nurullah.storesearchapi.models.SearchResponse;
import com.nurullah.storesearchapi.services.StoreService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping(value = "/search")
    public SearchResponse searchStore(@RequestParam("q") String query) {
        if (StringUtils.isBlank(query)) {
            return null;
        }

        return storeService.search(query);
    }
}
