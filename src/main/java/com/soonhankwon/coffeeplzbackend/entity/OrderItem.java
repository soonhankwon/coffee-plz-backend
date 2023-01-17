package com.soonhankwon.coffeeplzbackend.entity;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @Column(name = "oreder_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_option_id")
    private ItemOption itemOption;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "item_category", nullable = false)
    private String itemCategory;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_price", nullable = false)
    private Long itemPrice;

    @Column(name = "item_size", nullable = false)
    private Long item_size;

    @Column(name = "item_quantity", nullable = false)
    private Integer item_quantity;
}
