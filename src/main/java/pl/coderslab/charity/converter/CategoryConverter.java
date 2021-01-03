package pl.coderslab.charity.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.coderslab.charity.category.CategoryService;
import pl.coderslab.charity.model.Category;

import javax.persistence.EntityNotFoundException;

public class CategoryConverter implements Converter<String, Category> {

    @Autowired
    private CategoryService categoryService;

    @Override
    public Category convert(String source) {
        return categoryService.getCategory(Long.parseLong(source)).orElseThrow(EntityNotFoundException::new);
    }
}
