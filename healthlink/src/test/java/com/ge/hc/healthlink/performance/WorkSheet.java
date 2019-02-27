package com.ge.hc.healthlink.performance;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class WorkSheet {

    @Test
    public void testLeftPad() {
        for(int i = 1; i < 200; i++) {
            System.out.println(StringUtils.leftPad(Integer.toString(i), 3, "0"));
        }
    }

}
