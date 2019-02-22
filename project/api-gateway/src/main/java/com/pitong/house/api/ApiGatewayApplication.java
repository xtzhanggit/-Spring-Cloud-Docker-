package com.pitong.house.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pitong.house.api.config.NewRuleConfig;

@SpringBootApplication
@EnableDiscoveryClient
@Controller
// @RibbonClient(name = "user", configuration = NewRuleConfig.class)
@RibbonClients(value = { @RibbonClient(name = "user", configuration = NewRuleConfig.class),
		@RibbonClient(name = "comment", configuration = NewRuleConfig.class),
		@RibbonClient(name = "house", configuration = NewRuleConfig.class) })
@EnableCircuitBreaker
@EnableHystrix
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping("index1")
	@ResponseBody
	public List<ServiceInstance> getReister() {
		return discoveryClient.getInstances("user");
	}

	// @RequestMapping("lulu")
	// @ResponseBody
	// public RestResponse<String> getusername(Long id) {
	// return RestResponse.success(userService.getusername(id));
	// }

}
