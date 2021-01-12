package pl.coderslab.charity.category;

import org.springframework.stereotype.Service;
import pl.coderslab.charity.model.Category;

import java.util.List;
import java.util.Optional;

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
    public List<Category> getAllCategories() {
        return  categoryRepository.findAll();
    }

}
