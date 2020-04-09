package com.jit.mapper;

import com.jit.pojo.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-14 12:00
 **/
@Mapper
public interface TypeMapper {

    /**
     * 添加标签
     *
     * @param typeName
     * @return
     */
    @Insert("INSERT INTO t_type SET `name`= #{typeName}")
    Integer saveType(@Param("typeName") String typeName);

    /**
     * 通过TypeId删除Type
     *
     * @param typeId
     * @return
     */
    @Delete("DELETE FROM t_type WHERE id =#{typeId}")
    Integer deleteTypeByTypeId(@Param("typeId") Integer typeId);

    /**
     * 通过TypeId修改Type
     *
     * @param type
     * @return
     */
    @Update("UPDATE t_type SET `name`=#{type.name} , `counts` = #{type.counts} WHERE id =#{type.id}")
    Integer updateType(@Param("type") Type type);

    /**
     * 查询最热的counts个Type
     *
     * @return
     */
    @Select("SELECT * FROM t_type ORDER BY counts DESC limit #{counts}")
    List<Type> findHottestType(@Param("counts") Integer counts);

    /**
     * 查询所有的Type
     *
     * @return
     */
    @Select("SELECT * FROM t_type ")
    List<Type> findAllType();

    /**
     * 通过TypeId查询某个Type
     *
     * @param typeId
     * @return
     */
    @Select("SELECT * FROM `t_type` WHERE `id` = #{typeId}")
    Type findTypeByTypeId(@Param("typeId") Integer typeId);

    /**
     * 通过typeName查询Type
     *
     * @param typeName
     * @return
     */
    @Select("SELECT * FROM `t_type` WHERE `name` = #{typeName}")
    Type findTypeByTypeName(@Param("typeName") String typeName);

    /**
     * 通过用户Id查询其所有的Type
     *
     * @param userId
     * @return
     */
    @Select("SELECT DISTINCT `t_type`.`id`,`name` ,`counts` FROM `t_blog` LEFT JOIN `t_type` ON `t_blog`.`type_id`=`t_type`.`id` WHERE `t_blog`.`user_id` = #{userId}")
    List<Type> findAllTypeByUserId(@Param("userId") Integer userId);
}
