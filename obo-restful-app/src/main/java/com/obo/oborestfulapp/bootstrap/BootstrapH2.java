package com.obo.oborestfulapp.bootstrap;

import com.obo.oborestfulapp.domain.Order;
import com.obo.oborestfulapp.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Profile("dev")
@Slf4j
public class BootstrapH2 implements ApplicationListener<ContextRefreshedEvent> {

    private final OrderRepository orderRepository;

    public BootstrapH2(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        orderRepository.saveAll(getCategories());
//        orderRepository.saveAll(getUsers());
//        log.debug("Loading Bootstrap Data");
    }

//    private List<Order> getOrders() {
//        User user1 = User
//                .builder()
//                .username("hcrespo")
//                .name("Horacio")
//                .lastname("Crespo")
//                .build();
//
//        User user2 = User
//                .builder()
//                .username("ltorvalds")
//                .name("Linus")
//                .lastname("Torvals")
//                .build();
//
//        return Arrays.asList(user1, user2);
//    }

//    private List<ProductCategory> getCategories() {
//        List<ProductCategory> categories = new ArrayList<>();
//
//        ProductCategory productCategory1 = new ProductCategory();
//        productCategory1.setName("Electronics");
//        productCategory1.setDescription("Electronics");
//        categories.add(productCategory1);
//
//        ProductCategory productCategory2 = new ProductCategory();
//        productCategory2.setName("Books");
//        productCategory2.setDescription("Books");
//        categories.add(productCategory2);
//
//        return categories;
//    }
}
