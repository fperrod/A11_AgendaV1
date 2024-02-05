package agenda.tests;

import agenda.config.Config;

public class TestProperties {
	
	public static void main(String[] args) {
		System.out.println(Config.getProperties().getProperty("bbdd.url"));
		System.out.println(Config.getProperties().getProperty("bbdd.user"));
		System.out.println(Config.getProperties().getProperty("bbdd.pwd"));
	}

}
