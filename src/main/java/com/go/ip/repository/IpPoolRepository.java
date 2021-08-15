package com.go.ip.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.go.ip.model.IpPool;

@Repository
public interface IpPoolRepository extends CrudRepository<IpPool, Long>{

}
