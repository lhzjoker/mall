package com.imooc.mimall.service.Impl;

import com.imooc.mimall.consts.MallConst;
import com.imooc.mimall.dao.CategoryMapper;
import com.imooc.mimall.pojo.Category;
import com.imooc.mimall.service.ICategoryService;
import com.imooc.mimall.vo.CategoryVo;
import com.imooc.mimall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/1 11:56
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {

        //查出数据库中所有的数据
        List<Category> categories = categoryMapper.selectAll();
        //先查出parent_id为0的数据
//        List<CategoryVo> categoryVoList = new ArrayList<>();
//        for (Category category : categories) {
//            if (category.getParentId().equals(MallConst.ROOT_PARENT_ID)) {
//                CategoryVo categoryVo = categoryTocagoryVo(category);
//                categoryVoList.add(categoryVo);
//            }
//        }

        //lambda + stream
        List<CategoryVo> categoryVoList = categories.stream()
                .filter(e->e.getParentId().equals(MallConst.ROOT_PARENT_ID))    //选出parent_id为0的数据
                .map(this::categoryTocagoryVo)      //category转换为categoryVo
                .sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed())  //根据sort_order排序，大的在前面
                .collect(Collectors.toList());      //转换为list

        //查询子目录
        findSubCategory(categoryVoList, categories);
        return ResponseVo.success(categoryVoList);

    }

    private void findSubCategory(List<CategoryVo> categoryVoList, List<Category> categories) {
        for (CategoryVo categoryVo : categoryVoList) {
//            List<CategoryVo> subCategoryList = new ArrayList<>();
//            for (Category category : categories) {
//                if (categoryVo.getId().equals(category.getParentId())) {
//                    CategoryVo subCategoryVo = categoryTocagoryVo(category);
//                    subCategoryList.add(subCategoryVo);
//                }
//            }
//            subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
            List<CategoryVo> subCategoryList = categories.stream()
                    .filter(e->e.getParentId().equals(categoryVo.getId()))
                    .map(this::categoryTocagoryVo)
                    .sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed())
                    .collect(Collectors.toList());
            categoryVo.setSubCategories(subCategoryList);
            //如果查到了数据，继续查，直到为null
            findSubCategory(subCategoryList, categories);
        }
    }

    private CategoryVo categoryTocagoryVo(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
