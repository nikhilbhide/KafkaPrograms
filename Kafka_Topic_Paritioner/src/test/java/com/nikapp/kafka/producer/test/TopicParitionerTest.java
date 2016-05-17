package com.nikapp.kafka.producer.test;

import org.junit.Test;
import static org.junit.Assert.*;
import com.nikapp.kafka.topics.paritioner.TopicParitioner;

public class TopicParitionerTest {
	@Test
	public void checkParitionValue_InputKeyStartingWithSmallLetterg_ReturnAscciCode103() {
		TopicParitioner topicParitioner = new TopicParitioner();
		assertEquals(topicParitioner.partition(null, "google", null, null, null,null), 103);
	}

	@Test
	public void checkParitionValue_InputKeyStartingWithBigLetterG_ReturnAscciCode103() {
		TopicParitioner topicParitioner = new TopicParitioner();
		assertEquals(topicParitioner.partition(null, "Google", null, null, null,null), 71);
	}

	@Test
	public void checkParitionValue_InputKeyWithByteKey_ReturnNumber0() { 
		TopicParitioner topicParitioner = new TopicParitioner();
		assertEquals(topicParitioner.partition(null, "google".getBytes(), null, null, null,null), 0);
	}
	
	@Test
	public void checkParitionValue_InputKeyWithNullKey_ReturnNumber0() { 
		TopicParitioner topicParitioner = new TopicParitioner();
		assertEquals(topicParitioner.partition(null, "google".getBytes(), null, null, null,null), 0);
	}
}