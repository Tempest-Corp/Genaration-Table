package com.frame.naina;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.frame.naina.func.ControllerGenerator;
import com.frame.naina.func.EntityReader;
import com.frame.naina.func.Input;

@SpringBootApplication
public class NainaApplication {

	public static void main(String[] args) throws Exception {

		Input input = new Input();
		input.ask();
		if (input.getChoice().equalsIgnoreCase("a")) {
			EntityReader entityReader = new EntityReader(input.getDatabaseName(),
					input.getDatabaseUsername(),
					input.getPassword(), input);

			entityReader.readTables();
			System.out.println("Files generated successfully :)");
		}

	}

}
