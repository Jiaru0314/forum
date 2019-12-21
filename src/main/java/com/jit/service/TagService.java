package com.jit.service;

import com.jit.pojo.Tag;

import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-14 12:29
 **/
public interface TagService {

    Integer saveTag(String tagName);

    Integer deleteTagById(Integer tagId);

    Integer updateTag(Tag tag);

    List<Tag> findAllTag();

    List<Tag> findHottestTag(Integer counts);

    List<Tag> findTagsByUserId(Integer userId);

    Tag findTagByTagId(Integer tagId);

    Tag findTagByTagName(String tagName);

}
