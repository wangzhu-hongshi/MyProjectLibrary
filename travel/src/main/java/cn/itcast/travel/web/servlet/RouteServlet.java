package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.impl.CategorySetviceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 分页查询
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private CategorySetviceImpl csi=new CategorySetviceImpl();
    private RouteServiceImpl rsi=new RouteServiceImpl();
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String currentPages = request.getParameter("currentPage");
        String pageSizes = request.getParameter("pageSize");
        String cids = request.getParameter("cid");//获取cid
        //获取rname模糊查询的值
        String rname = request.getParameter("rname");
        //解码
        if(rname!=null&&rname.length()>0){
            rname =new String(rname.getBytes("iso-8859-1"),"utf-8");
        }

        // 把参数转换为int类型的 并设定默认值
        int cid=0;
        if(cids!=null&&cids.length()>0) {
            cid = Integer.parseInt(cids);
        }
        System.out.println(cid);
        int currpage=0;
        if(currentPages!=null&&currentPages.length()>0){
            currpage=Integer.parseInt(currentPages);
        }else {
            currpage=1;
        }
        int pageSize=0;
        if(pageSizes!=null&&pageSizes.length()>0){
            pageSize=Integer.parseInt(pageSizes);
        }else {
            pageSize=5;
        }
        //调用service 查询pageBean对象
        PageBean<Route> pageBean=csi.pageQuery(cid,currpage,pageSize,rname);
        writeValue(pageBean,response);

    }

    /**
     *根据rid查询一个旅游线路的详细内容
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取参数
        String rid = request.getParameter("rid");
        //2.调用service
        Route route = rsi.findOne(rid);
        //3.ajax返回
        writeValue(route,response);
    }
}
