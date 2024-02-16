package org.online.store;

import org.online.store.models.Cart;
import org.online.store.models.Orderr;
import org.online.store.models.Userr;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Start {
    public static void main(String[] args) {
        SpringApplication.run(Start.class,args);
        Userr user = new Userr();
        if(user!= null){
            System.out.println("User not Null!");
        }
        Cart cart = user.getCart();
        if(cart!= null){
            System.out.println("Cart not NULL!");
        }
        Orderr order = cart.getOrder();
        if(order != null){
            System.out.println("Order ==== null");
        }
    }
}