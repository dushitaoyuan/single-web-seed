package com.taoyuanx.codegen.service;

import com.taoyuanx.codegen.DTO.PageDTO;
import com.taoyuanx.codegen.DTO.UserDTO;
import com.taoyuanx.codegen.vo.PageVo;
/**
*  @date: 2019-56-09 05:56:59
*/
public interface UserService{

    UserDTO getById(Long id);

    void delete(Long id);

    void save(UserDTO userDTO);

    void update(UserDTO userDTO);

    PageDTO<UserDTO> list(PageVo<UserDTO> pageVo);
}




