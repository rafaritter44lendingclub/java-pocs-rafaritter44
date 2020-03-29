package com.github.rafaritter44.java_pocs.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class MessageServer {
	
	private final int port;
	
	public MessageServer(final int port) {
		this.port = port;
	}
	
	public void run() throws Exception {
		final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		final EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
        	final ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
        		.channel(NioServerSocketChannel.class)
        		.handler(new LoggingHandler(LogLevel.INFO))
            	.childHandler(new MessageServerInitializer())
            	.option(ChannelOption.SO_BACKLOG, 1024)
            	.childOption(ChannelOption.SO_KEEPALIVE, true);
            final ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
	}
	
	public static void main(final String[] args) throws Exception {
		final int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 8080;
		}
		new MessageServer(port).run();
	}

}
