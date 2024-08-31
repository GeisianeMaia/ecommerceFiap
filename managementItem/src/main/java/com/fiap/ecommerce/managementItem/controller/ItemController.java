package com.fiap.ecommerce.managementItem.controller;

import com.fiap.ecommerce.managementItem.dto.ItemDTO;
import com.fiap.ecommerce.managementItem.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<ItemDTO> getListItems() {
        return this.itemService.getListItems();
    }

    @GetMapping("/{id}")
    public ItemDTO getItemId(@PathVariable String id) {
        return this.itemService.getItemId(id);
    }

    @PostMapping
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO itemDTO){
        ItemDTO saveItem = itemService.createItem(itemDTO);
        return new ResponseEntity<>(saveItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable String id, @RequestBody ItemDTO itemDTO){
        ItemDTO updateItem = itemService.updateItem(id, itemDTO);
        return ResponseEntity.ok(updateItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable String id){
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

}
