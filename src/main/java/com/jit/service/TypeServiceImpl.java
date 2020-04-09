package com.jit.service;

import com.jit.mapper.TypeMapper;
import com.jit.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: forum
 * @description: TypeService接口实现类
 * @author: XZQ
 * @create: 2019-11-15 16:23
 **/
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Integer saveType(String typeName) {
        return typeMapper.saveType(typeName);
    }

    @Override
    public Integer deleteTypeById(Integer typeId) {
        return typeMapper.deleteTypeByTypeId(typeId);
    }

    @Override
    public Integer updateType(Type type) {
        return typeMapper.updateType(type);
    }

    @Override
    public List<Type> findAllType() {
        return typeMapper.findAllType();
    }


    @Override
    public List<Type> findHottestType(Integer counts) {
        //从redis中获取数据
        List<Type> typeList = redisTemplate.boundListOps("hotTypeList").range(0, -1);
        //如果数据不存在
        if (null == typeList || typeList.size() == 0) {
            System.out.println("从数据库中获取type，并将数据写入缓存");
            typeList = typeMapper.findHottestType(counts);
            for (Type type : typeList) {
                redisTemplate.boundListOps("hotTypeList").rightPush(type);
            }
        } else {
            System.out.println("从redis中获取type");
        }
        return typeList;
    }

    @Override
    public List<Type> findAllTypeByUserId(Integer userId) {
        return typeMapper.findAllTypeByUserId(userId);
    }

    @Override
    public Type findTypeByTypeId(Integer typeId) {
        return typeMapper.findTypeByTypeId(typeId);
    }

    @Override
    public Type findTypeByTypeName(String typeName) {
        return typeMapper.findTypeByTypeName(typeName);
    }
}
