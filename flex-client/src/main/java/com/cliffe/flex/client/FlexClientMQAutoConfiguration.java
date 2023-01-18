package com.cliffe.flex.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Cliffe
 * @Date: 2023-01-11 10:37 p.m.
 */
public class FlexClientMQAutoConfiguration {
	@Configuration
	public class MessageQueueAutoConfiguration {

//		@Bean
//		@ConditionalOnProperty(name = "message.queue", havingValue = "rabbitmq")
//		public RabbitMqConfig rabbitMqConfig() {
//			return new RabbitMqConfig();
//		}
//
//		@Bean
//		@ConditionalOnProperty(name = "message.queue", havingValue = "kafka")
//		public KafkaConfig kafkaConfig() {
//			return new KafkaConfig();
//		}
	}

//	@Configuration
//	@ConditionalOnProperty(name = "mq.type", havingValue = "rabbitmq")
//	public class RabbitMqConfiguration {
//    ...
//	}
//
//	@Configuration
//	@ConditionalOnProperty(name = "mq.type", havingValue = "kafka")
//	public class KafkaConfiguration {
//    ...
//	}


}
