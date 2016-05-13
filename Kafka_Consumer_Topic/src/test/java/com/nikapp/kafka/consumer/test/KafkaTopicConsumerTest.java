package com.nikapp.kafka.consumer.test;

import static org.junit.Assert.*;
import java.util.concurrent.ExecutionException;
import org.junit.Test;
import com.nikapp.kafka.consumer.KafkaTopicConsumer;
import com.nikapp.kafka.producer.KafkaDataProducer;

public class KafkaTopicConsumerTest {
	@Test
	public void testTopicConsumer() throws InterruptedException, ExecutionException {
		KafkaDataProducer producer = new KafkaDataProducer();
		KafkaTopicConsumer consumerGroup1 = new KafkaTopicConsumer(2,1000,"TestGroup1");
		consumerGroup1.start(1000,false);
		KafkaTopicConsumer consumerGroup2 = new KafkaTopicConsumer(2,1000,"TestGroup2");
		consumerGroup2.start(1000,false);
		producer.produceData(1);
		assertEquals(consumerGroup1.start(1000,true),1);
		assertEquals(consumerGroup2.start(1000,true),1);
	}
}