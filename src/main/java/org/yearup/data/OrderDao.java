package org.yearup.data;

import org.yearup.models.Order;
import org.yearup.models.ShoppingCart;

public interface OrderDao {
Order createOrder(ShoppingCart shoppingCart);
}
