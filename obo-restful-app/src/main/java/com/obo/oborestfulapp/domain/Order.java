package com.obo.oborestfulapp.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ShoppingOrder")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String carrier;
    private String trackingNumber;
    private OrderStatus orderStatus;

    // --- Relationships ---

    // this class is the owner
    @OneToOne(cascade = CascadeType.ALL)
    private Product product;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private Set<Comment> comments;

    public Long getId() {
        return id;
    }

    // TODO is it correct to have a setter for this?
    public void setId(Long id) {
        this.id = id;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        carrier = carrier;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public OrderStatus getStatus() {
        return orderStatus;
    }

    public void setStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
