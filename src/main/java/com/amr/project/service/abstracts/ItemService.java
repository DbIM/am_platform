package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Item;

import java.util.List;

public interface ItemService {
    List<Item> allItem();
    Item itemById(int id);
    void save(Item item);
    void update(Item item);
    void delete(Item item);
}
