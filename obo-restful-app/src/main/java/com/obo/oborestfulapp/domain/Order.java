package com.obo.oborestfulapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
// Order is not a valid name
@Table(name = "ShoppingOrder")
// A shortcut for @ToString, @EqualsAndHashCode, @Getter on all fields, @Setter on all non-final fields,
// and @RequiredArgsConstructor!
@Data
@NoArgsConstructor // This is needed to support Jackson's serializers
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    private String name;
    private String description;

//    @NotNull
    private OrderStatus orderStatus;

    // this is hibernate specific
    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private LocalDateTime createDateTime;

    // this is hibernate specific
    @UpdateTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private LocalDateTime updateDateTime;

    @NotNull
    private int quantity;
    @NotNull
    private double total;

    private String carrier;

    @Column(unique = true)
    private String trackingNumber;

    @Temporal(TemporalType.DATE)
    private Date deliveryDate;

    @NotNull
    private String productName;

    @NotNull
    private String billingAddress;

    @NotNull
    private String shippingAddress;

//    private String creditCardInfo;

    // --- Relationships ---

    // this class is the owner
//    @OneToOne(cascade = CascadeType.ALL)
//    private Product product;

    /**
     * To break the recursive, bi-directional interface, don't serialize {@literal comments}.
     */
//    @JsonIgnore
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
//    private Set<Comment> comments;

//    @PrePersist
//    protected void onCreate() {
//
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//
//    }

}
