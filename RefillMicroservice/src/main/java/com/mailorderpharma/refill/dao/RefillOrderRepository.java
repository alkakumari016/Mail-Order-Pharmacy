package com.mailorderpharma.refill.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mailorderpharma.refill.entity.RefillOrder;

/**JPA Repository which interacts with database*/
@Repository
public interface RefillOrderRepository extends JpaRepository<RefillOrder,Integer> {

}
