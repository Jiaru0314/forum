package com.jit.mapper;

import com.jit.pojo.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-14 12:00
 **/
@Mapper
public interface TagMapper {

    /**
     * 保存Tag
     *
     * @param tagName
     * @return
     */
    @Insert("INSERT INTO t_tag SET `name`= #{tagName}")
    Integer saveTag(@Param("tagName") String tagName);

    /**
     * 通过Id删除Tag
     *
     * @param tagId
     * @return
     */
    @Delete("DELETE FROM t_tag WHERE id =#{tagId}")
    Integer deleteTagById(@Param("tagId") Integer tagId);

    /**
     * 修改Tag
     *
     * @param tag
     * @return
     */
    @Update("UPDATE `t_tag` SET `name` = #{tag.name} ,`counts` = #{tag.counts} WHERE `id` = #{tag.id}")
    Integer updateTag(@Param("tag") Tag tag);

    /**
     * 通过TagId查询Tag
     *
     * @param tagId
     * @return
     */
    @Select("SELECT * FROM `t_tag` WHERE `id` = #{tagId}")
    Tag findTagById(@Param("tagId") Integer tagId);

    /**
     * 查询所有的Tag
     *
     * @return
     */
    @Select("SELECT * FROM t_tag ")
    List<Tag> findAllTag();

    /**
     * 查询最热的counts个Tag
     *
     * @param counts
     * @return
     */
    @Select("SELECT * FROM t_tag ORDER BY counts DESC LIMIT  #{counts}")
    List<Tag> findHottestTag(@Param("counts") Integer counts);

    /**
     * 查询某用户所有的TagId 拼接成字符串返回
     *
     * @param userId
     * @return
     */
    @Select("SELECT DISTINCT GROUP_CONCAT(DISTINCT(`tagIds`)) FROM `t_blog` WHERE `user_id` = #{userId}")
    String findTagsByUserId(@Param("userId") Integer userId);

    /**
     * 通过TagName查询Tag
     *
     * @param tagName
     * @return
     */
    @Select("SELECT * FROM `t_tag` WHERE `name` = #{tagName}")
    Tag findTagByTagName(String tagName);



    /*XMl配置方式*/

    /**
     * 通过TagIdList查询所有的Tag
     *
     * @param idList
     * @return
     */
    List<Tag> findTagByIdList(@Param("idList") List<Integer> idList);
}
