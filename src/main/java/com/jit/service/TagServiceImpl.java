package com.jit.service;

import com.jit.mapper.TagMapper;
import com.jit.pojo.Tag;
import com.jit.pojo.Type;
import com.jit.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: forum
 * @description: TagService接口实现类
 * @author: XZQ
 * @create: 2019-11-14 12:29
 **/
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Integer saveTag(String tagName) {
        return tagMapper.saveTag(tagName);
    }

    @Override
    public Integer deleteTagById(Integer tagId) {
        return tagMapper.deleteTagById(tagId);
    }

    @Override
    public Integer updateTag(Tag tag) {
        return tagMapper.updateTag(tag);
    }


    @Override
    public List<Tag> findAllTag() {
        return tagMapper.findAllTag();
    }

    @Override
    public List<Tag> findHottestTag(Integer counts) {
        //从redis中获取数据
        List<Tag> tagList = redisTemplate.boundListOps("hotTagList").range(0, -1);
        //如果数据不存在
        if (null == tagList || tagList.size() == 0) {
            System.out.println("从数据库中获取tag数据，并将数据写入缓存");
            tagList = tagMapper.findHottestTag(counts);
            for (Tag tag : tagList) {
                redisTemplate.boundListOps("hotTagList").rightPush(tag);
            }
        } else {
            System.out.println("从redis获取tag");
        }
        return tagList;
    }

    @Override
    public List<Tag> findTagsByUserId(Integer userId) {
        String tagIds = tagMapper.findTagsByUserId(userId);
        List<Integer> tagId_list = ListUtil.stringToList(tagIds);
        if (tagId_list != null && tagId_list.size() > 0) {
            return tagMapper.findTagByIdList(tagId_list);
        } else {
            return null;
        }
    }

    @Override
    public Tag findTagByTagId(Integer tagId) {
        return tagMapper.findTagById(tagId);
    }

    @Override
    public Tag findTagByTagName(String tagName) {
        return tagMapper.findTagByTagName(tagName);
    }
}
