package com.example.lj.paymentmanagement;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by lj on 15/10/26.
 */
public class getRawAudioDataFromUrlThread {
    public String url;
    public Integer port;
    private Socket socket;

    public getRawAudioDataFromUrlThread(){}

    public getRawAudioDataFromUrlThread(String url, Integer port){
        this.url = url;
        this.port = port;
    }

    public void run(){

        try{
            socket = new Socket(this.url, this.port);
            InputStream inputStream = socket.getInputStream();
            Scanner inputScanner = new Scanner(inputStream);
        }catch (Exception e){
            System.out.println("open url erros");
            e.printStackTrace();
        }

    }

}
