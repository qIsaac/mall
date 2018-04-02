package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.vo.OrderVo;

/**
 * @Author : Isaac
 * @Description:
 * @Date :Created in 14:49 2018/4/2
 */
public interface IOrderService {

    //portal
    ServerResponse createOrder(Integer userId,Integer shippingId);
    // backend
    ServerResponse<PageInfo> manageList(int pageNum, int pageSize);

    ServerResponse<OrderVo> manageDetail(Long orderNo);

    ServerResponse<PageInfo> manageSearch(Long orderNo,int pageNum,int pageSize);

    ServerResponse<String> manageSendGoods(Long orderNo);

    ServerResponse getOrderCartProduct(Integer userId);

    ServerResponse<String> cancel(Integer userId,Long orderNo);

    ServerResponse<OrderVo> getOrderDetail(Integer userId,Long orderNo);

    ServerResponse<PageInfo> getOrderList(Integer userId,int pageNum,int pageSize);



}
