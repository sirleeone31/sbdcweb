package com.sbdc.sbdcweb;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SbdcwebApplicationTests {

    @Before
    public void setup() {
        RestAssured.port=8070;
    }
}
