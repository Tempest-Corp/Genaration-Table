package com.frame.naina;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.frame.naina.func.EntityReader;
import com.frame.naina.func.Input;

@SpringBootApplication
public class NainaApplication {

	public static void main(String[] args) throws Exception {

		Input input = new Input();
		input.ask();
		System.out.println(input.toString());
		EntityReader entityReader = new EntityReader(input.getDatabaseName(),
				input.getDatabaseUsername(),
				input.getPassword());
		entityReader.setPackageName(input.getPackageName());
		entityReader.setPathFile(input.getPathFile());
		entityReader.setLangage(input.getLangage());
		entityReader.setImports(input.getImports());
		entityReader.readTables();
	}

}
