package com.producthut.repositiry;

import java.util.List;

import com.producthut.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AddressRepo extends JpaRepository<Address, Integer> {

	public List<Address> findByAddressType(String addressType);

	public List<Address> findByDistrict(String district);

	public List<Address> findByLandmark(String landmark);

	public List<Address> findByPincode(String pincode);

	public List<Address> findByState(String state);

}
