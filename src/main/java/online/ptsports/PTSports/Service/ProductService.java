package online.ptsports.PTSports.Service;





import online.ptsports.PTSports.DTO.PageDto;
import online.ptsports.PTSports.DTO.ProductDto;
import online.ptsports.PTSports.DTO.SearchDto;
import online.ptsports.PTSports.Entity.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDto getProductById(Integer generalId);

    List<ProductDto> getAllProduct();

    ProductDto createProduct(ProductDto productDto) throws IOException;

    ProductDto updateProduct(ProductDto productDto, Integer generalId);

    void deleteProduct(Integer generalId);


    List<ProductDto> searchProductByName(String productName);

    PageDto<ProductDto> getProductsHomePage(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    List<ProductDto>search(SearchDto searchDto);

    List<ProductDto>filterByLength(int id);

    List<ProductDto>filterByCatalog(int id);

    Product convertToProduct(ProductDto productDto);

    ProductDto convertToProductDto(Product product);

    int count();
}
