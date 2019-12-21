package com.jit.service;

import com.jit.mapper.TagMapper;
import com.jit.pojo.Tag;
import com.jit.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
        return tagMapper.findHottestTag(counts);
    }

    @Override
    public List<Tag> findTagsByUserId(Integer userId) {
        String tagIds = tagMapper.findTagsByUserId(userId);
        List<Integer> tagId_list = ListUtil.stringToList(tagIds);
        return tagMapper.findTagByIdList(tagId_list);
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
