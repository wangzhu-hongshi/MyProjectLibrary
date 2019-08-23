package cn.wang.conre;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    /**
     * 使用MVC框架的方式上传文件
     *
     * @param request
     * @param upload
     * @return
     * @throws IOException
     */
    @RequestMapping("/tofile")
    public String tofile(HttpServletRequest request, MultipartFile upload) throws IOException {
        System.out.println("使用MVC的方式上传文件");
        //先获取上传的路径
        String path = request.getSession().getServletContext().getRealPath("/uploads");
        // 创建File对象，一会向该路径下上传文件
        File file = new File(path);
        System.out.println(file);
        //判断路径是否存在
        if (!file.exists()) {
            //不存在创建文件夹
            file.mkdirs();
        }
        //获取上传文件的名字
        String Filename = upload.getOriginalFilename();
        //给文件设置一个唯一名
        String uid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        Filename = uid + "-" + Filename;
        //上传文件
        upload.transferTo(new File(file, Filename));
        return "success";
    }

    /**
     * 使用跨服务器上传文件
     *
     * @param request
     * @param upload
     * @return
     * @throws IOException
     */
    @RequestMapping("/tofile")
    public String tofile2(MultipartFile upload) throws IOException {
        System.out.println("跨服务器方式上传文件");
        //定义图片服务器的请求路径
        String path = "http://localhost:9090/fileuploadserver_war/";
        //判断路径是否存在
//        File file=new File(path);
//        if(file.exists()){
//            file.mkdirs();
//        }

        //获取上传文件的名字
        String Filename = upload.getOriginalFilename();
        //给文件设置一个唯一名
        String uid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        Filename = uid + "-" + Filename;

        // 创建客户端对象
         //Client client = Client.create();

        // 连接图片服务器
        // WebResource webResource = client.resource(path+filename);
        // 上传文件
         //webResource.put(upload.getBytes());

        return "success";
    }
}
