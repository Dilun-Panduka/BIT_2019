package lk.excellent.pharamacy_management.asset.item.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lk.excellent.pharamacy_management.asset.commonAsset.Enum.Category;
import lk.excellent.pharamacy_management.asset.commonAsset.service.SupplierItemService;
import lk.excellent.pharamacy_management.asset.item.entity.Item;
import lk.excellent.pharamacy_management.asset.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restItem")
public class ItemRestController {
    private final SupplierItemService supplierItemService;
    private final ItemService itemService;

    @Autowired
    public ItemRestController(SupplierItemService supplierItemService, ItemService itemService) {
        this.supplierItemService = supplierItemService;
        this.itemService = itemService;
    }
@GetMapping("/{category}")
    public MappingJacksonValue getItemByType(@PathVariable Category category) {

    //MappingJacksonValue
    List<Item> items = itemService.findByCategory(category);
    //employeeService.findByWorkingPlace(workingPlaceService.findById(id));

    //Create new mapping jackson value and set it to which was need to filter
    MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(items);

    //simpleBeanPropertyFilter :-  need to give any id to addFilter method and created filter which was mentioned
    // what parameter's necessary to provide
    SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter
            .filterOutAllExcept("id", "description", "sellingPrice", "soh");
    //filters :-  set front end required value to before filter

    FilterProvider filters = new SimpleFilterProvider()
            .addFilter("Item", simpleBeanPropertyFilter);
    //Employee :- need to annotate relevant class with JosonFilter  {@JsonFilter("Employee") }
    mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }
}
