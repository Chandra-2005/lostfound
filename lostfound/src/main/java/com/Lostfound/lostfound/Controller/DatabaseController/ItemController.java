package com.Lostfound.lostfound.Controller.DatabaseController;

import com.Lostfound.lostfound.Controller.Utils.JwtUtil;
import com.Lostfound.lostfound.Model.Item;
import com.Lostfound.lostfound.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;
    private final JwtUtil jwtUtil;

    public ItemController(ItemService itemService, JwtUtil jwtUtil) {
        this.itemService = itemService;
        this.jwtUtil = jwtUtil;
    }

    // ===== Get all lost items for logged-in user =====
    @GetMapping("/lost")
    public List<Item> getMyLostItems(@RequestHeader("Authorization") String token) {
        String userId = jwtUtil.getuserid(token);
        return itemService.getUserLostItems(userId);
    }

    // ===== Get all found items (for everyone) =====
    @GetMapping("/found")
    public List<Item> getAllFoundItems() {
        return itemService.getFoundItems();
    }
}
