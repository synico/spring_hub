package com.ge.hc.healthlink.repository;

import com.ge.hc.healthlink.entity.ElectricityMessage;
import org.springframework.data.repository.CrudRepository;

public interface ElectricityMessageRepository extends CrudRepository<ElectricityMessage, String> {
}
