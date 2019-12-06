package com.jit.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: forum
 * @description: Tag实体类
 * @author: XZQ
 * @create: 2019-11-14 11:47
 **/
@Data
@NoArgsConstructor
public class Tag {

    private Integer id;

    private String name;

    private Integer counts;

}

