package com.example.liangjian.servesocketdemo;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangJian on 2017/5/5.
 */

public class HttpContext {
    private Socket socket;
    private Map<String,String> requestHeaders ;
    public HttpContext(){
        requestHeaders = new HashMap<String,String>();
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public Socket getSocket() {
        return socket;
    }
    public void addRequestheadter(String header,String headerValue){
        requestHeaders.put(header,headerValue);
    }

    public String getHeadValue(String header){
        return requestHeaders.get(header);
    }
}
