package com.ge.hc.healthlink.repository;

import com.ge.hc.healthlink.entity.LinkMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LinkMessageRepository extends CrudRepository<LinkMessage, String> {

    @Query(value = "select * from hl_link_message where asset_mac = ?1 order by event_date desc limit 1", nativeQuery = true)
    LinkMessage findApMACByAssetMAC(String assetMAC);
}
