package com.Lostfound.lostfound.service;

import com.Lostfound.lostfound.Repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Add item for userId from JWT
    public Item addItem(Item item, String userId) {
        item.setUserid(userId);
        item.init();
        return itemRepository.save(item);
    }

    // Get all items of a user
    public List<Item> getUserItems(String userId) {
        return itemRepository.findAll().stream()
                .filter(item -> userId.equals(item.getUserid())) // null-safe
                .collect(Collectors.toList());
    }

    // Get single item by ID, only if belongs to user
    public Item getItemById(String id, String userId) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        if (!userId.equals(item.getUserid())) {
            throw new RuntimeException("Unauthorized access");
        }
        return item;
    }

    // Delete item by ID, only if belongs to user
    public void deleteItem(String id, String userId) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        if (!userId.equals(item.getUserid())) {
            throw new RuntimeException("Unauthorized access");
        }
        itemRepository.delete(item);
    }

    // ===== NEW =====
    // Get all "lost" items for the logged-in user
    public List<Item> getUserLostItems(String userId) {
        return itemRepository.findAll().stream()
                .filter(item -> "lost".equalsIgnoreCase(item.getType()))
                .filter(item -> userId.equals(item.getUserid()))
                .collect(Collectors.toList());
    }

    // Get all "found" items (for everyone)
    public List<Item> getFoundItems() {
        return itemRepository.findAll().stream()
                .filter(item -> "found".equalsIgnoreCase(item.getType()))
                .collect(Collectors.toList());
    }
}
