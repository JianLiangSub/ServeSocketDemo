package com.example.liangjian.servesocketdemo;

import android.util.Log;
import android.util.Xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by LiangJian on 2017/5/5.
 */

public class SimpleHttpServer {
    private boolean isEnable;

    private WebConfiguration webConfiguration;
    private InetSocketAddress socketAddress;
    private ServerSocket serverSocket;
    private ExecutorService threadPool;
    private Set<IResourceUriHandler> handlerSet;
    public SimpleHttpServer(WebConfiguration webConfiguration_new) {

        this.webConfiguration = webConfiguration_new;

        threadPool = Executors.newCachedThreadPool();

        handlerSet = new HashSet<>();
    }

    /*
    * 启动Server
    * */
    public void startServer() {
        isEnable = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                doProSync();
            }

        }).start();
    }

    private void doProSync() {
        try {
            socketAddress = new InetSocketAddress(webConfiguration.port);
            serverSocket = new ServerSocket();
            serverSocket.bind(socketAddress);
            while (isEnable){
                final Socket socket = serverSocket.accept();
                /*创建线程池防止阻塞*/
                threadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("client_ip",socket.getRemoteSocketAddress().toString());
                        onAcceptRemotePeer(socket);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerHandler(IResourceUriHandler handler){
            handlerSet.add(handler);
    }
    /*处理*/
    private void onAcceptRemotePeer(Socket socket) {
            if(socket != null){
                try {
                    HttpContext httpContext = new HttpContext();
                    httpContext.setSocket(socket);
//                    socket.getOutputStream().write("你好啊,我是Android服务器!".getBytes("GBK"));
//                    socket.getOutputStream().flush();
//                    socket.getOutputStream().close();
                    InputStream is = socket.getInputStream();
                    String header = null;
                    String content = StreamToolkit.readline(is).split(" ")[1];
                    while ((header = StreamToolkit.readline(is)) != null){
                        if(header.equals("\r\n")){
                            break;
                        }
                       String [] pair =  header.split(": ");
                        if(pair.length == 2){
                            httpContext.addRequestheadter(pair[0],pair[1]);
                        }
//                        Log.e("client_info",header+"----");
                    }

                    for (IResourceUriHandler handlerResource:handlerSet){
                        if(!handlerResource.accept(content)){
                            continue;
                        }
                        handlerResource.handler(content,httpContext);
                    }
                    Log.e("headerKey",httpContext.getHeadValue("User-Agent"));
                } catch (IOException e) {
                    Log.e("client_exception",e.getMessage());
                }
            }
    }

    /*
    * 关闭Server
    * */
    public void closeServer() throws IOException {
        if(!isEnable){
            return;
        }
        isEnable = false;
        serverSocket.close();
        serverSocket = null;
    }
}
