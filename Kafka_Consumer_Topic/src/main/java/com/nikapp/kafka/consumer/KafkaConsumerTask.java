package com.nikapp.kafka.consumer;

import java.util.concurrent.Callable;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * This task tries to consume the data from Kafka topic.
 * It has associated kafka consumer id and kafka consumer.
 * 
 * @author nikhil.bhide
 * @Since 1.0
 */

public class KafkaConsumerTask implements Callable {


	private int consumerId;
	private KafkaConsumer<String, String> consumer;
	private long timeOut;
	private String groupId;

	/**
	 * Constructor for KafkaConsumerTask
	 * @param consumerId The consumer groupd id
	 * @param consumer The consumer instance
	 * @param timeOut The value of timeout
	 */
	public KafkaConsumerTask(int consumerId, KafkaConsumer<String,String> consumer, long timeOut, String groupId) {
		this.consumerId =consumerId;
		this.consumer=consumer;
		this.timeOut=timeOut;
		this.groupId=groupId;
	}
	
	/**
	 * @return the consumer
	 */
	public KafkaConsumer<String, String> getConsumer() {
		return consumer;
	}
	
	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}
	
	/**
	 * Implementation of KafkaConsumerTask Callable. It tries to consume the message from Kafka topic.
	 * If success then returns 1 otherewise 0.
	 * 
	 * @return Integer
	 * 
	 */
	@Override
	public Object call() throws Exception {
		System.out.println("Consumer " + consumerId + " is trying to fetch the data");
		ConsumerRecords<String,String> records = consumer.poll(timeOut);
		records.forEach(record -> {
			System.out.println("Consumer " + consumerId + "belonging to consumer group "+this.groupId +" has consumed the messaged with offset " + record.offset());
		});
		if(records.count()>0) 
			return true;
		else 
			return false;
	}
}