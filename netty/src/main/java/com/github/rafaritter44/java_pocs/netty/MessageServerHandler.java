package com.github.rafaritter44.java_pocs.netty;

import java.util.HashMap;
import java.util.UUID;

import com.google.gson.Gson;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class MessageServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	
	private static final Gson GSON = new Gson();

	@Override
	public void channelRead0(final ChannelHandlerContext ctx, final FullHttpRequest req) {
		if (req.uri().startsWith("/api/message")) {
			if (req.method().equals(HttpMethod.POST)) {
				final MessageRequest requestBody = GSON.fromJson(req.content().toString(CharsetUtil.UTF_8), MessageRequest.class);
				final MessageResponse responseBody = MessageService.save(requestBody);
				final String jsonResponseBody = GSON.toJson(responseBody);
				final ByteBuf bufferResponseBody = Unpooled.copiedBuffer(jsonResponseBody, CharsetUtil.UTF_8);
				final FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CREATED, bufferResponseBody);
				response.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
				response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
				ctx.write(response);
			} else if (req.method().equals(HttpMethod.GET)) {
				final HashMap<UUID, MessageResponse> messages = MessageService.findAll();
				final String jsonResponseBody = GSON.toJson(messages);
				final ByteBuf bufferResponseBody = Unpooled.copiedBuffer(jsonResponseBody, CharsetUtil.UTF_8);
				final FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, bufferResponseBody);
				response.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
				response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
				ctx.write(response);
			}
		} else {
			final FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
			response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
			ctx.write(response);
		}
	}
	
	@Override
	public void channelReadComplete(final ChannelHandlerContext ctx) {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
	
}
