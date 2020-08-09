package com.alok.number.processor;

import com.alok.number.processor.compute.AsyncProcessor;
import com.alok.number.processor.compute.SyncProcessor;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class NumberProcessorApplicationTests {

	@Mocked
	AsyncProcessor mockAsyncProcessor;

	@Mocked
	SyncProcessor mockSyncProcessor;

	@Test
	void contextLoads() {
	}

	@Test
	public void main_Errors() throws IOException {
		assertThrows(IllegalArgumentException.class, () -> NumberProcessorApplication.main(new String[] {"1", "2", "6", "4", "3", "UNKNOWN"}));
		assertThrows(IllegalArgumentException.class, () -> NumberProcessorApplication.main(new String[] {}));
		assertThrows(IllegalArgumentException.class, () -> NumberProcessorApplication.main(new String[] {"1", "PARALLEL"}));
	}

	@Test
	public void main() {
		assertDoesNotThrow(() -> NumberProcessorApplication.main(new String[] {"1", "2", "6", "4", "3", "PARALLEL"}));
		assertDoesNotThrow(() -> NumberProcessorApplication.main(new String[] {"1", "2", "6", "4", "3", "SEQUENTIAL"}));
	}

}
