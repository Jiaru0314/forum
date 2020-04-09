package com.jit.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: forum
 * @description: Type实体类
 * @author: XZQ
 * @create: 2019-11-15 15:00
 **/
@Data
@NoArgsConstructor
public class Type implements Serializable {

    private Integer id;

    private String name;

    private Integer counts;

    private List<Type> types = new ArrayList<>();

}
