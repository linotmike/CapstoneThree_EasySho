package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.OrderDao;
import org.yearup.data.ProfileDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.Order;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/orders")
public class OrdersController {
    private OrderDao orderDao;
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProfileDao profileDao;

    @Autowired
    public OrdersController(OrderDao orderDao, ShoppingCartDao shoppingCart, UserDao userDao, ProfileDao profileDao) {
        this.orderDao = orderDao;
        this.shoppingCartDao = shoppingCart;
        this.userDao = userDao;
        this.profileDao = profileDao;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Order> createOrder(Principal principal) {
        String username = principal.getName();
        User user = userDao.getByUserName(username);
        int userId = user.getId();

        Profile profile = profileDao.getByUserId(userId);
        ShoppingCart shoppingCart = shoppingCartDao.getByUserId(userId);

        if (shoppingCart.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        Order order = new Order();
        order.setUserId(userId);
        order.setAddress(profile.getAddress());
        order.setCity(profile.getCity());
        order.setState(profile.getState());
        order.setZip(profile.getZip());
        order.setShipping_amount(BigDecimal.ZERO);

        Order newOrder = orderDao.createOrder(order,shoppingCart);
        shoppingCartDao.deleteCart(userId);
        return ResponseEntity.ok(newOrder);

    }
}


