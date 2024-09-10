package com.fiap.ecommerce.managementItem.controller;

import com.fiap.ecommerce.managementItem.dto.ItemDTO;
import com.fiap.ecommerce.managementItem.service.ItemService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ItemDTO> getListItems() {
        return this.itemService.getListItems();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ItemDTO getItemId(@PathVariable Long id) {
        return this.itemService.getItemId(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO itemDTO){
        ItemDTO saveItem = itemService.createItem(itemDTO);
        return new ResponseEntity<>(saveItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable String id, @RequestBody ItemDTO itemDTO){
        ItemDTO updateItem = itemService.updateItem(id, itemDTO);
        return ResponseEntity.ok(updateItem);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteItem(@PathVariable String id){
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

}
