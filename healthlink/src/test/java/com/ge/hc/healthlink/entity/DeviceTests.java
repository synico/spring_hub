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

    @Test
    public void saveDeviceCategory() {
        DeviceCategory category = new DeviceCategory();
        category.setName("category_4_84F3EB77F175");
        category.setPowerOffElectricCurrentStart(20d);
        category.setPowerOffElectricCurrentEnd(-20d);
        category.setPowerOnElectricCurrentStart(30d);
        category.setPowerOnElectricCurrentEnd(30d);
        category.setStandByElectricCurrentStart(100d);
        category.setStandByElectricCurrentEnd(120d);
        category.setInUseElectricCurrentStart(130d);
        category.setInUseElectricCurrentEnd(400d);
        categoryRepository.save(category);
    }

    @Test
    public void saveDevice() {
        Device d1 = new Device();
        d1.setDeviceCategoryUuid("b77081e5-9f3c-4094-a3f3-00d2ea04df3d");
        d1.setIotId("84F3EB77F175");
        d1.setCreatedTime(LocalDateTime.now());
        d1.setName("device3");
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
