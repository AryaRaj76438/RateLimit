package com.quantumsyntax.slidingwindow;

import com.quantumsyntax.slidingwindow.interceptor.RateLimiterInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@SpringBootApplication
public class SlidingwindowApplication implements WebMvcConfigurer {

	@Autowired @Lazy
	private RateLimiterInterceptor rateLimiterInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(rateLimiterInterceptor)
				.addPathPatterns("/api/calculator/**");
	}

	public static void main(String[] args) {
		SpringApplication.run(SlidingwindowApplication.class, args);
	}
}
