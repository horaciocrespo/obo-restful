package com.obo.oborestfulapp.Sanity;

import com.obo.oborestfulapp.controllers.OrderController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SanityTest {

    @Autowired
    OrderController orderController;

    @Test
    void contextLoads() {
        Assertions.assertThat(orderController).isNotNull();
    }
}
