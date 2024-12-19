package org.yearup.data;

import org.yearup.models.Order;
import org.yearup.models.OrderLineItems;
import org.yearup.models.ShoppingCart;

import java.util.List;

public interface OrderDao {
Order createOrder(Order order,ShoppingCart shoppingCart);
List<OrderLineItems> getOrderLineItems(int orderId);
}
