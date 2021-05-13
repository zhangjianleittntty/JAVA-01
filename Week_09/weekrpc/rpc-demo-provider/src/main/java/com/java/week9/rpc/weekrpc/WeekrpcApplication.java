package com.java.week9.rpc.weekrpc;

import com.alibaba.fastjson.JSON;
import com.java.week9.rpc.api.Order;
import com.java.week9.rpccore.api.RpcResponse;
import com.java.week9.rpccore.server.nettyserver.server.HttpInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@SpringBootApplication
//@RestController
public class WeekrpcApplication implements CommandLineRunner {

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

//	@GetMapping("/test")
//	public RpcResponse invoke1() {
//		RpcResponse rpcResponse = new RpcResponse();
//
//		// Order
//		Order order = Order.builder().id(1).name("ccccc").amount(new BigDecimal("456")).build();
//		rpcResponse.setStatus(true);
//		rpcResponse.setResult(JSON.toJSON(order));
//		return rpcResponse;
//	}


	@Override
	public void run(String... args) throws Exception {
		int port = 8085;

		EventLoopGroup bossGroup = new NioEventLoopGroup(2);
		EventLoopGroup workerGroup = new NioEventLoopGroup(16);

		try {
			ServerBootstrap b = new ServerBootstrap();
//            b.option(ChannelOption.TCP_NODELAY,true);
//			b.group(bossGroup,workerGroup)
//					.channel(NioServerSocketChannel.class)
//					.handler(new LoggingHandler(LogLevel.INFO))
//					.option(ChannelOption.SO_BACKLOG,128)
//					.childOption(ChannelOption.TCP_NODELAY, true)
//					.childHandler(new HttpInitializer());
//                    .childOption(ChannelOption.SO_KEEPALIVE, true)
//                    .childOption(ChannelOption.SO_REUSEADDR, true)
//                    .childOption(ChannelOption.SO_RCVBUF, 32 * 1024)
//                    .childOption(ChannelOption.SO_SNDBUF, 32 * 1024)
//                    .childOption(EpollChannelOption.SO_REUSEPORT, true)
//                    .childOption(ChannelOption.SO_KEEPALIVE, true)
//                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

			//.handler(new LoggingHandler(LogLevel.INFO))

			b.group(bossGroup,workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG,128)
					.childOption(ChannelOption.TCP_NODELAY, true)
					.childHandler(new HttpInitializer());

			Channel ch = b.bind(port).sync().channel();
			System.out.println("开启netty http服务器，监听地址和端口为 http://127.0.0.1:" + port + '/');
			ch.closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
