package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    ShoppingCart addToCart (int userId, int productId);
    ShoppingCart deleteCart(int userId);
    ShoppingCart updateCart(int userId, int productId,int quantity);
    boolean productExists(int userId, int productId);

}
