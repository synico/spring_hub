package com.ge.hc.healthlink.entity;

import com.ge.hc.healthlink.repository.DeviceCategoryRepository;
import com.ge.hc.healthlink.repository.DeviceRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@ActiveProfiles("local")
//@ComponentScan(basePackages = "com.ge.hc.healthlink.repository")
public class DeviceTests {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceCategoryRepository categoryRepository;

    @BeforeClass
    public static void setupBeforeClass() throws Exception {
        System.out.println("Starting...");
    }

    public static void tearDownAfterClass() throws Exception {
        System.out.println("Ending...");
    }

//    @Test
    public void saveDeviceCategory() {
        DeviceCategory category = new DeviceCategory();
        category.setName("test_category");
        category.setPowerOffElectricCurrentStart(0.001d);
        category.setPowerOffElectricCurrentEnd(0.005d);
        category.setPowerOnElectricCurrentStart(0.006d);
        category.setPowerOnElectricCurrentEnd(10d);
        category.setStandByElectricCurrentStart(0.01d);
        category.setStandByElectricCurrentEnd(0.1d);
        category.setInUseElectricCurrentStart(0.4d);
        category.setInUseElectricCurrentEnd(0.7d);
        categoryRepository.save(category);
    }

//    @Test
    public void saveDevice() {
        Device d1 = new Device();
        d1.setDeviceCategoryUuid("d0575a2e-695a-446f-ab22-bc8ca9f36c89");
        d1.setIotId("ECFABC216430");
        d1.setCreatedTime(LocalDateTime.now());
        d1.setName("device1");
        deviceRepository.save(d1);
    }

    @Test
    public void findDeviceByMAC() {
        Device device = deviceRepository.findByIotId("ECFABC216430");
        if(device != null) {
            System.out.println("category uid: " + device.getDeviceCategoryUuid());
            if(device.getDeviceCategoryUuid() != null) {
                Optional<DeviceCategory> category = categoryRepository.findById(device.getDeviceCategoryUuid());
                if(category.isPresent()) {
                    DeviceCategory dc = category.get();
                    System.out.println(dc.getInUseElectricCurrentStart());
                }
            }
        }
    }

}
