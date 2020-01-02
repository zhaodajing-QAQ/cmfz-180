package com.baizhi;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AlbumTest {
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private ChapterService chapterService;

    @Test
    public void tset1(){
        Album album = new Album("1","西游记","111.jpg","5","吴承恩","小方","30","一本好书",new Date(),"展示",null);
        albumDao.insertAlbum(album);
        System.out.println("-------invoke------");
    }

    @Test
    public void tset2(){
        List<Album> albums = albumDao.selectAll(0,1);
        for (Album album : albums) {
            System.out.println(album);
        }
    }

    @Test
    public void tset3(){
        Album album = new Album();
        album.setId("1");
        album.setImg("222.jsp");
        albumDao.updateAlbum(album);
        System.out.println("-----invoke-----");
    }

    @Test
    public void tset4(){
        Integer integer = albumDao.selectCount();
        System.out.println(integer);
    }

    @Test
    public void tset5(){
        Album album = new Album("2","三国演义","111.jpg","4","不知道","小方","30","一本好书",new Date(),"展示",null);
        albumService.addAlbum(album);
        System.out.println("-----invoke------");
    }

    @Test
    public void tset6(){
        Map<String, Object> map = albumService.queryAll(0, 2);
        List<Album> albums = (List<Album>) map.get("rows");
        for (Album album : albums) {
            System.out.println(album);
        }
    }

    @Test
    public void tset7(){
        Chapter chapter = new Chapter("1","三打白骨精","1","100MB","孙悟空三打白骨精","sdbgj.jsp","展示",null);
        chapterDao.insertChapter(chapter);
    }

    @Test
    public void tset8(){
        List<Chapter> chapters = chapterDao.selectAll(0, 1, "1");
        for (Chapter chapter : chapters) {
            System.out.println(chapter);
        }
    }

    @Test
    public void tset9(){
        Chapter chapter = new Chapter();
        chapter.setId("1");
        chapter.setStatus("不展示");
        chapterDao.uploadChapter(chapter);
        System.out.println("----invoke------");
    }

    @Test
    public void tset10(){
        System.out.println(chapterDao.selectCount("1"));
    }

    @Test
    public void tset11(){
        Map<String, Object> map = chapterService.queryAll(0, 1, "1");
        List<Chapter> chapters = (List<Chapter>) map.get("rows");
        for (Chapter chapter : chapters) {
            System.out.println(chapter);
        }
    }

    @Test
    public void tset12(){
        Album album = albumDao.selectById("1");
        System.out.println(new Integer(album.getCount()));
    }

}
