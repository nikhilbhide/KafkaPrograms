package com.nikapp.kafka.topics.paritioner;

import java.util.Map;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

public class TopicParitioner implements Partitioner {
	@Override
	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes,
			Cluster cluster) {
		if(key instanceof String) {
			String messageKey = (String) key;
			int hash = messageKey.charAt(0);
			return hash;
		}
		return 0;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}			
}