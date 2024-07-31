package com.cwc.cojbackendjudgeservice.judge.codesandbox;

import com.cwc.cojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.cwc.cojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
