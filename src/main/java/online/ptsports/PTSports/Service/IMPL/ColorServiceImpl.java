package online.ptsports.PTSports.Service.IMPL;



import online.ptsports.PTSports.DTO.ColorDto;
import online.ptsports.PTSports.Entity.Color;
import online.ptsports.PTSports.Exeption.ResoureNotFoundException;
import online.ptsports.PTSports.Repository.ColorRepo;
import online.ptsports.PTSports.Service.ColorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColorServiceImpl implements ColorService {
    @Autowired
    ColorRepo colorRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ColorDto createColor(ColorDto colorDto) {
        Color color = this.convertToColor(colorDto);
        colorRepo.save(color);
        return this.convertToColorDto(color);
    }

    @Override
    public ColorDto updateColor(ColorDto colorDto, Integer colorId) {
        Color color = colorRepo.findById(colorId).orElseThrow(()
                ->new ResoureNotFoundException("Color", "ID", colorId));
        Color color1 = this.convertToColor(colorDto);
        color1.setId(color.getId());
        colorRepo.save(color1);
        return this.convertToColorDto(color1);
    }

    @Override
    public ColorDto getColorById(Integer colorId) {
        Color color = colorRepo.findById(colorId).orElseThrow(()->new ResoureNotFoundException("Color", "ID", colorId));
        return this.convertToColorDto(color);
    }

    @Override
    public List<ColorDto> getAllColor() {
        List<Color> colorList = colorRepo.findAll();
        List<ColorDto> colorDtoList = new ArrayList<>();

        for (int i = 0; i < colorList.size(); i++) {
            colorDtoList.add(this.convertToColorDto(colorList.get(i)));
        }
        return colorDtoList;
    }

    @Override
    public void deleteColor(Integer colorId) {
        Color color = colorRepo.findById(colorId).orElseThrow(()->new ResoureNotFoundException("Color", "ID", colorId));
        colorRepo.deleteById(color.getId());
    }

    @Override
    public ColorDto convertToColorDto(Color color) {
        ColorDto colorDto = this.modelMapper.map(color, ColorDto.class);

        return colorDto;
    }

    @Override
    public Color convertToColor(ColorDto colorDto) {
        Color color = this.modelMapper.map(colorDto, Color.class);
        return color;
    }

    @Override
    public void addColorsService(List<Color> colors) {
        colorRepo.saveAll(colors);
    }

    @Override
    public void deleteColorsService(List<Integer> ids) {
        colorRepo.deleteAllById(ids);

    }
}
