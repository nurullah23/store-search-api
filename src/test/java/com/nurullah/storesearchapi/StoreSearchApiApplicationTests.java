package com.nurullah.storesearchapi;

import com.nurullah.storesearchapi.controllers.StoreController;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreSearchApiApplicationTests {

	@Autowired
	private StoreController storeController;

	@Rule
	public ErrorCollector collector = new ErrorCollector();

	@Test
	public void contextLoads() {
		collector.checkThat(storeController, notNullValue());
	}

}
