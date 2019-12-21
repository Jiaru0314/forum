package com.jit.mapper;

import com.jit.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-13 14:57
 **/
@Mapper
public interface UserMapper {

    /**
     * 添加新用户
     *
     * @param username
     * @param password
     */
    @Insert("INSERT INTO `t_user`(`username`,`password`) VALUES (#{username},#{password})")
    Integer saveUser(@Param("username") String username, @Param("password") String password);

    /**
     * 修改用户信息
     *
     * @param user
     */
    @Update("UPDATE `t_user` SET `password` =#{user.password} ,`nickname`=#{user.nickname},`avatar` =#{user.avatar} ," +
            "`email` =#{user.email} ,`description` =#{user.description} ,`phone` = #{user.phone},`github` =#{user.github} ,`follow` = #{user.follow},`fans` =#{user.fans} ,`follow_ids` = #{user.follow_ids},`fans_ids` =#{user.fans_ids} WHERE `id` = #{user.id}")
    void updateUser(@Param("user") User user);

    /**
     * 通过用户名和密码查询用户
     *
     * @param username
     * @param password
     * @return User
     */
    @Select("select * from t_user where username = #{username} and password = #{password}")
    User findUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 通过Id查询用户
     *
     * @param id
     * @return User
     */
    @Select("select * from t_user where id = #{id}")
    User findUserById(@Param("id") Integer id);

    /**
     * 查询十个热门博主 按粉丝数量排序
     *
     * @return
     */
    @Select("SELECT * FROM t_user ORDER BY `fans` DESC LIMIT 10")
    List<User> findHotUsers();

    /**
     * 通过用户名查询用户
     *
     * @param username
     * @return User
     */
    @Select("select * from t_user where username = #{username}")
    User findUserByUsername(@Param("username") String username);

    

    /*XML配置方式*/

    /**
     * 通过IdList查询相关联的Users
     *
     * @param idList
     * @return
     */
    List<User> findRelatedUsers(@Param("idList") List<Integer> idList);
}
