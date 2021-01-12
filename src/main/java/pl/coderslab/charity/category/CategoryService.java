package pl.coderslab.charity.category;

import pl.coderslab.charity.donation.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<Category> getCategory(Long id);
    List<Category> getAllCategories();

}
