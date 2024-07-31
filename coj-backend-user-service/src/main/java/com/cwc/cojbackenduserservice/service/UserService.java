package com.cwc.cojbackenduserservice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cwc.cojbackendmodel.model.entity.User;
import com.cwc.cojbackendmodel.model.vo.LoginUserVO;
import com.cwc.cojbackendmodel.model.vo.UserVO;

/**
 * 用户服务
 *

 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword);

    User getLoginUser();

    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    UserVO getUserVO(User user);
    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    boolean isAdmin(User user);

    UserVO getLoginUserVO();
}