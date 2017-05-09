package com.example.liangjian.servesocketdemo;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by LiangJian on 2017/5/8.
 */

public class ImageUploadHandler implements IResourceUriHandler {
    private String uriprefix = "/imageupload/";
    private Context context;
    public ImageUploadHandler(Context context_sub){

        this.context = context_sub;
    }
    @Override
    public boolean accept(String uri) {
        return uri.startsWith(uriprefix);
    }

    @Override
    public void handler(String uri, HttpContext httpContext) {
        Log.e("uri",uri);
        try {
            OutputStream os = httpContext.getSocket().getOutputStream();
            PrintWriter write = new PrintWriter(os);
            write.println("HTTP/1.1 200 ok");
            write.println();
            write.println("image upload  handler");
            write.flush();
            write.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
