package com.example.liangjian.servesocketdemo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LiangJian on 2017/5/5.
 */

public class StreamToolkit {
    public static final String readline(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        int c1 = 0;
        int c2 = 0;
        while (c2 != -1 && !(c1 == '\r' && c2 == '\n')){
                c1 = c2;
                c2 = inputStream.read();
                builder.append((char)c2);
        }
        if(builder.length() == 0){
            return null;
        }
        return builder.toString();

    }

    public static byte[] readRawFromStream(InputStream fis) throws IOException {
        ByteArrayOutputStream ops = new ByteArrayOutputStream();
        byte [] buffer = new byte[10240];
        int reader = 0;
        while ((reader = fis.read(buffer)) != -1){
            ops.write(buffer,0,reader);
        }
        return ops.toByteArray();
    }
}
