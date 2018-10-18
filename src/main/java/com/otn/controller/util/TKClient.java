package com.otn.controller.util;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * @Auther: 李景然
 * @Date: 2018/5/25 16:26
 * @Description:
 */

public class TKClient {
    String host; // 要连接的服务端IP地址
    int port = 8899; // 要连接的服务端对应的监听端口
    ClientThread thread = null;
    Socket client = null;
    Writer writer = null;

    public TKClient() {
        // TODO Auto-generated constructor stub
        try {
            Properties prop = new Properties();
            //filepath-->/F:/AllInstallBag/apache-tomcat-9.0.0.M21/webapps/ROOT/WEB-INF/classes/
            String filepath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            prop.load(new FileInputStream(filepath + "socketConfig.properties"));
            host = prop.getProperty("serverIp");
            initSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initSocket() {
        try {
            client = new Socket(this.host, this.port);
            writer = new OutputStreamWriter(client.getOutputStream());
            // 建立连接后就可以往服务端写数据了
            thread = new ClientThread(client, this);
            thread.start();
            System.out.println("客户端" + this.client.getLocalPort() + " 已经连上服务器");
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("客户端" + this.client.getLocalPort() + " 不能连接上服务器");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("客户端" + this.client.getLocalPort() + " 不能连接上服务器");
        }
    }

    public void sendMessage(String msg) {
        // TODO Auto-generated method stub
        try {
            //System.out.println("客户端" + this.client.getLocalPort() + "发送消息---" + msg.substring(0, 100));
            System.out.println("客户端" + this.client.getLocalPort() + "发送消息，长度===" + msg.length());
            writer.write(msg);
            writer.flush();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}

class ClientThread extends Thread {

    private Socket socket = null;
    private Reader reader = null;
    private int len = 0;
    char chars[] = new char[130000000];
    private TKClient client = null;
    private String temp = "";

    public ClientThread(Socket socket, TKClient client) {
        // TODO Auto-generated constructor stub
        this.socket = socket;
        this.client = client;
        try {
            reader = new InputStreamReader(socket.getInputStream());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        while (true) {
            try {
                if (socket.isClosed() == false) {
                    if (socket.isInputShutdown() == false) {
                        while ((len = ((Reader) reader).read(chars)) != -1) {
                            temp = "客户端--收到服务器消息——>" + new String(chars, 0, len);
                            System.out.println("客户端--收到服务器消息的长度len=======" + len);
                        }
                    }
                } else {
                    if (socket.getKeepAlive() == false) {
                        reader.close();
                        socket.close();
                        this.stop();
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("服务器退出 Disconnected from server");
                //e.printStackTrace();
                break;
            }
        }
    }
}


