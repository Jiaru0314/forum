package com.jit.service;

import com.jit.pojo.Blog;
import com.jit.vo.BlogDto;

import java.util.List;
import java.util.Map;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-13 21:29
 **/
public interface BlogService {

    Blog getBlogById(Integer id);

    Integer saveBlog(Blog blog);

    Integer updateBlog(Blog blog);

    List<BlogDto> findNewestBlogs(Integer rows);

    List<BlogDto> findHotestBlogs();

    Integer deleteBlogById(Integer id);

    List<BlogDto> findBlogByUserId(Integer userId);

    Map<String, List<Blog>> archiveBlog(Integer userId);

    Integer countBlog(Integer userId);

    BlogDto findBlogDto(Integer blogId);

    List<BlogDto> findBlogDtoByTypeId(Integer typeId);

    List<BlogDto> findBlogDtoByTagId(Integer tagId);

    List<BlogDto> findRecommendBlogs(Integer userId);

    List<BlogDto> findBlogDtoByTitleLike(String query);

    List<BlogDto> listRandBlogDto();

}
