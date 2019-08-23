package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDaoImpl routeDao=new RouteDaoImpl();
    private RouteImgDaoImpl routeImgDao=new RouteImgDaoImpl();
    private SellerDaoImpl sellerDao=new SellerDaoImpl();
    /**
     * rid查询旅游线路详细
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        //获取route对象
        Route route=routeDao.findOne(Integer.parseInt(rid));
        //获取routeimg的list集合
        List<RouteImg> routeImgList =routeImgDao.findImg(route.getRid());
        //封装到route的list中
        route.setRouteImgList(routeImgList);
        //获取所属商家的对象
        Seller seller=sellerDao.findSeller(route.getSid());
        //封装到route中
        route.setSeller(seller);
        return route;
    }
}
