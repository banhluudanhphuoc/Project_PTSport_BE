package online.ptsports.PTSports.Service.IMPL;



import online.ptsports.PTSports.DTO.DiscountDto;
import online.ptsports.PTSports.DTO.PageDto;
import online.ptsports.PTSports.DTO.ProductDto;
import online.ptsports.PTSports.DTO.SearchDto;
import online.ptsports.PTSports.Entity.*;
import online.ptsports.PTSports.Exeption.ResoureNotFoundException;
import online.ptsports.PTSports.Repository.*;
import online.ptsports.PTSports.Service.DiscountService;
import online.ptsports.PTSports.Service.FileUploadCloudinary;
import online.ptsports.PTSports.Service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    DiscountService discountService;


    @Autowired
    ProductRepo productRepo;

    @Autowired
    CatalogRepo catalogRepo;


    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ImageProductRepo imageProductRepo;

    @Autowired
    ColorRepo colorRepo;

    @Autowired
    SizeRepo sizeRepo;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    LengthRepo lengthRepo;

    @Autowired
    private FileUploadCloudinary fileUploadCloudinary ;

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private DiscountRepo discountRepo;

    @Override
    public ProductDto getProductById(Integer productId) {
        Product product = productRepo.findById(productId).orElseThrow(()
                -> new ResoureNotFoundException("Product", "ID", productId));

        return this.convertToProductDto(product);
    }

    @Override
    public List<ProductDto> getAllProduct() {
        List<Product> products = productRepo.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            ProductDto productDto = convertToProductDto(products.get(i));
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {

        try {
            Category category = categoryRepo.findById(productDto.getCategoryID())
                    .orElseThrow(() -> new ResoureNotFoundException("Category", "ID", String.valueOf(productDto.getCategoryID())));

            Catalog catalog = catalogRepo.findById(productDto.getCatalogID())
                    .orElseThrow(() -> new ResoureNotFoundException("Catalog", "ID", String.valueOf(productDto.getCatalogID())));


            Length length = lengthRepo.findById(productDto.getLengthIDX())
                    .orElseThrow(() -> new ResoureNotFoundException("Length", "ID", String.valueOf(productDto.getLengthIDX())));


            Product product = this.convertToProduct(productDto);

            List<Integer> sizesID = productDto.getSizesID();
            List<Size> lisSizes = new ArrayList<>();
            for (int i = 0; i < sizesID.size(); i++) {
                int finalI = i;
                Size size = sizeRepo.findById(sizesID.get(i)).orElseThrow(() -> new ResoureNotFoundException("Size", "ID", sizesID.get(finalI)));
                size.getProductList().add(product);
                lisSizes.add(size);
            }
            product.setSizes(lisSizes);



            List<Integer> colorID = productDto.getColorsID();
            Set<Color> lisColors = new HashSet<>();
            for (int i = 0; i < colorID.size(); i++) {
                int finalI = i;
                Color color = colorRepo.findById(colorID.get(i)).orElseThrow(() -> new ResoureNotFoundException("Color", "ID", colorID.get(finalI)));
                color.getProductList().add(product);
                lisColors.add(color);
            }
            product.setColors(lisColors);


            Set<MultipartFile> files = productDto.getFiles();

            for (MultipartFile file:files) {
                String imageURL = fileUploadCloudinary.uploadFile(file);
                ImageProduct imageProduct = new ImageProduct();
                imageProduct.setProduct(product);
                imageProduct.setPath(imageURL);
                imageProduct.setTitle(imageURL);
                product.addProductImages(imageProduct);
            }
            product.setDiscountedPrice(product.getPrice());
            product.setCatalog(catalog);
            product.setCategory(category);
            product.setLength(length);
            productRepo.save(product);

            return this.convertToProductDto(product);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return productDto;
    }




    @Override
    public ProductDto updateProduct(ProductDto productDto, Integer productId) {
        try {

            //lấy ảnh của productDto
            Set<MultipartFile> files = productDto.getFiles();
//product gốc

            Product product0 = productRepo.findById(productId).orElseThrow(() -> new ResoureNotFoundException("Product", "ID", productId));
//ds ảnh của product gốc
            List<ImageProduct> imageProductList = product0.getListImage();
//xóa ảnh trong db
            Product product = this.convertToProduct(productDto);//moi
            if (files == null || files.size() == 0) {

                product.setListImage(product0.getListImage());
            } else {//goc

                //delete trong db
                for (ImageProduct image : imageProductList) {
                    image.setProduct(null);
                    imageProductRepo.save(image);
                    imageProductRepo.deleteById(image.getId());
                }

                for (ImageProduct image : product0.getListImage()) {
                    fileUploadCloudinary.deleteImage(image.getPath());
                }
                product0.clearProductImages();//remove old image
                productRepo.save(product0);

                for (MultipartFile file:files) {
                    String imageURL = fileUploadCloudinary.uploadFile(file);
                    ImageProduct imageProduct = new ImageProduct();
                    imageProduct.setProduct(product);
                    imageProduct.setPath(imageURL);
                    imageProduct.setTitle(imageURL);
                    product.addProductImages(imageProduct);
                }

            }

            product.setId(product0.getId());


            Set<Color> colors = new HashSet<>();
            List<Integer> colorID = productDto.getColorsID();
            for (Integer i : colorID) {
                colors.add(colorRepo.findById(i).orElseThrow(() -> new ResoureNotFoundException("Color", "ID", i)));
            }
            product.setColors(colors);

            List<Size> sizes = new ArrayList<>();
            List<Integer> sizesID = productDto.getSizesID();
            for (Integer i : sizesID) {
                sizes.add(sizeRepo.findById(i).orElseThrow(() -> new ResoureNotFoundException("Size", "ID", i)));
            }

            Catalog catalog = catalogRepo.findById(productDto.getCatalogID()).orElseThrow(() -> new ResoureNotFoundException("Catalog", "ID", productDto.getCatalogID()));
            Length length = lengthRepo.findById(productDto.getLengthIDX()).orElseThrow(() -> new ResoureNotFoundException("Length", "ID", productDto.getLengthIDX()));
            Category category = categoryRepo.findById(productDto.getCategoryID()).orElseThrow(() -> new ResoureNotFoundException("Category", "ID", productDto.getCategoryID()));

            if (productDto.getTotalQuantity() == null) {
                product.setTotalQuantity(product0.getTotalQuantity());
            }
            product.setSizes(sizes);
            product.setCatalog(catalog);
            product.setLength(length);
            product.setCategory(category);
            productRepo.save(product);

            return this.convertToProductDto(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productDto;
    }

    @Override
    public void deleteProduct(Integer generalId) {
        Product product = productRepo.findById(generalId).orElseThrow(() -> new ResoureNotFoundException("General Product", "ID", generalId));
        List<ImageProduct> imageProductList = product.getListImage();

        for (ImageProduct image : imageProductList) {
            fileUploadCloudinary.deleteImage(image.getPath());
        }
        productRepo.deleteById(product.getId());
    }



    @Override
    public Product convertToProduct(ProductDto productDto) {
        Product product = this.modelMapper.map(productDto, Product.class);

        return product;
    }

    @Override
    public ProductDto convertToProductDto(Product product) {
        ProductDto productDto = this.modelMapper.map(product, ProductDto.class);

        List<Size> listSize = product.getSizes();
        for (Size size : listSize) {
            productDto.getSizesID().add(size.getId());
        }

        Set<Color> listColor = product.getColors();
        for (Color color : listColor) {
            productDto.getColorsID().add(color.getId());
        }

        productDto.setLengthIDX(product.getLength().getId());
        return productDto;
    }



    @Override
    public List<ProductDto> searchProductByName(String productName) {
        List<ProductDto> productDtoList = new ArrayList<>();
        List<Product> productList = productRepo.searchByName(productName);
        for (Product product : productList) {
            productDtoList.add(this.convertToProductDto(product));
        }
        return productDtoList;
    }

    @Override
    public PageDto<ProductDto> getProductsHomePage(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Product> pageProducts = productRepo.findAll(pageDetails);

        List<Product> products = pageProducts.getContent();

        List<ProductDto> productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());

        PageDto<ProductDto> productResponse = new PageDto<>();

        productResponse.setContents(productDTOs);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());

        return productResponse;
    }

    @Override
    public List<ProductDto> search(SearchDto searchDto) {

        List<Product> products = productRepo.findProductsBySizeColorAndCatalog(searchDto.getSize(), searchDto.getCatalogID(), searchDto.getColor(), searchDto.getStartPrice(), searchDto.getEndPrice(), searchDto.getLengthID());
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product p : products) {
            productDtos.add(this.convertToProductDto(p));
        }
        return productDtos;
    }

    @Override
    public List<ProductDto> filterByLength(int id) {
        List<Product> products = productRepo.filterByLength(id);
        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            productDtos.add(this.convertToProductDto(product));
        }
        return productDtos;
    }

    @Override
    public List<ProductDto> filterByCatalog(int id) {
        List<Product> products = productRepo.filterByCatalog(id);
        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            productDtos.add(this.convertToProductDto(product));
        }
        return productDtos;
    }

    @Override
    public List<ProductDto> filterByCategory(int id) {
        List<Product> products = productRepo.filterByCategory(id);
        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            productDtos.add(this.convertToProductDto(product));
        }
        return productDtos;
    }

    @Override
    public int count() {
        int count = productRepo.totalQuantity();
        return count;
    }



    @Override
    public void saveProduct(Product product) {
        productRepo.save(product);
    }

    @Override
    public Product getProductByIds(Integer productId) {
        return productRepo.findById(productId).orElseThrow(() ->
                new ResoureNotFoundException("Product", "ID", productId)
        );
    }


    @Override
    public void assignDiscountToProduct(Integer productId, Integer discountId) {
        Product product = getProductByIds(productId);
        Discount discount = discountService.getDiscountById(discountId);

        // Gọi phương thức trong Product để tính toán discountedPrice
        product.applyDiscount(discount);

        // Lưu lại thông tin đã cập nhật của Product
        saveProduct(product);
    }


}



