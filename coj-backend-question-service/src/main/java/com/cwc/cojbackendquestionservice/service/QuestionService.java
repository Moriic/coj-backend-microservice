package com.cwc.cojbackendquestionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cwc.cojbackendmodel.model.dto.question.QuestionQueryRequest;
import com.cwc.cojbackendmodel.model.entity.Question;
import com.cwc.cojbackendmodel.model.entity.QuestionAllVO;
import com.cwc.cojbackendmodel.model.vo.QuestionVO;

/**
* @author cwc
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2023-08-07 20:58:00
*/
public interface QuestionService extends IService<Question> {


    /**
     * 校验
     *
     * @param question
     * @param add
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);
    
    /**
     * 获取题目封装
     *
     * @param question
     * @return
     */
    QuestionVO getQuestionVO(Question question);

    /**
     * 分页获取题目封装
     *
     * @param questionPage
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage);

    Page<QuestionAllVO> getQuestionAllVOPage(Page<Question> questionPage);
}
