package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.data.OrderDao;
import org.yearup.data.ShoppingCartDao;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/orders")
public class OrdersController {
private OrderDao orderDao;
private ShoppingCartDao shoppingCartDao;

@Autowired
    public OrdersController(OrderDao orderDao, ShoppingCartDao shoppingCart){
    this.orderDao = orderDao;
    this.shoppingCartDao = shoppingCart;
}

}
