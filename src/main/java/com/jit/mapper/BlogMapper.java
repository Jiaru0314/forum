package com.jit.mapper;

import com.jit.pojo.Blog;
import com.jit.vo.BlogDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-13 20:27
 **/
@Mapper
public interface BlogMapper {

    /**
     * 保存blog
     *
     * @param blog
     */
    @Insert("INSERT INTO `t_blog`(`title`,`content`,`create_time`,`user_id`,`views`,`prefers`,`type_id`,`is_public`,`is_commentabled`,`tagIds`) " +
            "VALUES(#{blog.title},#{blog.content},#{blog.create_time},#{blog.user_id},0,0,#{blog.type_id},#{blog.is_public},#{blog.is_commentabled},#{blog.tagIds})")
    Integer saveBlog(@Param("blog") Blog blog);

    /**
     * 通过Id删除Blog
     *
     * @param id
     */
    @Delete("delete from t_blog where id=#{id} ")
    Integer deleteBlogById(@Param("id") Integer id);

    /**
     * 通过Id修改Blog
     *
     * @param blog
     */
    @Update("update t_blog set `title`=#{blog.title},`content`=#{blog.content}," +
            "`views`=#{blog.views},`prefers`=#{blog.prefers},`type_id`=#{blog.type_id}," +
            "`is_public`=#{blog.is_public},`is_commentabled`=#{blog.is_commentabled},`tagIds`=#{blog.tagIds} " +
            "where id = #{blog.id}")
    Integer updateBlog(@Param("blog") Blog blog);

    /**
     * 通过BlogId查询Blog
     *
     * @param id
     * @return
     */
    @Select("select * from t_blog where id = #{id}")
    Blog getBlogById(@Param("id") Integer id);

    /**
     * 通过用户Id查询Blog
     *
     * @param id
     * @return
     */
    @Select("SELECT `t_blog`.`id`,`title`,`create_time`,`user_id`,`views`,`prefers`,`name` typeName FROM `t_blog` " +
            "LEFT JOIN `t_type` ON `t_blog`.`type_id`=`t_type`.`id` where user_id = #{id}")
    List<BlogDto> findBlogsByUserId(@Param("id") Integer id);

    /**
     * 通过用户Id查询其Blog数量
     *
     * @param userId
     * @return
     */
    @Select("SELECT COUNT(*) FROM `t_blog` WHERE `user_id` = #{userId}")
    Integer findBlogCountsByUserId(@Param("userId") Integer userId);

    /**
     * 通过BlogId查询BlogDto
     *
     * @param blogId
     * @return
     */
    @Select("SELECT `t_blog`.`id`,`title`,`content`,`create_time`,`views`,`user_id`,`avatar`," +
            "`tagIds`,`name` typeName ,`username` ,`is_commentabled`,`prefers`,`t_type`.`id` type_id FROM `t_blog` " +
            "LEFT JOIN `t_type` ON `type_id` = `t_type`.`id` " +
            "LEFT JOIN `t_user` ON `user_id` =`t_user`.`id` " +
            "WHERE `t_blog`.id =  #{blogId}")
    BlogDto findBlogDtoByBlogId(@Param("blogId") Integer blogId);

    /**
     * 查看最新的rows篇Blog
     *
     * @param rows
     * @return
     */
    @Select("SELECT `t_blog`.`id`,`title`,`content`,`create_time`,`user_id`,`views` ,`avatar`,`username` FROM `t_blog` " +
            "LEFT JOIN `t_user` ON `user_id` =`t_user`.`id` ORDER BY create_time DESC LIMIT #{rows}")
    List<BlogDto> findNewestBlogs(@Param("rows") Integer rows);

    /**
     * 查看热门Blog 以views排序
     *
     * @return
     */
    @Select("SELECT `t_blog`.`id`,`title`,`content`,`create_time`,`user_id`,`views` ,`avatar`,`username` FROM `t_blog` " +
            "LEFT JOIN `t_user` ON `user_id` =`t_user`.`id`  ORDER BY views DESC")
    List<BlogDto> findHottestBlogs();

    /**
     * 通过TagId查询所有Blog
     *
     * @param tagId
     * @return
     */
    @Select("SELECT `t_blog`.`id`,`title`,`content`,`create_time`,`user_id`,`views` ,`avatar`,`username`,`name` typeName,`t_type`.`id` type_id  " +
            "FROM `t_blog` LEFT JOIN `t_user` ON `user_id` =`t_user`.`id` LEFT JOIN `t_type` ON `type_id` = `t_type`.`id` WHERE `tagIds` LIKE concat('%', #{tagId}, '%')")
    List<BlogDto> findBlogsByTagId(@Param("tagId") Integer tagId);

    /**
     * 以分组查询所有Blog的年份
     *
     * @return
     */
    @Select("SELECT DATE_FORMAT(b.`create_time`,'%Y') AS YEAR FROM `t_blog` b " +
            "WHERE b.`user_id` = #{userId} GROUP BY DATE_FORMAT(b.`create_time`,'%Y')ORDER BY YEAR DESC")
    List<String> findGroupYear(@Param("userId") Integer userId);

    /**
     * 查询某年的Blog
     *
     * @param year
     * @return
     */
    @Select("select * from t_blog b where DATE_FORMAT(b.`create_time`,'%Y') = #{year} AND b.`user_id` = #{userId}")
    List<Blog> findBlogByYear(@Param("year") String year, @Param("userId") Integer userId);

    /**
     * 通过TypeId查询BlogDto
     *
     * @param typeId
     * @return
     */
    @Select("SELECT `t_blog`.`id`,`title`,`content`,`create_time`,`user_id`,`views` ,`avatar`,`username`,`name` typeName,`t_type`.`id` type_id  FROM `t_blog` " +
            "LEFT JOIN `t_user` ON `user_id` =`t_user`.`id` " +
            "LEFT JOIN `t_type` ON `type_id` = `t_type`.`id` WHERE `t_blog`.`type_id` =#{typeId} ORDER BY views DESC ")
    List<BlogDto> findBlogByTypeId(@Param("typeId") Integer typeId);

    /**
     * 通过关注的Id集合查询其Blog(XML)
     *
     * @param idList
     * @return
     */
    List<BlogDto> findFollowedUserBlogs(@Param("idList") List<Integer> idList);

    /**
     * 通过query字符串模糊查询BLog
     *
     * @param query
     * @return
     */
    @Select("SELECT `t_blog`.`id`,`title`,`content`,`create_time`,`user_id`,`views` ,`avatar`,`username`,`name` typeName,`t_type`.`id` type_id  " +
            "FROM `t_blog` LEFT JOIN `t_user` ON `user_id` =`t_user`.`id` " +
            "LEFT JOIN `t_type` ON `type_id` = `t_type`.`id` " +
            "WHERE `title` LIKE concat('%', #{query}, '%') or `content` LIKE concat('%', #{query}, '%') OR `t_type`.`name` LIKE concat('%', #{query}, '%')")
    List<BlogDto> findBlogByTitleLike(@Param("query") String query);

    /**
     * 随机查询十条blog
     *
     * @return
     */
    @Select("SELECT `t_blog`.`id`,`title`,`content`,`create_time`,`user_id`,`views` ,`prefers`,`avatar`,`username`,`name` typeName,`t_type`.`id` type_id  " +
            "FROM `t_blog` LEFT JOIN `t_user` ON `user_id` =`t_user`.`id` " +
            "LEFT JOIN `t_type` ON `type_id` = `t_type`.`id` " +
            "ORDER BY RAND() LIMIT 10")
    List<BlogDto> listRandBlogDto();


//    SELECT * FROM `t_blog`  ORDER BY RAND() LIMIT 10;
}
