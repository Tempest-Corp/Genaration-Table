package com.frame.naina;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.frame.naina.func.EntityReader;
import com.frame.naina.func.Input;

@SpringBootApplication
public class NainaApplication {

	public static void main(String[] args) throws Exception {

		Input input = new Input();
		input.ask();
		EntityReader entityReader = new EntityReader(input.getDatabaseName(),
				input.getDatabaseUsername(),
				input.getPassword(), input);

		entityReader.readTables();

	}

}
