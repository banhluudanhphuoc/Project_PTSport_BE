package online.ptsports.PTSports.Service;





import online.ptsports.PTSports.DTO.ColorDto;
import online.ptsports.PTSports.Entity.Color;

import java.util.List;

public interface ColorService {
    ColorDto createColor(ColorDto colorDto);
    ColorDto updateColor(ColorDto colorDto,Integer colorId);
    ColorDto getColorById(Integer colorId);
    List<ColorDto> getAllColor();
    void deleteColor(Integer colorId);
    ColorDto convertToColorDto(Color color);
    Color convertToColor(ColorDto colorDto);

    void addColorsService(List<Color> colors);

    void deleteColorsService(List<Integer> ids);

}

