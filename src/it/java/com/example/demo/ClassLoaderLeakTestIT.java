package com.example.demo;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import de.evosec.leaktest.WebAppTest;

public class ClassLoaderLeakTestIT {

	@Test
	public void test() throws Exception {
		System.setProperty("spring.profiles.active", "integration");
		String warName = System.getProperty("warName", "demo-0.0.1-SNAPSHOT");
		Path warPath = Paths.get("./target/" + warName + ".war");
		new WebAppTest().warPath(warPath).run();
	}

}
