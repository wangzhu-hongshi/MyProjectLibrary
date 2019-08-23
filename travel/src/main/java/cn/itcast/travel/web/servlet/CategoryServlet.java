package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.impl.CategorySetviceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/Category/*")
public class CategoryServlet extends BaseServlet {
    private CategorySetviceImpl csi=new CategorySetviceImpl();

    /**
     * 查询分类
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> list = csi.findAll();
//        ObjectMapper mapper=new ObjectMapper();
//        mapper.writeValue(response.getOutputStream(),list);
        writeValue(list,response);//调用父类自定义的方法
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
