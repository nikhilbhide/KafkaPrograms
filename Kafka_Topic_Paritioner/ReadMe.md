###Kafka_Topic_Paritioner
This program provides custom partitioner for Kafka topic while producing message onto it.
#How do I choose the number of partitions for a topic?
Partition count determines the maximum consumer parallelism.
one should set a partition count based on the maximum consumer parallelism you would expect to need (i.e. over-provision).
Clusters with up to 10k total partitions are quite workable. 
For example : Consider Kafka for log use case. You may use one topic for logging and different topics for different log types such as activity, error, MDPs, web-services etc..

### How to build?

The project is based on maven and can be built using maven commands.
Run mvn install or run mvn package.
It requires Java 1.8

###  How to run?
1. Start zookeeper service
   bin/zookeeper-server-start.sh -daemon config/zookeeper.properties
2. Start Kafka broker
   bin/kafka-server-start.sh config/server.properties
3. Run TopicPartitionerTest
