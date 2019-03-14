package com.nick.repository;

import com.nick.entity.OrgInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author nick.liu
 * @create 2019/3/14 15:00
 */
public interface OrgInfoRepository extends JpaRepository<OrgInfo, String> {
}
