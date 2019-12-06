package com.jit.service;

import com.jit.mapper.TagMapper;
import com.jit.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Integer updateTagNameById(String newTagName, Integer tagId) {
        return tagMapper.updateTagNameById(newTagName, tagId);
    }

    @Override
    public List<Tag> findAllTag() {
        return tagMapper.findAllTag();
    }

    @Override
    public Integer findTagByTagName(String tagName) {
        return tagMapper.findTagByTagName(tagName);
    }

    @Override
    public List<Tag> findHottestTag(Integer counts) {
        return tagMapper.findHottestTag(counts);
    }

    @Override
    public List<Tag> findTagsByUserId(Integer userId) {
        String tagIds = tagMapper.findTagsByUserId(userId);
        if (tagIds == null || "".equals(tagIds)) {
            return null;
        }
        String[] split = tagIds.split(",");
        List<Integer> tagId_list = new ArrayList<>();
        for (String str : split) {
            if (str.length() != 0) {
                int id = Integer.parseInt(str);
                if (!tagId_list.contains(id)) {
                    tagId_list.add(id);
                }
            }
        }
//        System.out.println(tagId_list);
        return tagMapper.findTagByIdList(tagId_list);
    }
}
