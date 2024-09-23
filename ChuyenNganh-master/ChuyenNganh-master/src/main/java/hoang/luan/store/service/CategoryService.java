package hoang.luan.store.service;


import hoang.luan.store.model.Category;
import hoang.luan.store.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategorys() {
        return categoryRepository.findAll();
    }


    public void saveCategory(Category category) {
        this.categoryRepository.save(category);
    }


    public Category getCategoryById(int id) {
        Optional<Category>optional = categoryRepository.findById(id);
        Category category=null;
        if(optional.isPresent()){
            category=optional.get();

        }else {
            throw new RuntimeException("Category not found for id::"+id);
        }
        return category;
    }



    public void deleteCategoryById(int id) {
        this.categoryRepository.deleteById(id);
    }
}
