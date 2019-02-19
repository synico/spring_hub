package com.ge.hc.healthlink.entity;

import com.ge.hc.healthlink.repository.ElectricityHeartbeatRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ElectricityHeartbeatTests {

    @Autowired
    private ElectricityHeartbeatRepository electricityHeartbeatRepository;

    @Test
    public void findHeartbeatByAssetMAC() {
        ElectricityHeartbeat heartbeat = electricityHeartbeatRepository.findLatestHeartbeatByAssetMAC("84F3EB77F175");
        System.out.println(heartbeat.getHeartbeatKey());
    }
}
