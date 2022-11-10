package com.example.seckilldemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.seckilldemo.pojo.Blog;

public interface BlogMapper {
    Blog selectBlog();
    Blog selectBlogById(Integer id);
}
