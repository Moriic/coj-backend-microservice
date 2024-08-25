package com.cwc.cojbackendquestionservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cwc.cojbackendquestionservice.lc.OptionTest;
import com.shuzijun.lc.LcClient;
import com.shuzijun.lc.http.HttpClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.shuzijun.lc.command.*;
import com.shuzijun.lc.errors.LcException;
import com.shuzijun.lc.model.*;

import java.util.List;

@SpringBootTest
class cojBackendQuestionServiceApplicationTests {

    private static LcClient lcClient;

    private static String cookie = "";

    static {
        lcClient = LcClient.builder(HttpClient.SiteEnum.CN).build();
//         从环境变量中获取cookie
//        cookie = System.getenv("LC_COOKIE");
//        lcClient.invoker(CookieCommand.buildSetCookie(cookie));
//        boolean t = lcClient.invoker(CommonCommand.buildVerify());
    }
    @Test
    void contextLoads() {

    }

    @Test
    public void testGetAllQuestion() throws LcException {
        List<QuestionView> questionViews = lcClient.invoker(QuestionCommand.buildAllQuestions());
        System.out.println(JSON.toJSONString(questionViews.get(0)));
    }

    @Test
    public void testGetQuestion() throws LcException {
        QuestionView questionView = lcClient.invoker(QuestionCommand.buildGetQuestion("two-sum",new OptionTest("two-sum")));
        System.out.println(JSON.toJSONString(questionView));
    }

    @Test
    public void testSolution() throws LcException {
        List<Solution> solutions = lcClient.invoker(SolutionCommand.buildSolutionList(1, 0, "two-sum"));
        if (lcClient.getClient().isCn()) {
            System.out.println(JSON.toJSONString(solutions));
            String content = lcClient.invoker(SolutionCommand.buildSolutionArticle(solutions.get(0).getSlug()));
            System.out.println(content);
        } else {
            String content = lcClient.invoker(SolutionCommand.buildSolutionArticle("two-sum"));
            System.out.println(content);
        }
    }

    @Test
    public void testTags() throws LcException {
        List<Tag> tags = lcClient.invoker(FindCommand.buildTags());
        System.out.println(JSONObject.toJSONString(tags));
    }
}
