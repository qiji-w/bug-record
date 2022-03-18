package com.bug.bugrecordbackend.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.session.FlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds= 1800, flushMode = FlushMode.ON_SAVE, redisNamespace = "bug_record")
//@EnableRedisHttpSession
public class RedisSessionConfig {

}
