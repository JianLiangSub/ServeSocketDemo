package com.example.liangjian.servesocketdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SimpleHttpServer simpleHttpServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebConfiguration configuration = new WebConfiguration();
        configuration.port = 8088;
        configuration.maxParallels = 50;
        simpleHttpServer = new SimpleHttpServer(configuration);
        simpleHttpServer.registerHandler(new ImageUploadHandler(this));
        simpleHttpServer.registerHandler(new ResourceInAssetsHandler(this));
        simpleHttpServer.startServer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            simpleHttpServer.closeServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
