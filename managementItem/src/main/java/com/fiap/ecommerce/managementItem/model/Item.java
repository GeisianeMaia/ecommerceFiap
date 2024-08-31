package com.fiap.ecommerce.managementItem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "itens")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String nameItem;
    private double price;
    private String description;
    private boolean hasStock;

    public Item(String id, String nameItem, String description, double price, boolean hasStock) {
        this.id = id;
        this.nameItem = nameItem;
        this.description = description;
        this.price = price;
        this.hasStock = hasStock;
    }

    public void setNameItem(String s, String s1, String description, double price, boolean b) {
    }
}
