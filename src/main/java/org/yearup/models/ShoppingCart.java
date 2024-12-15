package org.yearup.models;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Integer, ShoppingCartItem> items = new HashMap<>();

    public Map<Integer, ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, ShoppingCartItem> items) {
        this.items = items;
    }

    public boolean contains(int productId) {
        return items.containsKey(productId);
    }

    public void add(ShoppingCartItem item) {
        int productId = item.getProductId();
        if(items.containsKey(productId)){
            ShoppingCartItem shoppingCartItem = items.get(productId);
            shoppingCartItem.setQuantity(shoppingCartItem.getQuantity() + item.getQuantity());
        }
        items.put(item.getProductId(), item);
    }

    public void updateQuantity (int productId, int quantity){
        if(items.containsKey(productId)){
            items.get(productId).setQuantity(quantity);
        }
    }

    public ShoppingCartItem get(int productId) {
        return items.get(productId);
    }

    public BigDecimal getTotal() {
        BigDecimal total = items.values()
                .stream()
                .map(i -> i.getLineTotal())
                .reduce(BigDecimal.ZERO, (lineTotal, subTotal) -> subTotal.add(lineTotal));

        return total;
    }

}
