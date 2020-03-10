package com.hlwcbz.modules.admin.upms.service;

import com.hlwcbz.modules.common.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-08-16
 */
public interface UserService extends IService<User> {
    boolean saveUser(User user);

    boolean updateUser(User user);


}
