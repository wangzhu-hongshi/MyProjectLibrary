package cn.itcast.travel.service;

import cn.itcast.travel.domain.Route;

/**
 * 旅游线路的详细
 */
public interface RouteService {
    Route findOne(String rid);
}
