package de.miwoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@SpringBootApplication
public class RedisSenderApplication {

	static Logger LOGGER = LoggerFactory.getLogger(RedisSenderApplication.class);


	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
		jedisConFactory.setHostName("localhost");
		jedisConFactory.setPort(6379);
		return jedisConFactory;
	}

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(RedisSenderApplication.class, args);

		StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
		LOGGER.info("Sending message...");
		template.convertAndSend("chat", "Hello from Redis!");


		System.exit(0);

	}
}
