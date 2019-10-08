package com.taoyuanx.mvcseed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoyuanx.commons.bean.CBeanMapper;
import com.taoyuanx.commons.utils.PasswordUtil;
import com.taoyuanx.mvcseed.DO.UserDO;
import com.taoyuanx.mvcseed.DTO.UserDTO;
import com.taoyuanx.mvcseed.dao.UserDao;
import com.taoyuanx.mvcseed.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserDao, UserDO> implements UserService {

    @Override
    public UserDTO getById(Long userId) {
        UserDO userDO = baseMapper.selectById(userId);
        if (Objects.nonNull(userDO)) {
            return CBeanMapper.map(userDO, UserDTO.class);
        }
        return null;
    }

    @Override
    public void save(UserDTO userDTO) {
        userDTO.setPassword(PasswordUtil.passwordEncode(userDTO.getPassword()));
        baseMapper.insert(userDTO);
    }
}




