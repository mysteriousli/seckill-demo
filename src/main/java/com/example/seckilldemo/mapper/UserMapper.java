package com.example.seckilldemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.seckilldemo.pojo.User;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liguangyuan
 * @since 2022-04-04
 */
public interface UserMapper extends BaseMapper<User> {
   User selectUser();
}
