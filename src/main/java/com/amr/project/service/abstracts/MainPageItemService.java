package com.amr.project.service.abstracts;

import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Item;

import java.util.List;

public interface MainPageItemService extends ReadWriteService<Item,Long>{
    List<ItemDto> findPopularItems();
}
