package com.imooc.mimall.pojo;

import lombok.Data;

import java.util.Date;

@Data       //自动生成get set toString方法
public class Category {
    private Integer id;
    private Integer parentId;
    private String name;
    private Integer status;
    private Integer sortOrder;
    private Date createTime;
    private Date updateTime;
}
