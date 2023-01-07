package com.producthut.repository;

import com.producthut.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardDao extends JpaRepository<Card,Integer> {
}
