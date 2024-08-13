package com.cwc.cojbackendmodel.model.entity;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.*;
import com.cwc.cojbackendmodel.model.dto.question.JudgeCase;
import com.cwc.cojbackendmodel.model.dto.question.JudgeConfig;
import com.cwc.cojbackendmodel.model.vo.QuestionVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuestionAllVO implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 题目答案
     */
    private String answer;

    /**
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptedNum;

    /**
     * 判题用例（json 数组）
     */
    private List<JudgeCase> judgeCase;

    /**
     * 判题配置（json 对象）
     */
    private JudgeConfig judgeConfig;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    /**
     * 对象转包装类
     *
     * @param question
     * @return
     */
    public static QuestionAllVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionAllVO questionAllVO = new QuestionAllVO();
        BeanUtils.copyProperties(question, questionAllVO);
        List<String> tagList = JSONUtil.toList(question.getTags(), String.class);
        questionAllVO.setTags(tagList);
        String judgeConfigStr = question.getJudgeConfig();
        questionAllVO.setJudgeConfig(JSONUtil.toBean(judgeConfigStr, JudgeConfig.class));
        List<JudgeCase> judgeCaseList = JSONUtil.toList(question.getJudgeCase(), JudgeCase.class);
        questionAllVO.setJudgeCase(judgeCaseList);
        return questionAllVO;
    }

}