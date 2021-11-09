package com.github.maquina1995.grpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.maquina1995.grpc.client.HelloWorldClient;

@SpringBootApplication
public class Main implements CommandLineRunner {

	public static void main(String... args) {
		SpringApplication.run(Main.class);
	}

	@Autowired
	private HelloWorldClient helloWorldClient;

	@Override
	public void run(String... args) throws Exception {
		helloWorldClient.sayHello("Maquina", "1995");
	}
}