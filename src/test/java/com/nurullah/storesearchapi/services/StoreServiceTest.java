package com.nurullah.storesearchapi.services;

import com.nurullah.storesearchapi.models.ItemType;
import com.nurullah.storesearchapi.models.SearchResponse;
import com.nurullah.storesearchapi.models.StoreItem;
import com.nurullah.storesearchapi.services.impl.StoreServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class StoreServiceTest {

    private static final int SEARCH_LIMIT = 5;
    private static final String TEST_QUERY = "test";
    private static final String TEST_TITLE = "btest";
    private static final String TEST_TITLE_2 = "atest";
    private static final String TEST_ARTIST = "test artist";
    private static final String TEST_AUTHOR = "test author";

    @Mock
    private SearchService albumService;

    @Mock
    private SearchService bookService;

    private StoreServiceImpl service;

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Before
    public void setUp() {
        service = new StoreServiceImpl(SEARCH_LIMIT, albumService, bookService);
    }

    @Test
    public void givenNoMatchingItems_whenSearch_thenReturnEmptyResult() {
        //Given
        List<StoreItem> albums = Collections.emptyList();
        List<StoreItem> books = Collections.emptyList();
        when(albumService.search(TEST_QUERY, SEARCH_LIMIT)).thenReturn(albums);
        when(bookService.search(TEST_QUERY, SEARCH_LIMIT)).thenReturn(books);

        //When
        SearchResponse result = service.search(TEST_QUERY);

        //Then
        verify(albumService).search(TEST_QUERY, SEARCH_LIMIT);
        verify(bookService).search(TEST_QUERY, SEARCH_LIMIT);
        collector.checkThat(result, notNullValue());
        collector.checkThat(result.getCount(), equalTo(0));
        collector.checkThat(result.getResults(), empty());
    }

    @Test
    public void givenValidQuery_whenSearch_thenReturnResults() {
        //Given
        StoreItem storeItem2 = new StoreItem(ItemType.Album, TEST_ARTIST, TEST_TITLE);
        StoreItem storeItem1 = new StoreItem(ItemType.Book, TEST_AUTHOR, TEST_TITLE_2);
        List<StoreItem> albums = Collections.singletonList(storeItem2);
        List<StoreItem> books = Collections.singletonList(storeItem1);
        when(albumService.search(TEST_QUERY, SEARCH_LIMIT)).thenReturn(albums);
        when(bookService.search(TEST_QUERY, SEARCH_LIMIT)).thenReturn(books);

        //When
        SearchResponse result = service.search(TEST_QUERY);

        //Then
        verify(albumService).search(TEST_QUERY, SEARCH_LIMIT);
        verify(bookService).search(TEST_QUERY, SEARCH_LIMIT);
        collector.checkThat(result, notNullValue());
        collector.checkThat(result.getCount(), equalTo(2));
        collector.checkThat(result.getResults(), hasSize(2));
        collector.checkThat(result.getResults().get(0), equalTo(storeItem1));
        collector.checkThat(result.getResults().get(1), equalTo(storeItem2));
    }
}