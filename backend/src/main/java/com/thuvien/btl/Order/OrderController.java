package com.thuvien.btl.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.thuvien.btl.Book.Book;
import com.thuvien.btl.Cart.Cart;
import com.thuvien.btl.Image.ImageDAO;

@RestController
@CrossOrigin
public class OrderController {
	private ImageDAO imageDAO = new ImageDAO();
	@GetMapping("/orders/{id_user}")
	public List<Order> getOrders(@PathVariable String id_user) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Order> orders = new ArrayList<Order>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_thuvien", "root", "Dang30032002.");
			ps = con.prepareStatement("SELECT o.*, b.title FROM jdbc_thuvien.order "+
			"o join jdbc_thuvien.book b on o.idbook=b.idbook where o.iduser=?;");
			ps.setInt(1, Integer.parseInt(id_user));
			rs = ps.executeQuery();
			while(rs.next()) {
				int idorder = rs.getInt("idorder");
				int idbook = rs.getInt("idbook");
				int iduser = rs.getInt("iduser");
				String title = rs.getString("title");
				int number = rs.getInt("number");
				int cancel = rs.getInt("cancel");
				String imageurl = imageDAO.selectFirstImage(String.valueOf(idbook));
				
				orders.add(new Order(idorder, idbook, iduser, title, number, cancel, imageurl));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}
	
	@PostMapping("/order")
	public ResponseEntity<String> addOrder(@RequestBody Order order) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_demo", "root", "Dang30032002.");
			ps = con.prepareStatement("INSERT INTO `jdbc_thuvien`.`order` (`idbook`, `iduser`, `number`, `cancel`) VALUES (?, ?, ?, ?);");
			ps.setInt(1, order.getIdbook());
			ps.setInt(2, order.getIduser());
			ps.setInt(3, order.getNumber());
			ps.setInt(4, order.getCancel());
			ps.executeUpdate();
			ps.close();
			con.close();
			return ResponseEntity.ok("Book added successfully");
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding book");
		}
	}
	@PostMapping("/orders")
	public ResponseEntity<String> addOrderFromCart(@RequestBody List<Cart> carts) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_demo", "root", "Dang30032002.");
			for(Cart cart:carts) {
				ps = con.prepareStatement("INSERT INTO `jdbc_thuvien`.`order` (`idbook`, `iduser`, `number`) VALUES (?, ?, ?);");
				ps.setInt(1, cart.getIdbook());
				ps.setInt(2, cart.getIduser());
				ps.setInt(3, cart.getNumber());
				ps.executeUpdate();
			}
			
			ps.close();
			con.close();
			return ResponseEntity.ok("Books added successfully");
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding books");
		}
	}
	@PutMapping("/order/{idorder}")
	public ResponseEntity<String> updateOrder(@PathVariable String idorder) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_demo", "root", "Dang30032002.");
			ps = con.prepareStatement("update `jdbc_thuvien`.`order` set `cancel`=1 where idorder=?;");
			ps.setInt(1, Integer.parseInt(idorder));
			ps.executeUpdate();
			ps.close();
			con.close();
			return ResponseEntity.ok("Order canceled successfully");
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error canceled book");
		}
	}
}
