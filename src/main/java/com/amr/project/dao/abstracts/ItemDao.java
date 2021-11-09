package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Item;

import java.util.List;

public interface ItemDao extends ReadWriteDAO<Item, Long> {
    Item findItemById(Long id);

    Item findItemByName(String name);

    List<Item> getItemsByShopId(Long id);

    List<Item> getSoldItemsByShopId(Long id);

    List<Item> getUnmoderatedItems();

    List<Item> getModeratedItems();

}
