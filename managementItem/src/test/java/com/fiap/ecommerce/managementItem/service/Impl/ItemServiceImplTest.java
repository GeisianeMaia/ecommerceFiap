package com.fiap.ecommerce.managementItem.service.Impl;

import com.fiap.ecommerce.managementItem.dto.ItemDTO;
import com.fiap.ecommerce.managementItem.model.Item;
import com.fiap.ecommerce.managementItem.repository.ItemRepository;
import com.fiap.ecommerce.managementItem.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemServiceImplTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetListItems() {
        Item item = new Item(1L, "Item1", "Description", 100.0, true);
        when(itemRepository.findAll()).thenReturn(Collections.singletonList(item));

        ItemDTO itemDTO = new ItemDTO(1L, "Item1", "Description", 100.0, true);
        assertEquals(Collections.singletonList(itemDTO), itemService.getListItems());
    }

    @Test
    void testGetItemId() {
        Item item = new Item(1L, "Item1", "Description", 100.0, true);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ItemDTO itemDTO = new ItemDTO(1L, "Item1", "Description", 100.0, true);
        assertEquals(itemDTO, itemService.getItemId(1L));
    }

    @Test
    void testCreateItem() {
        ItemDTO itemDTO = new ItemDTO(1L, "Item1", "Description", 100.0, true);
        Item item = new Item(1L, "Item1", "Description", 100.0, true);
        when(itemRepository.save(item)).thenReturn(item);

        assertEquals(itemDTO, itemService.createItem(itemDTO));
    }

    @Test
    void testUpdateItem() {
        ItemDTO itemDTO = new ItemDTO(1L, "Item1", "Description", 100.0, true);
        Item item = new Item(1L, "Item1", "Description", 100.0, true);
        when(itemRepository.getReferenceById(1L)).thenReturn(item);
        when(itemRepository.save(item)).thenReturn(item);

        assertEquals(itemDTO, itemService.updateItem("1", itemDTO));
    }

    @Test
    void testUpdateItemNotFound() {
        ItemDTO itemDTO = new ItemDTO(1L, "Item1", "Description", 100.0, true);
        when(itemRepository.getReferenceById(1L)).thenThrow(new EntityNotFoundException());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            itemService.updateItem("1", itemDTO);
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
    }

    @Test
    void testDeleteItem() {
        doNothing().when(itemRepository).deleteById(1L);

        itemService.deleteItem("1");

        verify(itemRepository, times(1)).deleteById(1L);
    }
}
