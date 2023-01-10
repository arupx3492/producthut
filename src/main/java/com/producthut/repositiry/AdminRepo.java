package com.producthut.repositiry;

import com.producthut.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer>{

	public Admin findByAdminUsername(String userName);

}
