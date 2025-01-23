package com.ladysparks.ttaenggrang.service;

import com.ladysparks.ttaenggrang.domain.Item;
import com.ladysparks.ttaenggrang.repository.ItemRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRespository itemRepository;

    @Autowired
    public ItemService(ItemRespository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public int saveItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    public Optional<Item> findItem(int itemId) {
        return itemRepository.findById(itemId);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

}
