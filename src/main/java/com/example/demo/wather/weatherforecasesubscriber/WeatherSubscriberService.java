package com.example.demo.wather.weatherforecasesubscriber;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeatherSubscriberService {

	private final static Logger LOGGER = LoggerFactory.getLogger(WeatherSubscriberService.class);
	
	@Autowired
	private KafkaConsumer<String, String> kafkaConsumer;
	
	private ExecutorService executorService = Executors.newCachedThreadPool();
	
	@Value("${weather.kafka-topic}") 
	String kafkaTopic;
	
	@PostConstruct
	public void start() {
		LOGGER.info("Starting weather consumer fot topic : {} ", kafkaTopic);
		kafkaConsumer.subscribe(Arrays.asList(kafkaTopic));

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				kafkaConsumer.close();
			}
		}));
		
		executorService.execute(() -> {
			while(true) {
				ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
				for(ConsumerRecord<String, String> record : records) {
					LOGGER.info("Message Received... : \n Topic : {} \n key : {}  \n value : {} ",
							record.topic(), record.key(), record.value());
				}
			}
		});
		
	}
}
