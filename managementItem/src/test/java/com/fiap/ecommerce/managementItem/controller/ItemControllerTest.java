package com.fiap.ecommerce.managementItem.controller;

import com.fiap.ecommerce.managementItem.dto.ItemDTO;
import com.fiap.ecommerce.managementItem.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetListItems() throws Exception {
        ItemDTO itemDTO = new ItemDTO(1L, "Item1", "Description", 100.0, true);
        when(itemService.getListItems()).thenReturn(Collections.singletonList(itemDTO));

        mockMvc.perform(get("/item")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nameItem").value("Item1"))
                .andExpect(jsonPath("$[0].description").value("Description"))
                .andExpect(jsonPath("$[0].price").value(100.0))
                .andExpect(jsonPath("$[0].hasStock").value(true));
    }

    @Test
    void testGetItemId() throws Exception {
        ItemDTO itemDTO = new ItemDTO(1L, "Item1", "Description", 100.0, true);
        when(itemService.getItemId(anyLong())).thenReturn(itemDTO);

        mockMvc.perform(get("/item/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nameItem").value("Item1"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.hasStock").value(true));
    }

    @Test
    void testCreateItem() throws Exception {
        ItemDTO itemDTO = new ItemDTO(1L, "Item1", "Description", 100.0, true);
        when(itemService.createItem(any(ItemDTO.class))).thenReturn(itemDTO);

        mockMvc.perform(post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nameItem").value("Item1"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.hasStock").value(true));
    }

    @Test
    void testUpdateItem() throws Exception {
        ItemDTO itemDTO = new ItemDTO(1L, "Item1", "Description", 100.0, true);
        when(itemService.updateItem(anyString(), any(ItemDTO.class))).thenReturn(itemDTO);

        mockMvc.perform(put("/item/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nameItem").value("Item1"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.hasStock").value(true));
    }

    @Test
    void testDeleteItem() throws Exception {
        doNothing().when(itemService).deleteItem(anyString());

        mockMvc.perform(delete("/item/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
