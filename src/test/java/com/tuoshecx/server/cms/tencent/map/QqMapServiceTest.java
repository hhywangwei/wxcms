package com.tuoshecx.server.cms.tencent.map;

import com.tuoshecx.server.cms.tencent.configure.properties.TencentProperties;
import org.junit.Before;

public class QqMapServiceTest {
    private TencentProperties properties;

    @Before
    public void before(){
        properties = new TencentProperties();
        properties.setMapKey("L2XBZ-L7LWD-GAV46-H23SI-KAGJF-KRFUN");
    }

}
