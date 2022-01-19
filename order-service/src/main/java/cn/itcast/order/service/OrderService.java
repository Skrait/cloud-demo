package cn.itcast.order.service;

import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import cn.itcast.order.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private RestTemplate restTemplate;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        //2、利用RestTemplate对user_service服务接口发起http请求，根据order里面的userId查询用户信息
        //2.1 url路径
        String url = "http://userservice:8081/user/" + order.getUserId();
        //发送http请求，实现远程调用
        User user = restTemplate.getForObject(url, User.class);
        //3.封装user到order
        order.setUser(user);
        // 4.返回
        return order;
    }
}
