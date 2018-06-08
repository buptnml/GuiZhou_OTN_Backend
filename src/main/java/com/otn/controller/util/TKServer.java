package com.otn.controller.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.otn.pojo.MaintenanceRecordDTO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

/**
 * @Auther: 李景然
 * @Date: 2018/5/25 16:28
 * @Description:
 */
public class TKServer {

    //clients中的key代表客户端的port
    private Map<Integer, Socket> clients = new HashMap<Integer, Socket>();
    private static TKServer tkServer;

    private TKServer() {
        // TODO Auto-generated constructor stub
    }

    public static TKServer getTkServer() {
        if (tkServer == null) {
            tkServer = new TKServer();
        }
        return tkServer;
    }

    public void listenClient() {
        String temp = "";
        try {
            //server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
            // 定义一个ServerSocket监听在端口8899上
            ServerSocket server = new ServerSocket(8899);
            while (true) {
                Socket socket = server.accept();
                clients.put(socket.getPort(), socket);
                temp = "客户端" + socket.getPort() + " 连接";
                System.out.println(temp);
                new ServerThread(socket, this).start();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void sendMsgToAll(Socket fromSocket, String msg) {
        Set<Integer> keset = this.clients.keySet();
        java.util.Iterator<Integer> iter = keset.iterator();
        while (iter.hasNext()) {
            int key = iter.next();
            Socket socket = clients.get(key);
            if (socket != fromSocket) {
                try {
                    if (socket.isClosed() == false) {
                        if (socket.isOutputShutdown() == false) {
                            Writer writer = new OutputStreamWriter(
                                    socket.getOutputStream());
                            writer.write(msg);
                            writer.flush();
                        }
                    }
                } catch (SocketException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }

    public void sendMessage(String temp) {
        // TODO Auto-generated method stub
        Set<Integer> keset = this.clients.keySet();
        java.util.Iterator<Integer> iter = keset.iterator();
        while (iter.hasNext()) {
            int key = iter.next();
            Socket socket = clients.get(key);
            try {
                Writer writer = new OutputStreamWriter(socket.getOutputStream());
                writer.write(temp);
                writer.flush();
            } catch (SocketException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
}

class ServerThread extends Thread {

    private Socket socket = null;
    private TKServer server = null;
    private InputStreamReader reader = null;
    char chars[] = new char[130000000];
    int len;
    private String temp = null;

    public ServerThread(Socket socket, TKServer server) {
        // TODO Auto-generated constructor stub
        this.socket = socket;
        this.server = server;
        init();
    }

    private void init() {
        try {
            reader = new InputStreamReader(socket.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            try {
                Thread.sleep(1000);
                int count = 0;
                //System.out.println("服务器子线程" + this.getId() + "收到消息");
                while ((len = ((Reader) reader).read(chars)) != -1) {
                    count++;
                    temp = new String(chars, 0, len);
                    //System.out.println("客户端" + socket.getPort() + "说-->" + temp);
                    //server.sendMsgToAll(this.socket, "客户端" + socket.getPort() + "说-->" + temp);
                    int length = temp.split("]").length;
                    System.out.println("第" + count + "次---服务器---收到客户端" + socket.getPort() + "的消息的长度 len===" + len + "最后几位是---" + temp.substring(temp.length() - 4, temp.length()) + ";]分割成了----" + length + "  个");
                    Gson gson = new Gson();
                    ArrayList<MaintenanceRecordDTO> list = gson.fromJson(temp, new TypeToken<ArrayList<MaintenanceRecordDTO>>() {
                    }.getType());
                    System.out.println("ArrayList<MaintenanceRecordDTO> list = gson.fromJson解析成功");
                }
                if (socket.getKeepAlive() == false) {
                    ((Reader) reader).close();
                    temp = "客户端" + socket.getPort() + "-->退出";
                    System.out.println(temp);
                    socket.close();
                    this.stop();
                }
            } catch (Exception e) {
                // TODO: handle exception
                //e.printStackTrace();
                temp = "客户端" + socket.getPort() + "-->退出";
                System.out.println(temp);
                break;
            }
        }
    }
}

