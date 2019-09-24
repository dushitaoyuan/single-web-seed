package com.taoyuanx.mvcseed.service.impl;
import com.taoyuanx.commons.bean.CBeanMapper;
import com.taoyuanx.mvcseed.DO.UserDO;
import com.taoyuanx.mvcseed.DTO.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.taoyuanx.mvcseed.service.UserService;
import com.taoyuanx.mvcseed.dao.UserDao;

import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    UserDao userDao;

    @Override
    public UserDTO getById(Long userId) {
        UserDO userDO = userDao.selectByPrimaryKey(userId);
        if(Objects.nonNull(userDO)){
            return  CBeanMapper.map(userDO,UserDTO.class);
        }
        return null;
    }
}




