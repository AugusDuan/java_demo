package com.augus.tcp.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @类名 HeartBeatsClient
 * @类描述 <pre></pre>
 * @作者 duanXy
 * @创建时间 11:06$ 2018/11/28$
 * @版本 1.0
 * @修改记录 <pre>
 *      版本          时间          创建人         修改内容描述
 *    --------------------------------------------------------------
 *      1.00        11:06 2018/11/28         Administrator
 * </pre>
 */
public class HeartBeatsClient {
    private  int port;
    private  String address;

    public HeartBeatsClient(int port, String address) {
        this.port = port;
        this.address = address;
    }

    public void start(){
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new HeartBeatsClientChannelInitializer());

        try {
            ChannelFuture future = bootstrap.connect(address,port).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        HeartBeatsClient client = new HeartBeatsClient(7788,"127.0.0.1");
        client.start();
    }
}