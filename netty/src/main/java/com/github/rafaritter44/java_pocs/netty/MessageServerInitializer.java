package com.github.rafaritter44.java_pocs.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class MessageServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(final SocketChannel ch) throws Exception {
		ch.pipeline()
				.addLast(new HttpServerCodec())
				.addLast(new HttpObjectAggregator(Integer.MAX_VALUE))
				.addLast(new MessageServerHandler());
	}

}
