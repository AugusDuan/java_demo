package com.augus.tcp.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @类名 HeartBeatServerHandle
 * @类描述 <pre></pre>
 * @作者 duanXy
 * @创建时间 11:04$ 2018/11/28$
 * @版本 1.0
 * @修改记录 <pre>
 *      版本          时间          创建人         修改内容描述
 *    --------------------------------------------------------------
 *      1.00        11:04 2018/11/28         Administrator
 * </pre>
 */
public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {
    private int loss_connect_time = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "Server :" + msg.toString());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            //服务端对应着读事件，当为READER_IDLE时触发
            IdleStateEvent event = (IdleStateEvent)evt;
            if(event.state() == IdleState.READER_IDLE){
                loss_connect_time++;
                System.out.println("接收消息超时");
                if(loss_connect_time > 2){
                    System.out.println("关闭不活动的链接");
                    ctx.channel().close();
                }
            }else{
                super.userEventTriggered(ctx,evt);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}