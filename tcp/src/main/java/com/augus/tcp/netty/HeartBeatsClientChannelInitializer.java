package com.augus.tcp.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @类名 HeartBeatsClientChannelInitializer
 * @类描述 <pre></pre>
 * @作者 duanXy
 * @创建时间 11:07$ 2018/11/28$
 * @版本 1.0
 * @修改记录 <pre>
 *      版本          时间          创建人         修改内容描述
 *    --------------------------------------------------------------
 *      1.00        11:07 2018/11/28         Administrator
 * </pre>
 */
public class HeartBeatsClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast("handler", new IdleStateHandler(0, 3, 0, TimeUnit.SECONDS));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast(new HeartBeatClientHandler());
    }
}