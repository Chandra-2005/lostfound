package com.Lostfound.lostfound.Controller.DatabaseController;

import com.Lostfound.lostfound.Model.Item;
import com.Lostfound.lostfound.Repository.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
public class ItemController {

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // ➕ Add item
    @PostMapping("/add")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        item.init(); // set ID and createdAt
        Item savedItem = itemRepository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }

    // 📋 Get all items
    @GetMapping ("/show")
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemRepository.findAll());
    }

    // 🔍 Get item by ID
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable String id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id " + id));
        return ResponseEntity.ok(item);
    }

    // ❌ Delete item by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable String id) {
        itemRepository.deleteById(id);
        return ResponseEntity.ok("Item deleted successfully");
    }
}
