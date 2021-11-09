package com.github.maquina1995.grpc.client;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.maquina1995.grpc.Greeting;
import com.github.maquina1995.grpc.HelloWorldServiceGrpc;
import com.github.maquina1995.grpc.HelloWorldServiceGrpc.HelloWorldServiceBlockingStub;
import com.github.maquina1995.grpc.Person;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Component
public class HelloWorldClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldClient.class);

	private HelloWorldServiceBlockingStub helloWorldServiceBlockingStub;

	@PostConstruct
	private void init() {
		ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
		        .usePlaintext()
		        .build();

		this.helloWorldServiceBlockingStub = HelloWorldServiceGrpc.newBlockingStub(managedChannel);
	}

	public String sayHello(String firstName, String lastName) {

		Person person = Person.newBuilder()
		        .setFirstName(firstName)
		        .setLastName(lastName)
		        .build();

		LOGGER.info("client sending {}", person);

		Greeting greeting = this.helloWorldServiceBlockingStub.sayHello(person);
		LOGGER.info("client received {}", greeting);

		return greeting.getMessage();
	}
}