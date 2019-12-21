package com.jit.service;

import com.jit.pojo.Type;

import java.util.List;

/**
 * @program: forum
 * @description: TypeService接口
 * @author: XZQ
 * @create: 2019-11-15 16:19
 **/
public interface TypeService {

    Integer saveType(String typeName);

    Integer deleteTypeById(Integer typeId);

    Integer updateType(Type type);

    List<Type> findAllType();

    List<Type> findHottestType(Integer counts);

    List<Type> findAllTypeByUserId(Integer userId);

    Type findTypeByTypeId(Integer typeId);

    Type findTypeByTypeName(String typeName);

}
