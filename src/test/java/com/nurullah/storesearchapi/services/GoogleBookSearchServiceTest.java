package com.nurullah.storesearchapi.services;

import com.nurullah.storesearchapi.models.StoreItem;
import com.nurullah.storesearchapi.models.external.GoogleBooksResponse;
import com.nurullah.storesearchapi.models.external.GoogleBooksResponseItem;
import com.nurullah.storesearchapi.services.impl.GoogleBooksSearchService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class GoogleBookSearchServiceTest {

    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q={query}&maxResults={limit}";
    private static final String TEST_QUERY = "test";
    private static final int TEST_LIMIT = 5;
    private static final int TEST_COUNT = 3;
    private static final String TEST_TITLE = "title";
    private static final String TEST_AUTHOR = "author";
    private static final String TEST_AUTHOR_2 = "author2";
    private static final String TEST_AUTHORS = "author, author2";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GoogleBooksSearchService service;

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void givenQuery_whenSearch_thenReturnResults() {
        //Given
        GoogleBooksResponseItem responseItem = new GoogleBooksResponseItem(TEST_TITLE, Collections.singletonList(TEST_AUTHOR));
        GoogleBooksResponse response = new GoogleBooksResponse();
        response.setCount(TEST_COUNT);
        response.setItems(Collections.singletonList(responseItem));
        when(restTemplate.getForEntity(BASE_URL, GoogleBooksResponse.class, TEST_QUERY, TEST_LIMIT))
                .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        //When
        List<StoreItem> result = service.search(TEST_QUERY, TEST_LIMIT);

        //Then
        verify(restTemplate).getForEntity(BASE_URL, GoogleBooksResponse.class, TEST_QUERY, TEST_LIMIT);
        collector.checkThat(result, notNullValue());
        collector.checkThat(result, hasSize(1));
        collector.checkThat(result.get(0).getTitle(), equalTo(TEST_TITLE));
        collector.checkThat(result.get(0).getAuthor(), equalTo(TEST_AUTHOR));
    }

    @Test
    public void givenMultipleAuthors_whenSearch_thenReturnAuthorsCommaSeparated() {
        //Given
        GoogleBooksResponseItem responseItem = new GoogleBooksResponseItem(TEST_TITLE, Arrays.asList(TEST_AUTHOR, TEST_AUTHOR_2));
        GoogleBooksResponse response = new GoogleBooksResponse();
        response.setCount(TEST_COUNT);
        response.setItems(Collections.singletonList(responseItem));
        when(restTemplate.getForEntity(BASE_URL, GoogleBooksResponse.class, TEST_QUERY, TEST_LIMIT))
                .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        //When
        List<StoreItem> result = service.search(TEST_QUERY, TEST_LIMIT);

        //Then
        verify(restTemplate).getForEntity(BASE_URL, GoogleBooksResponse.class, TEST_QUERY, TEST_LIMIT);
        collector.checkThat(result, notNullValue());
        collector.checkThat(result, hasSize(1));
        collector.checkThat(result.get(0).getAuthor(), equalTo(TEST_AUTHORS));
    }

    @Test
    public void givenResponseIsNull_whenSearch_thenReturnEmptyList() {
        //Given
        when(restTemplate.getForEntity(BASE_URL, GoogleBooksResponse.class, TEST_QUERY, TEST_LIMIT))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        //When
        List<StoreItem> result = service.search(TEST_QUERY, TEST_LIMIT);

        //Then
        verify(restTemplate).getForEntity(BASE_URL, GoogleBooksResponse.class, TEST_QUERY, TEST_LIMIT);
        collector.checkThat(result, notNullValue());
        collector.checkThat(result, empty());
    }

    @Test
    public void givenExceptionThrown_whenSearch_thenReturnEmptyList() {
        //Given
        when(restTemplate.getForEntity(BASE_URL, GoogleBooksResponse.class, TEST_QUERY, TEST_LIMIT))
                .thenThrow(new RestClientException("Exception"));

        //When
        List<StoreItem> result = service.search(TEST_QUERY, TEST_LIMIT);

        //Then
        verify(restTemplate).getForEntity(BASE_URL, GoogleBooksResponse.class, TEST_QUERY, TEST_LIMIT);
        collector.checkThat(result, notNullValue());
        collector.checkThat(result, empty());
    }

}
