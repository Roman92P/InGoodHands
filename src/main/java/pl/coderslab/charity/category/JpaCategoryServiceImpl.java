package pl.coderslab.charity.category;

import org.springframework.stereotype.Service;
import pl.coderslab.charity.model.Category;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class JpaCategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public  JpaCategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    @Override
    public Optional<Category> getCategory(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return  categoryRepository.findAll();
    }

    @Override
    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void removeCategory(Category category) {
        categoryRepository.delete(category);
    }
}
