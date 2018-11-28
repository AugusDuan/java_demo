package com.augus.tcp.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @类名 HeartBeatServer
 * @类描述 <pre></pre>
 * @作者 duanXy
 * @创建时间 11:00$ 2018/11/28$
 * @版本 1.0
 * @修改记录 <pre>
 *      版本          时间          创建人         修改内容描述
 *    --------------------------------------------------------------
 *      1.00        11:00 2018/11/28         Administrator
 * </pre>
 */
public class HeartBeatServer {
    private int port;

    public HeartBeatServer(int port) {
        this.port = port;
    }

    public void start(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap server = new ServerBootstrap().group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new HeartBeatServerChannelInitializer());

        try {
            ChannelFuture future = server.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        HeartBeatServer server = new HeartBeatServer(7788);
        server.start();
    }
}
