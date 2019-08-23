package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

public interface RouteDao {
    Route findOne(int rid);
}
