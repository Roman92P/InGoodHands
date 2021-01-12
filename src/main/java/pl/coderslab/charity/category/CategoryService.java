package pl.coderslab.charity.category;

import pl.coderslab.charity.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CategoryService {
    Optional<Category> getCategory(Long id);
    List<Category> getAllCategories();

}
