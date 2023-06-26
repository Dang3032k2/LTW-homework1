package com.thuvien.btl.Cart;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class CartController {
	private CartDAO cartDAO = new CartDAO();
	
	@GetMapping("/cart/{iduser}")
	public List<Cart> getCartByIdUser(@PathVariable String iduser) {
		return cartDAO.selectCartByIdUser(iduser);
	}
	
	@PostMapping("/cart")
	public ResponseEntity<String> addBookToCart(@RequestBody Cart cart) throws SQLException {
		return cartDAO.insertBookToCart(cart);
	}
	
	@PutMapping("/cart")
	public ResponseEntity<String> updateCart(@RequestBody Cart cart) throws SQLException {
		return cartDAO.updateCart(cart);
	}
	
	@DeleteMapping("/cart/{idcart}")
	public ResponseEntity<String> deleteBookFromCart(@PathVariable String idcart) throws SQLException {
		return cartDAO.deleteBookFromCart(idcart);
	}
	
	@DeleteMapping("/carts")
	public ResponseEntity<String> deleteBooksFromCart(@RequestBody List<String> idcarts) throws SQLException {
		return cartDAO.deleteBooksFromCart(idcarts);
	}
}
