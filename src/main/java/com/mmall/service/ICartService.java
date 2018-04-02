package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

/**
 * @Author : Isaac
 * @Description:
 * @Date :Created in 17:05 2018/4/2
 */
public interface ICartService {
    ServerResponse<CartVo> list (Integer userId);
}
