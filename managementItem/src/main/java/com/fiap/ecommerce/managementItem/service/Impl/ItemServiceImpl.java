package com.fiap.ecommerce.managementItem.service.Impl;

import com.fiap.ecommerce.managementItem.dto.ItemDTO;
import com.fiap.ecommerce.managementItem.model.Item;
import com.fiap.ecommerce.managementItem.repository.ItemRepository;
import com.fiap.ecommerce.managementItem.service.ItemService;
import io.jsonwebtoken.Claims;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<ItemDTO> getListItems() {
        List<Item> items = this.itemRepository.findAll();
        return items.stream().map(this::toItemDTO).collect(Collectors.toList());
    }

    @Override
    public ItemDTO getItemId(Long id) {
        return toItemDTO(this.itemRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado")));
    }

    @Override
    public ItemDTO createItem(ItemDTO itemDTO) {
        Item item = toItem(itemDTO);
        item = itemRepository.save(item);
        System.out.println("item" + item);
        return toItemDTO(item);
    }

    @Override
    public ItemDTO updateItem(String id, ItemDTO itemDTO) {
        try {
            Item item = itemRepository.getReferenceById(Long.valueOf(id));
            item.setNameItem(String.valueOf(itemDTO.id()), itemDTO.nameItem(), itemDTO.description(), itemDTO.price(), itemDTO.hasStock());

            item = itemRepository.save(item);
            return toItemDTO(item);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado", e);

        }
    }

    @Override
    public void deleteItem(String id) {
        itemRepository.deleteById(Long.valueOf(id));
    }

    @Override
    public ItemDTO toItemDTO(Item item) {
        return new ItemDTO(item.getId(), item.getNameItem(), item.getDescription(), item.getPrice(), item.isHasStock());
    }

    @Override
    public Item toItem(ItemDTO itemDTO) {
        return new Item(itemDTO.id(), itemDTO.nameItem(), itemDTO.description(), itemDTO.price(), itemDTO.hasStock());
    }

}
