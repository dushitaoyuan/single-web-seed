package com.taoyuanx.codegen.controller;

import com.taoyuanx.codegen.service.UserService;
import com.taoyuanx.codegen.DTO.UserDTO;
import com.taoyuanx.codegen.vo.PageVo;
import com.taoyuanx.commons.api.Result;
import com.taoyuanx.commons.api.ResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
* @date 2019-56-09 05:56:59
* @desc: 代码生成controller
*/
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("get/{id:[0-9]}")
    @ResponseBody
    public UserDTO getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @DeleteMapping("del/{id:[0-9]}")
    @ResponseBody
    public Result delete(@PathVariable("id") Long id) {
    userService.delete(id);
    return ResultBuilder.success();
    }

    @PostMapping("modify")
    @ResponseBody
    public Result modify(@RequestBody UserDTO userDTO) {
    userService.update(userDTO);
    return ResultBuilder.success();
    }

    @PostMapping("new")
    @ResponseBody
    public Result save(@RequestBody UserDTO userDTO) {
    userService.save(userDTO);
    return ResultBuilder.success(userDTO.getId());
    }

    @PostMapping("list")
    @ResponseBody
    public Result list(@RequestBody UserDTO userDTO,
    @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
    @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        return ResultBuilder.success( userService.list(new PageVo<UserDTO>(pageSize,pageNum,userDTO)));
    }
}
