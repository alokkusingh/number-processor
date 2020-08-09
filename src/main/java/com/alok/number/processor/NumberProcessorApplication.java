package com.alok.number.processor;

import com.alok.number.processor.compute.AsyncProcessor;
import com.alok.number.processor.compute.Processor;
import com.alok.number.processor.compute.SyncProcessor;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;

@SpringBootApplication
public class NumberProcessorApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(NumberProcessorApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);

		if (args.length < 3 ||
				(!"SEQUENTIAL".equals(args[args.length - 1]) && !"PARALLEL".equals(args[args.length - 1])))
			throw new IllegalArgumentException("At least there should be 3 arguments - {2 to 5 numbers} {SEQUENTIAL|PARALLEL}");

		String type = args[args.length - 1];

		HashSet numbers = new HashSet<Integer>();
		for (int i = 0; i < args.length - 2; i++) {
			numbers.add(Integer.parseInt(args[i]));
		}

		Processor processor = null;
		if (type.equals("PARALLEL")) {
			processor = AsyncProcessor.builder()
					.numbers(numbers)
					.build();
		} else {
			processor = SyncProcessor.builder()
					.numbers(numbers)
					.build();
		}

		processor.process();
	}
}
