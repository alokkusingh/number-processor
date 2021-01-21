package com.alok.number.processor;

import com.alok.number.processor.compute.AsyncProcessor;
import com.alok.number.processor.compute.SyncProcessor;
import mockit.Mocked;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NumberProcessorApplication Test Cases")
class NumberProcessorApplicationTests {

	@Mocked
	AsyncProcessor mockAsyncProcessor;

	@Mocked
	SyncProcessor mockSyncProcessor;

	@Test
	void contextLoads() {
	}

	@DisplayName("Main - errors")
	@Test
	public void main_Errors() throws IOException {
		assertThrows(IllegalArgumentException.class, () -> NumberProcessorApplication.main(new String[] {"1", "2", "6", "4", "3", "UNKNOWN"}));
		assertThrows(IllegalArgumentException.class, () -> NumberProcessorApplication.main(new String[] {}));
		assertThrows(IllegalArgumentException.class, () -> NumberProcessorApplication.main(new String[] {"1", "PARALLEL"}));
	}

	@DisplayName("Main - Success")
	@Test
	public void main() {
		assertDoesNotThrow(() -> NumberProcessorApplication.main(new String[] {"1", "2", "6", "4", "3", "PARALLEL"}));
		assertDoesNotThrow(() -> NumberProcessorApplication.main(new String[] {"1", "2", "6", "4", "3", "SEQUENTIAL"}));
	}

}
