package com.example.liangjian.servesocketdemo;

import android.content.Context;
import android.icu.util.Output;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by LiangJian on 2017/5/8.
 */

public class ResourceInAssetsHandler implements  IResourceUriHandler {

    private String acceprprefix = "/static/";
    private Context context;

   public ResourceInAssetsHandler(Context context_sub){
       this.context = context_sub;
   }

    @Override
    public boolean accept(String uri) {
        return uri.startsWith(acceprprefix);
    }

    @Override
    public void handler(String uri, HttpContext httpContext) throws IOException {
//        Log.e("uri",uri);
        String content = uri.substring(acceprprefix.length());
        InputStream fis = context.getAssets().open(content);
        byte [] raw =  StreamToolkit.readRawFromStream(fis);
        fis.close();
       OutputStream ous =  httpContext.getSocket().getOutputStream();
        PrintStream printer = new PrintStream(ous);
        printer.println("HTTP/1.1 200 OK");
        printer.println("Content-length:"+raw.length);
        if(uri.endsWith(".html")){
            printer.println("Content-Type:text/html");
        }else if(uri.endsWith(".js")){
            printer.println("Content-Type:text/javaScript");
        }else if(uri.endsWith(".css")){
            printer.println("Content-Type:text/css");
        }else if(uri.endsWith(".jpg")){
            printer.println("Content-Type:text/jpg");
        }else if(uri.endsWith(".png")){
            printer.println("Content-Type:text/png");
        }
        printer.println();
        printer.write(raw);
        printer.flush();
//        printer.close();
//        try {
//            OutputStream os = httpContext.getSocket().getOutputStream();
//            PrintWriter writer = new PrintWriter(os);
//            writer.println("HTTP/1.1 200 ok");
//            writer.println();
//            writer.println("resource Assets handler");
//            writer.flush();
//            writer.close();
//            os.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
