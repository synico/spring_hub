package com.nick.repository;

import com.nick.entity.ElectricityMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectricityMessageRepository extends JpaRepository<ElectricityMessage, String> {
}
