package com.nikapp.kafka.consumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * This is a Kafka consumer which consumes data from topic. This consumer is example of queue.
 *<p>
 *In messagging systems, there are two entities. 
 *1. Queue - Point to point 
 *2. Topic - Publisher Subscriber
 *Kafka has taken this to a level of abstraction where there are no two entities. There is only one entity named as topic.
 *<p>
 *On the consumer side, there are consumer group.
 *If you want use Kafka for queueing model then you can have a consumers which subscribe to a topic have same consumer group id. 
 *Only one  (any one) consumers which belong to consumer group can  consume the message.
 *If you want to use Kafka for topic model then you can have each consumer should have different consumer group id. That means all consumers will receive the message i.e publish-subscribed mode.    
 *<p>
 *This class demonstrates Kafka usage for Topic modeling. Consumers may belong to different group ids.
 *User can start the KafkaTopicConsumer from different hosts just to simulate production kind of scenario. User can just fork different Java processes on the same machine.
 * 
 * @author nikhil.bhide
 * @since 1.0 
 */
public class KafkaTopicConsumer {
	private int totalNumOfConsumers;
	private String groupId;
	private List<KafkaConsumerTask> kafkaConsumerTasks= new ArrayList();
	public KafkaTopicConsumer(int totalNumberOfConsumers, int timeOut, String groupId) {
		this.totalNumOfConsumers = totalNumberOfConsumers;
		IntStream.rangeClosed(1, totalNumberOfConsumers)
		.forEach(i->init(i, timeOut,groupId));				 
	}
	
	/**
	 * Creates Kafka consumer based on the properties set.
	 * The properties should be configured in properties file; however, over here properties are hardcoded since it is a demonstration project. 
	 * 
	 */
	private void init(int consumerId, int timeOut,String groupId) {
		this.groupId=groupId;
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", groupId);
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("auto.offset.reset", "latest");
		KafkaConsumer consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Arrays.asList("Kafka-Topic"));
		KafkaConsumerTask task = new KafkaConsumerTask(consumerId, consumer, timeOut, groupId);
		kafkaConsumerTasks.add(task);
	}

	/**
	 * Starts kafka consumer to fetch the messages
	 * 
	 */
	public int start(int timeOut, boolean closeConnection) throws InterruptedException, ExecutionException {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(totalNumOfConsumers);
		List<Future<Boolean>> resultList = new ArrayList<>();
		kafkaConsumerTasks.forEach(
				consumerTask-> {
					Future<Boolean> result = executor.submit(consumerTask);
					resultList.add(result);
				});
		 int successfulAcks = resultList.stream()
				.filter(res-> {
					try {
						return res.get()==true;	
					}
					catch (Exception e){
						throw new RuntimeException(e);
					}})
				.collect(Collectors.toList()).size();
		executor.shutdown();
		if(closeConnection) {
			kafkaConsumerTasks.parallelStream()
							   .filter(kafkaConsumerTask->kafkaConsumerTask.getGroupId().equals(this.groupId))
							  .forEach(kafkaConsumerTask-> kafkaConsumerTask.getConsumer().close());
		}
		return successfulAcks;
	}
}