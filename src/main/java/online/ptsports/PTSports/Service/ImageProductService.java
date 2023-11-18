package online.ptsports.PTSports.Service;





import online.ptsports.PTSports.DTO.ImageProductDto;

import java.util.List;

public interface ImageProductService {
    ImageProductDto createImageProduct(ImageProductDto imageProductDto);
    void deleteImageProduct(Integer imageId);
    ImageProductDto updateImageProduct(ImageProductDto imageProductDto, Integer imageId);
    List<ImageProductDto> getAllImageForProduct(Integer productId);

}
