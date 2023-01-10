package com.producthut.repositiry;

import com.producthut.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CardRepo extends JpaRepository<Card, Integer>{
	
	
}
