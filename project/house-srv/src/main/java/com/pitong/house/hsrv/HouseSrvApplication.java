package com.pitong.house.hsrv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

import com.pitong.house.hsrv.config.NewRuleConfig;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@RibbonClient(name = "user", configuration = NewRuleConfig.class)
@EnableSwagger2
public class HouseSrvApplication {

	public static void main(String[] args) {
		SpringApplication.run(HouseSrvApplication.class, args);
	}

}
