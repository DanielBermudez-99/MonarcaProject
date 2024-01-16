package com.monarca.backendmonarca.domain.order;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Orders, Long> {

    List<Orders> findByUserId(Long userId);
}
