package com.cwc.cojbackenduserservice.controller;

import com.cwc.cojbackendcommon.annotation.AuthCheck;
import com.cwc.cojbackendcommon.common.BaseResponse;
import com.cwc.cojbackendcommon.common.ErrorCode;
import com.cwc.cojbackendcommon.common.ResultUtils;
import com.cwc.cojbackendcommon.constant.UserConstant;
import com.cwc.cojbackendcommon.exception.BusinessException;
import com.cwc.cojbackenduserservice.service.UserService;
import com.cwc.cojbackendmodel.model.dto.user.*;
import com.cwc.cojbackendmodel.model.vo.LoginUserVO;
import com.cwc.cojbackendmodel.model.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用户接口
 *
 */
@RestController
@RequestMapping("/")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword);
        return ResultUtils.success(loginUserVO);
    }


    /**
     * 获取当前登录用户
     * @return
     */
    @GetMapping("/get/login")
    @AuthCheck(mustRole = UserConstant.USER_ROLE)
    public BaseResponse<UserVO> getLoginUser() {
        return ResultUtils.success(userService.getLoginUserVO());
    }
}
