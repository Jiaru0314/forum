package com.jit.service;

import com.jit.mapper.TypeMapper;
import com.jit.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
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
        return typeMapper.findHotsestType(counts);
    }

    @Override
    public List<Type> findAllTypeByUserId(Integer userId) {
        return typeMapper.findAllTypeByUserId(userId);
    }

    @Override
    public Type findTypeByTypeId(Integer typeId) {
        return typeMapper.findTypeByTypeId(typeId);
    }
}
