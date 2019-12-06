package com.jit.mapper;

import com.jit.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @program: forum
 * @description: CommentMapper
 * @author: XZQ
 * @create: 2019-11-25 12:44
 **/
public interface CommentMapper {
    /**
     * 保存Comment
     *
     * @param comment
     * @return
     */
    @Insert("INSERT INTO `t_comment`(`user_id`,`content`,`createTime`,`blog_id`,`parent_id`) " +
            "VALUES (#{comment.user_id},#{comment.content},#{comment.createTime},#{comment.blog_id},#{comment.parent_id})")
    Integer saveComment(@Param("comment") Comment comment);

    /**
     * 查询Comment
     *
     * @param blog_id
     * @return
     */
    @Select("SELECT `t_comment`.`id`,`user_id`,`content`,`createTime`,`blog_id`,`parent_id`,`username`,`avatar` FROM `t_comment` " +
            "LEFT JOIN `t_user` ON `t_comment`.`user_id` = `t_user`.`id` " +
            "WHERE `blog_id` = #{blog_id} ORDER BY `createTime`")
    List<Comment> getCommentsByBlogId(@Param("blog_id") Integer blog_id);


    @Select("SELECT `t_comment`.`id`,`user_id`,`content`,`createTime`,`blog_id`,`parent_id`,`username`,`avatar` FROM `t_comment` " +
            "LEFT JOIN `t_user` ON `t_comment`.`user_id` = `t_user`.`id` " +
            "WHERE `blog_id` = #{blog_id} AND `parent_id` = -1 ORDER BY `createTime`")
    List<Comment> listComments(@Param("blog_id") Integer blog_id);
}
