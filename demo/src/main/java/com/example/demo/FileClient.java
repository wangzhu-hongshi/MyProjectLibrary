package com.example.demo;

import java.io.*;
import java.net.Socket;

/**
 * 文件上传的客户端
 * 从本地读取文件长传到服务器
 */
public class FileClient {
    public static void main(String[] args) throws IOException {
        //

        FileInputStream fis=new FileInputStream(new File("d://as.jpg"));
        Socket socket=new Socket("localhost",8888);
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();
        int len=0;
        byte[] bytes=new byte[1024];
        while ((len=fis.read(bytes))!=-1){
            os.write(bytes,0,len);
        }
        //关闭输出流 通知服务器，写出数据完毕
        socket.shutdownOutput();
        while ((len=is.read(bytes))!=-1){
            System.out.println(new String(bytes,0,len));
        }
        socket.close();
        fis.close();


    }
}
