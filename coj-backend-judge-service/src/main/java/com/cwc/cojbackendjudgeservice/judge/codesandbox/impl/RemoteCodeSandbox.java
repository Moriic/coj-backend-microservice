package com.cwc.cojbackendjudgeservice.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import com.cwc.cojbackendcommon.common.ErrorCode;
import com.cwc.cojbackendcommon.exception.BusinessException;
import com.cwc.cojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.cwc.cojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.cwc.cojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.cwc.cojbackendmodel.model.codesandbox.JudgeInfo;
import com.cwc.cojbackendmodel.model.enums.JudgeInfoMessageEnum;

import org.apache.commons.lang3.StringUtils;

/**
 * 远程代码沙箱（实际调用接口的沙箱）
 */
@Slf4j
public class RemoteCodeSandbox implements CodeSandbox {

    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        String url = "http://localhost:8090/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr;
        try {
            responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        } catch (Exception e) {
            log.error("connect remoteSandbox error, message = {}", e.getMessage());
            ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
            JudgeInfo judgeInfo = new JudgeInfo();
            judgeInfo.setMessage(JudgeInfoMessageEnum.SYSTEM_ERROR.getValue());
            executeCodeResponse.setMessage(JudgeInfoMessageEnum.SYSTEM_ERROR.getValue());
            executeCodeResponse.setStatus(2);
            executeCodeResponse.setJudgeInfo(judgeInfo);
            return executeCodeResponse;
        }
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR,
                "executeCode remoteSandbox error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
