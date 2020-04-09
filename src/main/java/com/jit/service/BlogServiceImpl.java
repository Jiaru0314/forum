package com.jit.service;

import com.jit.mapper.BlogMapper;
import com.jit.mapper.TagMapper;
import com.jit.mapper.UserMapper;
import com.jit.pojo.Blog;
import com.jit.pojo.Tag;
import com.jit.pojo.User;
import com.jit.util.HTMLUtils;
import com.jit.util.ListUtil;
import com.jit.util.MarkdownUtils;
import com.jit.vo.BlogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.*;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-13 21:30
 **/
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Blog getBlogById(Integer id) {
        return blogMapper.getBlogById(id);
    }

    @Override
    public Integer saveBlog(Blog blog) {
        return blogMapper.saveBlog(blog);
    }

    @Override
    public Integer updateBlog(Blog blog) {
        return blogMapper.updateBlog(blog);
    }

    @Override
    public List<BlogDto> findNewestBlogs(Integer rows) {
        //从redis中获取数据
        List<BlogDto> blogDtoList = redisTemplate.boundListOps("newBlogList").range(0, -1);
        if (null == blogDtoList || blogDtoList.size() == 0) {
            //从数据库中获取数据
            blogDtoList = blogMapper.findNewestBlogs(rows);
            System.out.println("从数据库中获取最新Blog数据,并将数据写入缓存");
            //将数据存入缓存
            for (BlogDto blogDto : blogDtoList) {
                redisTemplate.boundListOps("newBlogList").rightPush(blogDto);
            }
        } else {
            System.out.println("从redis中获取Blog数据");
        }
        return blogDtoList;
    }

    @Override
    public List<BlogDto> findHotestBlogs() {
        //从redis中获取数据
        List<BlogDto> blogDtoList = redisTemplate.boundListOps("hotBlogList").range(0, -1);
        if (null == blogDtoList || blogDtoList.size() == 0) {
            //从数据库中获取数据
            blogDtoList = blogMapper.findHottestBlogs();
            System.out.println("从数据库中获取Blog数据,并将数据写入缓存");
            //将数据存入缓存
            for (BlogDto blogDto : blogDtoList) {
                redisTemplate.boundListOps("hotBlogList").rightPush(blogDto);
            }
        } else {
            System.out.println("从redis中获取Blog数据");
        }
        return transferContentList(blogDtoList);
    }

    @Override
    public Integer deleteBlogById(Integer id) {
        return blogMapper.deleteBlogById(id);
    }

    @Override
    public List<BlogDto> findBlogByUserId(Integer userId) {
        return blogMapper.findBlogsByUserId(userId);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog(Integer userId) {
        List<String> years = blogMapper.findGroupYear(userId);
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogMapper.findBlogByYear(year, userId));
        }
        return map;
    }

    @Override
    public Integer countBlog(Integer userId) {
        return blogMapper.findBlogCountsByUserId(userId);
    }

    /**
     * 通过blogId查询BlogDto 并将content内容由Markdown格式转成HTML格式
     *
     * @param blogId
     * @return
     */
    @Override
    public BlogDto findBlogDto(Integer blogId) {
        BlogDto dto = blogMapper.findBlogDtoByBlogId(blogId);
        String tagIds = dto.getTagIds();
        if (tagIds == null || "".equals(tagIds)) {
            dto.setTags(null);
        }
        List<Tag> tags = new ArrayList<>();
        String[] split = tagIds.split(",");
        for (int i = 0; i < split.length; i++) {
            int id = Integer.parseInt(split[i]);
            tags.add(tagMapper.findTagById(id));
        }
        dto.setTags(tags);
        return dto;
    }

    @Override
    public List<BlogDto> findBlogDtoByTypeId(Integer typeId) {
        List<BlogDto> blogDtoList = blogMapper.findBlogByTypeId(typeId);
        return transferContentList(blogDtoList);
    }

    @Override
    public List<BlogDto> findBlogDtoByTagId(Integer tagId) {
        List<BlogDto> blogDtoList = blogMapper.findBlogsByTagId(tagId);
        return transferContentList(blogDtoList);
    }

    @Override
    public List<BlogDto> findRecommendBlogs(Integer userId) {
        User this_user = userMapper.findUserById(userId);
        String follow_ids = this_user.getFollow_ids();
        List<Integer> idList = ListUtil.stringToList(follow_ids);
        if (null != idList) {
            List<BlogDto> followedUserBlogs = blogMapper.findFollowedUserBlogs(idList);
            return transferContentList(followedUserBlogs);
        }
        return null;
    }

    @Override
    public List<BlogDto> findBlogDtoBySearchParam(String query) {
        return blogMapper.findBlogBySearchParamLike(query);
    }

    @Override
    public List<BlogDto> listRandBlogDto() {
        List<BlogDto> dtoList = blogMapper.listRandBlogDto();
        return transferContentList(dtoList);
    }

    @Override
    public List<BlogDto> listAllBlog() {
        return blogMapper.listAllBlogDto();
    }

    /**
     * 将BlogDtoList中的content转成普通文本格式返回
     *
     * @param blogDtoList
     * @return
     */
    public List<BlogDto> transferContentList(List<BlogDto> blogDtoList) {
        for (BlogDto blogDto : blogDtoList) {
            String md_content = blogDto.getContent();//获取MarkDown格式的内容
            String html_content = MarkdownUtils.markdownToHtml(md_content);//将MarkDown格式内容转换成HTML格式
            blogDto.setContent(HTMLUtils.getText(html_content));//将html格式转换成普通文本格式
        }
        return blogDtoList;
    }
}
