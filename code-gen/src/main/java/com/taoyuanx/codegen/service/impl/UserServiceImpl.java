package com.taoyuanx.codegen.service.impl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.taoyuanx.codegen.DTO.PageDTO;
import com.taoyuanx.codegen.vo.PageVo;
import com.taoyuanx.commons.bean.CBeanMapper;
import java.util.Objects;

import com.taoyuanx.codegen.DO.UserDO;
import com.taoyuanx.codegen.dao.UserDao;
import com.taoyuanx.codegen.service.UserService;
import com.taoyuanx.codegen.DTO.UserDTO;
@Service
@Transactional
public class UserServiceImpl  extends ServiceImpl<UserDao, UserDO> implements UserService{
    @Autowired
    UserDao userDao;

    @Override
    public UserDTO getById(Long id) {
        UserDO userDO = userDao.selectById(id);
        if (Objects.isNull(userDO)) {
            return null;
        }
        return CBeanMapper.map(userDO, UserDTO.class);
    }

    @Override
    public void delete(Long id) {
        if (count(new LambdaQueryWrapper<UserDO>().ne(UserDO::getId, id)) > 0) {
            removeById(id);
        }
    }

    @Override
    public void save(UserDTO userDTO) {
        save(userDTO);
    }

    @Override
    public void update(UserDTO userDTO) {
        if(count(new LambdaQueryWrapper<UserDO>().ne(UserDO::getId, userDTO.getId())) > 0) {
            updateById(userDTO);
        }
     }
    @Override
    public PageDTO<UserDTO> list(PageVo<UserDTO> pageVo) {
        PageHelper.startPage(pageVo.getPageNum(), pageVo.getPageSize());
        QueryWrapper queryWrapper = new QueryWrapper(pageVo.getQuery());
        return PageDTO.githubPage((Page) list(queryWrapper));
    }

}




