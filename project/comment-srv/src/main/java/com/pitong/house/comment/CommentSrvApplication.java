package com.pitong.house.comment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

import com.pitong.house.comment.config.NewRuleConfig;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@RibbonClients(value = { @RibbonClient(name = "user", configuration = NewRuleConfig.class),
		@RibbonClient(name = "house", configuration = NewRuleConfig.class) })
@EnableSwagger2
public class CommentSrvApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentSrvApplication.class, args);
	}

}

