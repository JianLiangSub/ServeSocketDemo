package com.example.liangjian.servesocketdemo;

import java.io.IOException;

/**
 * Created by LiangJian on 2017/5/8.
 */

public interface IResourceUriHandler {
    boolean accept(String uri);
    void handler(String uri,HttpContext httpContext) throws IOException;
}
