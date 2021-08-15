package com.go.ip.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.go.ip.model.IpAddress;

@Repository
public interface IpAddressRepository extends CrudRepository<IpAddress, Long>{

}

