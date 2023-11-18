package online.ptsports.PTSports.Service.IMPL;



import online.ptsports.PTSports.DTO.CatalogDto;
import online.ptsports.PTSports.Entity.Catalog;
import online.ptsports.PTSports.Exeption.ResoureNotFoundException;
import online.ptsports.PTSports.Repository.CatalogRepo;
import online.ptsports.PTSports.Service.CatalogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    CatalogRepo catalogRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CatalogDto createCatalog(CatalogDto catalogDto) {
        Catalog catalog = this.convertToCatalog(catalogDto);
        catalogRepo.save(catalog);
        return catalogDto;

    }

    @Override
    public CatalogDto updateCatalog(CatalogDto catalogDto, Integer catelogId) {
        Catalog catalog = catalogRepo.findById(catelogId).orElseThrow(()
                -> new ResoureNotFoundException("Catalog", "ID", catelogId));
        Catalog catalogNew = this.convertToCatalog(catalogDto);
        catalogNew.setCatalogId(catalog.getCatalogId());
        catalogRepo.save(catalogNew);
        return this.convertToCatalogDto(catalogNew);
    }

    @Override
    public CatalogDto getCatalogById(Integer catelogId) {
        Catalog catalog = catalogRepo.findById(catelogId).orElseThrow(()->new ResoureNotFoundException("Catalog", "ID", catelogId));

        return this.convertToCatalogDto(catalog);
    }

    @Override
    public List<CatalogDto> getAllCatalogs() {
        List<Catalog> catalogList = catalogRepo.findAll();
        List<CatalogDto> catalogDtos = new ArrayList<>();
        for (int i = 0; i< catalogList.size(); i++){
            catalogDtos.add(this.convertToCatalogDto(catalogList.get(i)));
        }
        return catalogDtos;
    }

    @Override
    public void deleteCatalog(Integer catelogId) {
        Catalog catalog = catalogRepo.findById(catelogId).orElseThrow(()->new ResoureNotFoundException("Catalog", "ID", catelogId));
        catalogRepo.deleteById(catalog.getCatalogId());
    }

    @Override
    public void addCatalogsService(List<Catalog> catalogs) {
        catalogRepo.saveAll(catalogs);
    }

    @Override
    public void deleteCatalogsService(List<Integer> ids) {
            catalogRepo.deleteAllById(ids);
    }

    public CatalogDto convertToCatalogDto(Catalog catalog){
        return this.modelMapper.map(catalog, CatalogDto.class);

    }
    public Catalog convertToCatalog(CatalogDto catalogDto){
        return this.modelMapper.map(catalogDto, Catalog.class);

    }

}
