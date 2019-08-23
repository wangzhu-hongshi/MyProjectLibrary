package com.mmall.service.Impl;

        import com.mmall.common.ServerResponse;
        import com.mmall.dao.OrderItemMapper;
        import com.mmall.pojo.OrderItem;
        import com.mmall.service.IOrderItmeService;
        import org.apache.commons.collections.CollectionUtils;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;

@Service("iOrderItmeService")
public class OrderItmeServiceImpl implements IOrderItmeService {
    @Autowired
    private OrderItemMapper orderItemMapper;

    public ServerResponse list(Integer userId,Long orderNO){
        List<OrderItem> byOrderNoUserId = orderItemMapper.getByOrderNoUserId(orderNO, userId);
        if(CollectionUtils.isEmpty(byOrderNoUserId)){
            return ServerResponse.createByError();
        }
        return ServerResponse.createBySuccess();
    }
    public ServerResponse sele(Integer Id){
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(Id);
        System.out.println(orderItem);
       if(orderItem==null){
            return ServerResponse.createByError();
        }
        return ServerResponse.createBySuccess();
    }
}
