package com.lh.myblog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.myblog.bean.Blog;
import com.lh.myblog.bean.QQInfo;
import com.lh.myblog.exception.NotFoundException;
import com.lh.myblog.mapper.BlogMapper;
import com.lh.myblog.service.BlogService;
import com.lh.myblog.util.MarkdownUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Value("${qqUrl}")
    private String qqUrl;

    @Override
    public Page<Blog> listBlogs(Page<Blog> page, Blog blog) {
        return baseMapper.selectPageBlogs(page,blog);
    }

    @Override
    public List<Blog> listByTypeId(Long typeId) {
        Map<String, Object> map = new HashMap<>();
        map.put("type_id",typeId);
        List<Blog> blogs = baseMapper.selectByMap(map);
        return blogs;
    }

    @Override
    public Blog getByTitle(String title) {
        Map<String, Object> map = new HashMap<>();
        map.put("title",title);
        List<Blog> blogs = baseMapper.selectByMap(map);
        if (blogs.isEmpty()){
            return null;
        }else{
            return blogs.get(0);
        }
    }

    @Override
    public Page<Blog> listPageAllBlogs(Page<Blog> page,Integer typeId) {
        return baseMapper.selectPageAllBlogs(page,typeId);
    }

    @Transactional
    @Override
    public Blog getAllInfoById(Long id) {
        Blog blog = baseMapper.selectAllInfoById(id);
        if (blog ==null){
            throw new NotFoundException("该博客不存在");
        }
        String content = blog.getContent();
        //将文章内容转换为html格式
        String s = MarkdownUtils.markdownToHtmlExtensions(content);
        blog.setContent(s);
        //浏览次数+1
        baseMapper.updateViewsById(id);

        return blog;
    }

    @Override
    public QQInfo getQQInfo(String qqId) {
        //去除qq前后的空格
        String qqId1 = qqId.trim();
        //拼接拉取qq信息的接口地址
        String url = qqUrl + qqId1;
        //获取qq信息，返回String类型的键值对字符串
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        //用alibaba的fastjson工具将字符串转对象
        QQInfo qqInfo = JSONObject.parseObject(result, QQInfo.class);
        if (qqInfo.getQq() != null){
            return qqInfo;
        }
        return null;

    }

}
