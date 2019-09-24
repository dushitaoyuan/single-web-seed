package com.taoyuanx.mvcseed.controller;

import com.taoyuanx.commons.utils.PropertiesUtil;
import com.taoyuanx.mvcseed.DTO.UserDTO;
import com.taoyuanx.mvcseed.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dushitaoyuan
 * @date 2019/9/1117:01
 * @desc: hello
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    UserService userService;
    @RequestMapping(value = "sayHello")
    @ResponseBody
    public Map<String, Object> hello() {
        Map<String, Object> helloPacket = new HashMap<>(1);
        helloPacket.put("hello", PropertiesUtil.getSystemProperty("application.name"));
        return helloPacket;
    }

    @RequestMapping(value = "user/get")
    @ResponseBody
    public UserDTO getUser(Long userId) {
        UserDTO userDTO=userService.getById(userId);
        return userDTO;
    }
}
