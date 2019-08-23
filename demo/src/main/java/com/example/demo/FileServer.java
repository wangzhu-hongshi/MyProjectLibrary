package com.example.demo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 文件上传的服务器
 * 从客户端读取文件 并写入本地磁盘
 */
public class FileServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(8888);
        while (true){
            Socket socket = serverSocket.accept();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = socket.getInputStream();
                        File file=new File("f://upl");
                        if(!file.exists()){
                            file.mkdirs();
                        }
                        FileOutputStream fos=new FileOutputStream(file+"/"+System.currentTimeMillis()+".jpg");
                        OutputStream os = socket.getOutputStream();
                        int len=0;
                        byte[] bytes=new byte[1024*8];
                        while ((len=is.read(bytes))!=1){
                            fos.write(bytes,0,len);
                        }
                        os.write("上传成功".getBytes());
                        fos.close();

                        socket.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();


        }

    }
}
