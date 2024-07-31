package com.cwc.cojbackendquestionservice.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cwc.cojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.cwc.cojbackendmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.cwc.cojbackendmodel.model.entity.QuestionSubmit;
import com.cwc.cojbackendmodel.model.entity.User;
import com.cwc.cojbackendmodel.model.vo.QuestionSubmitVO;

/**
* @author cwc
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2023-08-07 20:58:53
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage);
}
