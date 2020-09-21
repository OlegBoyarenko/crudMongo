package com.olegB.mongoCrud;

import com.olegB.mongoCrud.controller.ActivityController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MongoCrudApplicationTests {

	@Autowired
	private ActivityController activityController;

	@Test
	void contextLoads() {
		assertThat(activityController).isNotNull();
	}

}
