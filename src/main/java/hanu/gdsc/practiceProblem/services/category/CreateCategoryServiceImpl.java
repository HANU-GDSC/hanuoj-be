package hanu.gdsc.practiceProblem.services.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hanu.gdsc.practiceProblem.domains.Category;
import hanu.gdsc.practiceProblem.repositories.CategoryRepository;
import hanu.gdsc.share.domains.Id;

@Service
public class CreateCategoryServiceImpl implements CreateCategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Id create(Input input) {
        Category category = Category.create(input.name);
        categoryRepository.create(category);
        return category.getId();
    }
    
}