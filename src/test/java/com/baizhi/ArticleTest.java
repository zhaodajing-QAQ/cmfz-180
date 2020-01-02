package com.baizhi;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ArticleTest {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ArticleService articleService;

    @Test
    public void test1(){
        Map<String, Object> map = articleService.queryAll(1, 1);
        List<Article> rows = (List<Article>) map.get("rows");
        for (Article row : rows) {
            System.out.println(row);
        }
        /*List<Article> list = articleDao.selectAll(1, 1);
        for (Article article : list) {
            System.out.println(article);
        }*/
    }
}
