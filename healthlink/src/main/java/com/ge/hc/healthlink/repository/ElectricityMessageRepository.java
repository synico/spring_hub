package com.ge.hc.healthlink.repository;

import com.ge.hc.healthlink.entity.ElectricityMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ElectricityMessageRepository extends CrudRepository<ElectricityMessage, String> {

    List<ElectricityMessage> findByEventDateBetween(Integer startDate, Integer endDate);

}
