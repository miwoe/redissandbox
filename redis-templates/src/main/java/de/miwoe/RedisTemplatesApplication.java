package de.miwoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;

import java.util.Map;
import java.util.UUID;

@SpringBootApplication
public class RedisTemplatesApplication {

	static Logger LOGGER = LoggerFactory.getLogger(RedisTemplatesApplication.class);


	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(connectionFactory);
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

		ApplicationContext ctx = SpringApplication.run(RedisTemplatesApplication.class, args);

		Person p = new Person();
		p.setId(UUID.randomUUID());
		p.setFirstName("Hans");
		p.setLastName("Wurst");
		Address address = new Address();
		address.setCity("Duisburg");
		address.setStreet("Sanboxplatz 1");
		p.setAddress(address);
		StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);

		HashMapper<Object, String, Object> mapper = new Jackson2HashMapper(true);
		Map<String, Object> mappedHash = mapper.toHash(p);

		template.opsForHash().putAll(p.getId().toString(), mappedHash);

		HashOperations x = template.opsForHash();
		Person loaded = (Person) mapper.fromHash(x.entries(p.getId().toString()));
		System.out.println(loaded.getLastName());


		System.exit(0);
	}
}
