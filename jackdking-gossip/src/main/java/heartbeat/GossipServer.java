package heartbeat;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName GossipClient
 * @Description TODO
 * @Author jackdking
 * @Date 23/06/2022 2:37 下午
 * @Version 2.0
 **/
public class GossipServer {

    /**
     * 客户端集合
     */
    private static List<Socket> clients = new ArrayList<>();

    /**
     * 客户端最近的一次心跳时间集合
     */
    private static Map<Socket, Date> heartbeatMap = new HashMap<>();

    /**
     * 心跳超时时间，超过了就认为client下线了
     */
    private static final long TIMEOUT = 10 * 1000;

    private Integer port ;

    GossipServer(){
    }

    GossipServer(Integer port){
        this.port = port;
    }

    private void start() {
        try {
            // 开启服务，设置指定端口
            ServerSocket server = new ServerSocket(port);
            System.out.println("服务开启，等待客户端连接中...");
            // 循环监听
            while (true) {
                // 等待客户端进行连接
                Socket client = server.accept();
                // 将客户端添加到集合
                clients.add(client);
                // 连接上来，立即添加一次心跳时间，否则会导致MessageListener开启不了
                heartbeatMap.put(client, new Date());

                System.out.println("客户端[" + client.getRemoteSocketAddress() + "]连接成功，当前在线客户端" + clients.size() + "个");

                // 每一个客户端开启一个线程处理消息
                new MessageListener(client).start();
                // 每一个客户端开启一个线程监测心跳
                new HeartbeatListener(client).start();
            }
        } catch (IOException e) {
            // log
        }
    }

    /**
     * 发送消息
     *
     * @param type   消息类型（0、系统消息；1、客户端消息）
     * @param msg    消息内容
     * @param client 客户端
     * @throws IOException
     */
    private void sendMsg(int type, String msg, Socket client) throws IOException {
        if (type != 0) {
            System.out.println("处理消息：" + msg);
        }
        OutputStream os;
        PrintWriter pw;
        for (Socket socket : clients) {
            // 1、如果是系统消息，那就群发；
            // 2、如果是客户端消息，转发时就需要跳过客户端自己；
            if (type != 0 && socket == client) {
                continue;
            }
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.println(msg);// 这里需要特别注意，对方用readLine获取消息，就必须用print而不能用write，否则会导致消息获取不了
            pw.flush();
        }
    }

    /**
     * 接收消息
     *
     * @param client 客户端
     * @return 消息内容
     * @throws IOException
     */
    private String receiveMsg(Socket client) throws IOException {
        InputStream is = client.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        return br.readLine();
    }

    /**
     * 消息处理线程，负责转发消息到聊天室里的人
     */
    class MessageListener extends Thread {

        // 将每个连接上的客户端传递进来，收消息和发消息
        private Socket client;

        public MessageListener(Socket socket) {
            this.client = socket;
        }

        @Override
        public void run() {
            try {
                // 每个客户端连接上了，就发送一条系统消息（类似于广播）
                sendMsg(0, "[系统消息]：欢迎" + client.getRemoteSocketAddress() + "来到聊天室，当前共有" + clients.size() + "人在聊天", client);
                String msg;
                // 不再使用while(true)了，避免在客户端下线了之后，线程还依旧在无限循环
                while (heartbeatMap.get(client) != null) {
                    msg = receiveMsg(client);

                    // 如果客户端已经断开连接，但是心跳间隔时间又还没到的这一段时间（本案例中是2秒），会导致接收消息的readLine()一直为null
                    if (msg == null) {
                        continue;
                    }

                    if ("heartbeat".equals(msg)) {
                        // 记录客户端的心跳时间
                        heartbeatMap.put(client, new Date());
                        // 发送回执消息（主要用来给客户端做监测使用，可以知道服务器是否健康）
                        OutputStream os = client.getOutputStream();
                        PrintWriter pw = new PrintWriter(os);
                        pw.println("heartbeat_receipt");
                        pw.flush();
                    } else if ("exit".equals(msg)) {
                        // 客户端主动下线，需要移除相应的记录
                        heartbeatMap.remove(client);
                        clients.remove(client);
                        // 发送回执消息（客户端需要确保服务器知道我要下线了，才会退出）
                        OutputStream os = client.getOutputStream();
                        PrintWriter pw = new PrintWriter(os);
                        pw.println("exit_receipt");
                        pw.flush();

                        System.out.println("[" + client.getRemoteSocketAddress() + "]已下线，当前在线客户端" + clients.size() + "个");

                        // 发送广播
                        sendMsg(0, "[系统消息]：" + client.getRemoteSocketAddress() + "已下线，当前共有" + clients.size() + "人在聊天", client);
                    } else {
                        sendMsg(1, "[" + client.getRemoteSocketAddress() + "]：" + msg, client);
                    }
                }
            } catch (IOException e) {
                // log
            }
        }
    }

    /**
     * 心跳监测线程，负责客户端的下线提醒
     */
    class HeartbeatListener extends Thread {

        // 将每个连接上的客户端传递进来，收消息和发消息
        private Socket client;

        public HeartbeatListener(Socket socket) {
            this.client = socket;
        }

        @Override
        public void run() {
            try {
                // 比对时间来监测客户端是否已经下线了
                Date time, now;
                // 不再使用while(true)了，避免在客户端下线了之后，线程还依旧在无限循环
                while ((time = heartbeatMap.get(client)) != null) {
                    now = new Date();
                    // 如果超过指定时间还没有心跳，那就视作已下线
                    if (now.getTime() - time.getTime() > TIMEOUT) {
                        // 移除记录
                        heartbeatMap.remove(client);
                        clients.remove(client);

                        System.out.println("[" + client.getRemoteSocketAddress() + "]已下线，当前在线客户端" + clients.size() + "个");

                        sendMsg(0, "[系统消息]：" + client.getRemoteSocketAddress() + "已下线，当前共有" + clients.size() + "人在聊天", client);
                    }
                }
            } catch (IOException e) {
                // log
            }
        }
    }

    public static void main(String[] args) {
        new GossipServer().start();
    }

}
