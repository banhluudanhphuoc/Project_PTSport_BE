package online.ptsports.PTSports.Service;





import online.ptsports.PTSports.DTO.CategoryDto;
import online.ptsports.PTSports.Entity.Category;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
    CategoryDto getCategoryById(Integer categoryId);
    List<CategoryDto> getAllCategorys();
    void deleteCategory(Integer categoryId);
    void addCategorysService(List<Category> categoryId);
    void deleteCategorysService(List<Integer> ids);
    CategoryDto convertToCategoryDto(Category category);
    Category convertToCategory(CategoryDto categoryDto);
}
