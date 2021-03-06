package com.amr.project.service.abstracts;

import com.amr.project.model.dto.CategoryDto;
import com.amr.project.model.entity.Category;

import java.util.List;

public interface CategoryService extends ReadWriteService<Category, Long> {
    Category getCategoryByName(String name);

    List<CategoryDto> getCategoryDto();
}
