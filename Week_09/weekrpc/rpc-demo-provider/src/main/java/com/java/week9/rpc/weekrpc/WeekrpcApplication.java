package com.java.week9.rpc.weekrpc;

import com.alibaba.fastjson.JSON;
import com.java.week9.rpc.api.Order;
import com.java.week9.rpccore.api.RpcResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@SpringBootApplication
@RestController
public class WeekrpcApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeekrpcApplication.class, args);
	}

//	@PostMapping("/")
//	public RpcResponse invoke() {
//		RpcResponse rpcResponse = new RpcResponse();
//
//		// Order
//		Order order = Order.builder().id(1).name("ccccc").amount(new BigDecimal("456")).build();
//		rpcResponse.setStatus(true);
//		rpcResponse.setResult(JSON.toJSON(order));
//		return rpcResponse;
//	}

	@GetMapping("/")
	public RpcResponse invoke1() {
		RpcResponse rpcResponse = new RpcResponse();

		// Order
		Order order = Order.builder().id(1).name("ccccc").amount(new BigDecimal("456")).build();
		rpcResponse.setStatus(true);
		rpcResponse.setResult(JSON.toJSON(order));
		return rpcResponse;
	}

}
