package online.ptsports.PTSports.Service;




import online.ptsports.PTSports.DTO.SizeDto;
import online.ptsports.PTSports.Entity.Size;

import java.util.List;

public interface SizeService {
    SizeDto createSize(SizeDto sizeDto);
    SizeDto updateSize(SizeDto sizeDto,Integer sizeId);
    SizeDto getSizeById(Integer sizeId);
    List<SizeDto> getAllSize();
    void deleteSize(Integer sizeId);
    SizeDto convertToSizeDto(Size size);
    Size convertToSize(SizeDto sizeDto);
    void addSizesService(List<Size> sizes);

    void deleteSizesService(List<Integer> ids);

}