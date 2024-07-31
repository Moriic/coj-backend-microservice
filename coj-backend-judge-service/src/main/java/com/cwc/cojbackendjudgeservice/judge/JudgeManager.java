package com.cwc.cojbackendjudgeservice.judge;

import com.cwc.cojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.cwc.cojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.cwc.cojbackendjudgeservice.judge.strategy.JudgeContext;
import com.cwc.cojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.cwc.cojbackendmodel.model.codesandbox.JudgeInfo;
import com.cwc.cojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}
