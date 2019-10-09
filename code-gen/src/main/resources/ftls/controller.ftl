package com.taoyuanx.codegen.controller;

import ${serviceFullName};
import ${dtoFullName};
import com.taoyuanx.codegen.vo.PageVo;
import com.taoyuanx.commons.api.Result;
import com.taoyuanx.commons.api.ResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
* @date ${date}
* @desc: 代码生成controller
*/
@Controller
@RequestMapping("${controllerMapping}")
public class ${controllerFinalName} {
    @Autowired
    ${serviceFinalName} ${serviceFinalName?uncapFirst};

    @GetMapping("get/{id:[0-9]}")
    @ResponseBody
    public ${dtoFinalName} getById(@PathVariable("id") Long id) {
        return ${serviceFinalName?uncapFirst}.getById(id);
    }

    @DeleteMapping("del/{id:[0-9]}")
    @ResponseBody
    public Result delete(@PathVariable("id") Long id) {
    ${serviceFinalName?uncapFirst}.delete(id);
    return ResultBuilder.success();
    }

    @PostMapping("modify")
    @ResponseBody
    public Result modify(@RequestBody ${dtoFinalName} ${dtoFinalName?uncapFirst}) {
    ${serviceFinalName?uncapFirst}.update(${dtoFinalName?uncapFirst});
    return ResultBuilder.success();
    }

    @PostMapping("new")
    @ResponseBody
    public Result save(@RequestBody ${dtoFinalName} ${dtoFinalName?uncapFirst}) {
    ${serviceFinalName?uncapFirst}.save(${dtoFinalName?uncapFirst});
    return ResultBuilder.success(${dtoFinalName?uncapFirst}.getId());
    }

    @PostMapping("list")
    @ResponseBody
    public Result list(@RequestBody ${dtoFinalName} ${dtoFinalName?uncapFirst},
    @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
    @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        return ResultBuilder.success( ${serviceFinalName?uncapFirst}.list(new PageVo<${dtoFinalName}>(pageSize,pageNum,${dtoFinalName?uncapFirst})));
    }
}
