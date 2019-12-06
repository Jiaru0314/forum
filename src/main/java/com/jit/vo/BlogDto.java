package com.jit.vo;

import com.jit.pojo.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-19 09:59
 **/
@Data
@NoArgsConstructor
public class BlogDto {
    private Integer id;

    private String title;

    private String content;

    private Date create_time;

    private Integer views;

    private Integer prefers;

    private String avatar;

    private String username;

    private String typeName;

    private String tagIds;

    private List<Tag> tags;

    private Integer user_id;

    private Integer type_id;

    private Boolean is_commentabled;

}
