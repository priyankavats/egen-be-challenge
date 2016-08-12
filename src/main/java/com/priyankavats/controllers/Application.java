package com.priyankavats.controllers;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;

import org.easyrules.api.RulesEngine;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mongodb.MongoClient;
import com.priyankavats.rules.OverWeightRule;
import com.priyankavats.rules.UnderWeightRule;

@SpringBootApplication
public class Application {

	final static Morphia morphia = new Morphia();
	private static Datastore datastore = null;
	private static RulesEngine rulesEngine;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		morphia.mapPackage("com.priyankavats.dao");

		// create the Datastore connecting to the default port on the local host
		datastore = morphia.createDatastore(new MongoClient(), "EgenMicroservice");
		// datastore.getDB().dropDatabase();
		datastore.ensureIndexes();
		
		rulesEngine = aNewRulesEngine().build();
        rulesEngine.registerRule(new UnderWeightRule());
        rulesEngine.registerRule(new OverWeightRule());

	}

	public static Datastore getDatastore() {
		return datastore;
	}
	
	public static RulesEngine getRulesEngine() {
		return rulesEngine;
	}

}