package com.example.demo.wather.weatherforecasesubscriber;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherforecastsubscriberApplication {
	
	@Autowired
	private KafkaConsumer<String, String> kafkaConsumer;
	
	@Value("${weather.kafka-topic}") 
	private String kafkaTopic;

	public static void main(String[] args) {
		SpringApplication.run(WeatherforecastsubscriberApplication.class, args);
		
		
		
	}

}
