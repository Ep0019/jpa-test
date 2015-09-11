package com.lhy.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.lhy.netty.entity.Code;
import com.lhy.netty.entity.Ticket;
import com.lhy.netty.entity.Train;
import com.lhy.netty.msg.BookRequestMsg;
import com.lhy.netty.msg.BookResponseMsg;

/**
 * @author: 李慧勇
 * @description:订票服务器
 * @mail:jdlglhy@163.com
 * @2015年7月7日
 * @version 1.0
 */
public class BookTicketServer {
	
	public static List<Train> trains;
    /**
     * 初始化 构造车次和车票余数
     */
    public BookTicketServer() {
        trains=new ArrayList<Train>();
        trains.add(new Train("G242",500));
        trains.add(new Train("G243",200));
        trains.add(new Train("D1025",100));
        trains.add(new Train("D1235",0));
    }
    public void bind(int port) throws Exception{
        //配置NIO线程组
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try{
            //服务器辅助启动类配置
            ServerBootstrap b=new ServerBootstrap();
            b.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 100)
            .handler(new LoggingHandler(LogLevel.INFO))
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    //添加对象解码器 负责对序列化POJO对象进行解码 设置对象序列化最大长度为1M 防止内存溢出
                    //设置线程安全的WeakReferenceMap对类加载器进行缓存 支持多线程并发访问  防止内存溢出 
                    ch.pipeline().addLast(new ObjectDecoder(1024*1024,ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                    //添加对象编码器 在服务器对外发送消息的时候自动将实现序列化的POJO对象编码
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new BookTicketServerhandler());
                }
            });
            //绑定端口 同步等待绑定成功
            ChannelFuture f=b.bind(port).sync();
            //等到服务端监听端口关闭
            f.channel().closeFuture().sync();
        }finally{
            //优雅释放线程资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    /**
     * 订票server端处理器
     * @author xwalker
     *
     */
    public class BookTicketServerhandler extends ChannelHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg)
                throws Exception {
            BookRequestMsg requestMsg=(BookRequestMsg) msg;
            BookResponseMsg responseMsg=null;
            switch (requestMsg.getCode()) {
            case Code.CODE_SEARCH://查询余票
                for(Train train:BookTicketServer.trains){
                    //找到车次与请求车次相同的 返回车次余票
                    if(requestMsg.getTrainNumber().equals(train.getNumber())){
                        responseMsg=new BookResponseMsg();
                        responseMsg.setUser(requestMsg.getUser());
                        responseMsg.setCode(Code.CODE_SEARCH);
                        responseMsg.setSuccess(true);
                        responseMsg.setTrain(train);
                        responseMsg.setStartTime(requestMsg.getStartTime());
                        responseMsg.setMsg("火车【"+train.getNumber()+"】余票数量为【"+train.getTicketCounts()+"】");
                        break;
                    }
                }
                if(responseMsg==null){
                    responseMsg=new BookResponseMsg();
                    responseMsg.setUser(requestMsg.getUser());
                    responseMsg.setCode(Code.CODE_SEARCH);
                    responseMsg.setSuccess(false);
                    responseMsg.setMsg("火车【"+requestMsg.getTrainNumber()+"】的信息不存在！");
                }
                break;
            case Code.CODE_BOOK://确认订票
                for(Train train:BookTicketServer.trains){
                    //找到车次与请求车次相同的 返回车次余票
                    if(requestMsg.getTrainNumber().equals(train.getNumber())){
                        responseMsg=new BookResponseMsg();
                        responseMsg.setUser(requestMsg.getUser());
                        responseMsg.setSuccess(true);
                        responseMsg.setCode(Code.CODE_BOOK);
                        responseMsg.setMsg("恭喜您，订票成功！");
                        Ticket ticket=new Ticket();
                        ticket.setBookTime(new Date());
                        ticket.setUser(requestMsg.getUser());
                        ticket.setStartTime(requestMsg.getStartTime());
                        ticket.setNumber(train.getNumber()+System.currentTimeMillis());//生成车票编号
                        ticket.setCarriageNumber(new Random().nextInt(15));//随机车厢
                        ticket.setUser(requestMsg.getUser());//设置订票人信息
                        String[] seat=new String[]{"A","B","C","D","E"};
                        Random seatRandom=new Random();
                        ticket.setSeatNumber(seat[seatRandom.nextInt(5)]+seatRandom.nextInt(100));
                        ticket.setTrainNumber(train.getNumber());
                        train.setTicketCounts(train.getTicketCounts()-1);//余票减去一张
                        responseMsg.setTrain(train);
                        responseMsg.setTicket(ticket);
                        break;
                    }
                }
                if(responseMsg==null){
                    responseMsg=new BookResponseMsg();
                    responseMsg.setUser(requestMsg.getUser());
                    responseMsg.setCode(Code.CODE_BOOK);
                    responseMsg.setSuccess(false);
                    responseMsg.setMsg("火车【"+requestMsg.getTrainNumber()+"】的信息不存在！");
                }
                break;
            default://无法处理
                    responseMsg=new BookResponseMsg();
                    responseMsg.setUser(requestMsg.getUser());
                    responseMsg.setCode(Code.CODE_NONE);
                    responseMsg.setSuccess(false);
                    responseMsg.setMsg("指令无法处理！");
                break;
            }
             
            ctx.writeAndFlush(responseMsg);
        }
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public static void main(String[] args) throws Exception {
        int port =8000;
        new BookTicketServer().bind(port);
    }

}
