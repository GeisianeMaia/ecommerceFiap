package com.fiap.ecommerce.managementItem.repository;

import com.fiap.ecommerce.managementItem.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
