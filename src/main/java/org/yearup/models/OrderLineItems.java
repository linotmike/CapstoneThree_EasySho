package org.yearup.models;

import java.math.BigDecimal;

public class OrderLineItems {
    private int orderLineItems;
    private int orderId;
    private int productId;
    private BigDecimal sales;
    private int quantity;
    private BigDecimal discount;
    private String productName;

    public OrderLineItems(){}

    public OrderLineItems(int orderLineItems, int orderId, int productId, BigDecimal sales, int quantity, BigDecimal discount) {
        this.orderLineItems = orderLineItems;
        this.orderId = orderId;
        this.productId = productId;
        this.sales = sales;
        this.quantity = quantity;
        this.discount = discount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(int orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public BigDecimal getSales() {
        return sales;
    }

    public void setSales(BigDecimal sales) {
        this.sales = sales;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
