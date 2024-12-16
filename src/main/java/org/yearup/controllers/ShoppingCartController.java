package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("cart")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@CrossOrigin(origins = "*")
public class ShoppingCartController {
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    @GetMapping
    public ShoppingCart getCart(Principal principal) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            return shoppingCartDao.getByUserId(user.getId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }




@PostMapping("products/{productId}")
@ResponseStatus(value= HttpStatus.CREATED)
    public ResponseEntity<ShoppingCart>create (Principal principal, @PathVariable int productId){
        try{
            String username = principal.getName();
            User user = userDao.getByUserName(username);
            int userId = user.getId();

            ShoppingCart updatedCart= shoppingCartDao.addToCart(userId,productId);
//             Map<String, String> res = new HashMap<>();
//             res.put("message","Product added to the shopping cart");
             return ResponseEntity.ok(updatedCart);
        }catch(Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Oops... our bad.");
        }
}


    @PutMapping("products/{productId}")
    public ResponseEntity<ShoppingCart> updateCart (Principal principal, @PathVariable int productId, @RequestBody ShoppingCartItem shoppingCartItem){
        String username = principal.getName();
        User user = userDao.getByUserName(username);
        int userId = user.getId();
        boolean productExists = shoppingCartDao.productExists(userId,productId);
        if(!productExists){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found in cart");
        }

        ShoppingCart updateCart = shoppingCartDao.updateCart(userId,productId, shoppingCartItem.getQuantity());

//        Map<String,String>res = new HashMap<>();
//        res.put("Message" ,"Product updated ");
        return ResponseEntity.ok(updateCart);
    }


@DeleteMapping
    public ResponseEntity<ShoppingCart> deleteCart (Principal principal){
        String username = principal.getName();
        User user = userDao.getByUserName(username);
        int userId = user.getId();

        ShoppingCart deleteCart =shoppingCartDao.deleteCart(userId);
//        Map<String,String> res = new HashMap<>();
//        res.put("message","Shopping cart Deleted");
        return ResponseEntity.ok(deleteCart);
}
}
