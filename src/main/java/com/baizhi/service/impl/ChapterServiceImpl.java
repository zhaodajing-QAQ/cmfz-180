package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private AlbumDao albumDao;

    /*
     *   分页展示全部
     * */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAll(Integer page, Integer rows, String id) {
        /**   计算起始
         *   page: 当前页
         *   rows: 数据
         *   total: 总页数
         *   records: 总条数
         **/
        //开始条数
        Integer start = (page - 1) * rows;
        //数据
        List<Chapter> list = chapterDao.selectAll(start, rows, id);
        //总条数
        int records = chapterDao.selectCount(id);
        //总页数
        int total = records % rows == 0 ? records / rows : records / rows + 1;

        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", list);
        map.put("total", total);
        map.put("records", records);
        return map;
    }

    /*
     *   添加章节
     * */
    @Override
    @Transactional
    public void addChapter(Chapter chapter) {
        chapter.setId(UUID.randomUUID().toString());
        chapterDao.insertChapter(chapter);

        //查询专辑数量
        Album album = albumDao.selectById(chapter.getAlbum_Id());
        //计算总数量
        Integer integer = new Integer(album.getCount());
        int count = integer + 1;
        album.setCount(new String(count+""));
        albumDao.updateAlbum(album);
    }

    /*
     *   修改章节
     * */
    @Override
    @Transactional
    public void changeChapter(Chapter chapter) {
        chapterDao.uploadChapter(chapter);
    }

    /*
     *   删除章节
     * */
    @Override
    @Transactional
    public void removeChapter(String[] id,String albumId) {
        chapterDao.deleteChapter(id);
        //查询对应专辑数量
        Album album = albumDao.selectById(albumId);
        //减去删除的章节数量
        String s = album.getCount();
        int count = new Integer(s) - id.length;
        //将新数量赋值
        album.setCount(count+"");
        albumDao.updateAlbum(album);
    }

    /*
     *   上传章节
     * */
    @Override
    public Map<String, String> chapterUpload(MultipartFile src, HttpSession session, String id) {
        Map<String, String> map = new HashMap<>();
        //1.获取upload的路径
        String realPath = session.getServletContext().getRealPath("/album/chapter");
        //2.判断文件夹是否存在
        File file1 = new File(realPath);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        //3.获取文件名
        String filename = src.getOriginalFilename();
        //4.上传
        try {
            src.transferTo(new File(realPath, filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //5.更换数据库中的数据
        if (filename.equals("")) {
            filename = null;
        }
        Chapter chapter = new Chapter();
        chapter.setId(id);
        chapter.setSrc(filename);
        //获取文件时长
        Encoder encoder = new Encoder();
        long ls = 0;
        MultimediaInfo m;
        File file = new File(realPath, filename);
        System.out.println(file);
        try {
            m = encoder.getInfo(file);  //这里传入的是文件对象
            ls = m.getDuration() / 1000;  //得到一个long类型的时长
        } catch (Exception e) {
            System.out.println("获取音频时长有误：" + e.getMessage());
        }
        chapter.setDuration(String.valueOf(ls / 60) + ":" + String.valueOf(ls % 60)); //转换为分钟:秒
        //file.length可以获取文件字节大小
        Double aDouble = Double.valueOf(file.length() / 1024.0 / 1024);
        BigDecimal bigDecimal = new BigDecimal(aDouble);
        String size = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).toString();
        chapter.setSize(size + "MB");
        chapterDao.uploadChapter(chapter);
        map.put("msg", "上传成功");
        return map;
    }


    @Override
    @Transactional
    public String chapterDownload(String src, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> map = new HashMap<>();
        //拆分路径
        String[] split = src.split("/");
        String filename = split[split.length - 1];
        //为文件名进行编码操作（中文）
        String encode = null;
        try {
            encode = URLEncoder.encode(filename, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //响应数据前设置下载方式 一级下载后的文件名
        response.setHeader("content-disposition", "attachment;fileName=" + encode);
        //获取文件的路径
        String path = request.getSession().getServletContext().getRealPath("/album/chapter");
        System.out.println(path);
        //path, fileName相当于path+fileName
        FileInputStream is = null;
        try {
            is = new FileInputStream(new File(path, filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //通过响应输出流给Client打印数据
        ServletOutputStream os = response.getOutputStream();
        //文件传输 读取过程中给Client相应数据
        byte[] bytes = new byte[2048];
        while (true) {
            //返回值代表读取数据的个数 如果到达文件末尾 返回-1
            int i = is.read(bytes, 0, bytes.length);
            if (i == -1) break;
            //向外响应
            try {
                os.write(bytes, 0, i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //关闭资源
        is.close();
        os.close();
        return "下载成功";
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Chapter> queryChapter(String id) {
        return chapterDao.selectChapter(id);
    }
}