package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    void addToCart (int userId, int productId);
    void deleteCart(int userId);
    void updateCart(int userId, int productId,int quantity);
    boolean productExists(int userId, int productId);

}
