package com.imooc.mimall.vo;

import lombok.Data;

import java.util.List;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/1 11:47
 */
@Data
public class CategoryVo {
    private Integer id;

    private Integer parentId;

    private String name;

    private Integer sortOrder;

    private List<CategoryVo> subCategories;
}
