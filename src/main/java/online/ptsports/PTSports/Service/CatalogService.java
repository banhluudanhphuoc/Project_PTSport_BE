package online.ptsports.PTSports.Service;




import online.ptsports.PTSports.DTO.CatalogDto;
import online.ptsports.PTSports.Entity.Catalog;

import java.util.List;

public interface CatalogService {
    CatalogDto createCatalog(CatalogDto catalogDto);
    CatalogDto updateCatalog(CatalogDto catalogDto,Integer catelogId);
    CatalogDto getCatalogById(Integer catelogId);
    List<CatalogDto> getAllCatalogs();
    void deleteCatalog(Integer catelogId);
    void addCatalogsService(List<Catalog> catalogs);

    void deleteCatalogsService(List<Integer> ids);

}
