###Kafka_Consumer_Queue
This is a Kafka consumer which consumes data from topic. This consumer is example of queue.
In messagging systems, there are two entities : 
1. Queue - Point to point 
2. Topic - Publisher Subscriber
Kafka has taken this to a level of abstraction where there are no two entities. There is only one entity named as topic. On the consumer side, there are consumer group. If you want use Kafka for queueing model then you can have a consumers which subscribe to a topic have same consumer group id.
Only one (any one) consumers which belong to consumer group can consume the message.
If you want to use Kafka for topic model then you can have each consumer should have different consumer group id. That means all consumers will receive the message i.e publish-subscribed mode.    
This class demonstrates Kafka usage for queue modeling. All the consumers use the belong to the same consumer group id. User can start the KafkaQueueConsumer from different hosts just to simulate production kind of scenario. User can just fork different Java processes on the same machine. Only one consumer will receive the message. Multiple consumers which belong to same consumer group are used for fault tolerance in case one consumer process is crashed and the other purpose is parallelism.

### How to build?

The project is based on maven and can be built using maven commands.
Run mvn install or run mvn package.
It requires Java 1.8

###  How to run?
1. Start zookeeper service
   bin/zookeeper-server-start.sh -daemon config/zookeeper.properties
2. Start Kafka broker
   bin/kafka-server-start.sh config/server.properties
3. Run KafkaQueueConsumerTest
