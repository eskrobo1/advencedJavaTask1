package com.comtrade.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class Application {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		final File folder = new File("src\\main\\resources\\files");
		Solution.solve(folder);
	}
}
