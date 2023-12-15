package ru.wms.WarehouseManagementService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ApplicationTests {

	private final Application application;

	@Autowired
	public ApplicationTests(Application application) {
		this.application = application;
	}

	@Test
	void contextLoads() {
		assertNotNull(application);
	}

}
