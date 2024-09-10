package com.fiap.ecommerce.managementItem.service;

import com.fiap.ecommerce.managementItem.dto.ItemDTO;
import com.fiap.ecommerce.managementItem.model.Item;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemService {
    public List<ItemDTO> getListItems();

    public ItemDTO getItemId(Long id);

    public ItemDTO createItem(ItemDTO itemDTO);

    public ItemDTO updateItem (String id, ItemDTO itemDTO);

    public void deleteItem(String id);

    public ItemDTO toItemDTO(Item item);

    public Item toItem(ItemDTO itemDTO);

}
