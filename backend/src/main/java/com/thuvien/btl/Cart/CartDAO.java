package com.thuvien.btl.Cart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.thuvien.btl.Feedback.Feedback;
import com.thuvien.btl.Image.ImageDAO;

public class CartDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/jdbc_thuvien";
	private String jdbcUsername = "root";
	private String jdbcPassword = "Dang30032002.";
	private ImageDAO imageDAO = new ImageDAO();
	private static final String select_cart_by_iduser = "SELECT c.*, b.title FROM jdbc_thuvien.cart c join jdbc_thuvien.book b on c.idbook=b.idbook where c.iduser=?;";
	private static final String insert_book_cart_sql = "INSERT INTO `jdbc_thuvien`.`cart` (`iduser`, `idbook`, `number`) VALUES (?, ?, ?);";
	private static final String update_cart_sql = "UPDATE `jdbc_thuvien`.`cart` set `number`=? where idcart=?;";
	private static final String update_book_cart_sql = "UPDATE `jdbc_thuvien`.`cart` set `number`=? where idbook=?;";
	private static final String delete_book_cart_sql = "DELETE FROM `jdbc_thuvien`.`cart` WHERE (`idcart` = ?);";
	
	public CartDAO() {
	
	}
	
	protected Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	
	public List<Cart> selectCartByIdUser(@PathVariable String idu) {
		List<Cart> cart = new ArrayList<>();
		try(Connection con = getConnection())
		{
			PreparedStatement ps = con.prepareStatement(select_cart_by_iduser);
			ps.setString(1, idu);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int idcart = rs.getInt("idcart");
				int iduser = rs.getInt("iduser");
				int idbook = rs.getInt("idbook");
				int number = rs.getInt("number");
				String title = rs.getString("title");
				String imageurl = imageDAO.selectFirstImage(String.valueOf(idbook));
				cart.add(new Cart(idcart,idbook,iduser,number,title,imageurl));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return cart;
	}
	
	
	public ResponseEntity<String> insertBookToCart(@RequestBody Cart cart) throws SQLException {
		try(Connection con = getConnection()) {
			List<Integer> idbooks = new ArrayList<>();
			List<Integer> numbers = new ArrayList<>();
			PreparedStatement p = con.prepareStatement(select_cart_by_iduser);
			p.setInt(1, cart.getIduser());
			ResultSet rs = p.executeQuery();
			while(rs.next()) {
				idbooks.add(rs.getInt("idbook"));
				numbers.add(rs.getInt("number"));
			}
			System.out.println(cart.getIdbook());
			System.out.println(cart.getNumber());
			for(int i:idbooks) System.out.println(i);
			for(int i:numbers) System.out.println(i);
			if(idbooks.contains(cart.getIdbook())) {
				int sl = numbers.get(idbooks.indexOf(cart.getIdbook()))+cart.getNumber();
				PreparedStatement p1 = con.prepareStatement(update_book_cart_sql);
				p1.setInt(1, sl);
				p1.setInt(2, cart.getIdbook());
				p1.executeUpdate();
			}
			else {
				PreparedStatement ps = con.prepareStatement(insert_book_cart_sql);
				ps.setInt(1, cart.getIduser());
				ps.setInt(2, cart.getIdbook());
				ps.setInt(3, cart.getNumber());
				ps.executeUpdate();
				ps.close();	
			}
			
			return ResponseEntity.ok("Book added successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding cart");
	    }
	}
	public ResponseEntity<String> updateCart(@RequestBody Cart cart) throws SQLException {
		try(Connection con = getConnection()) {
			PreparedStatement ps = con.prepareStatement(update_cart_sql);
			ps.setInt(1, cart.getNumber());
			ps.setInt(2, cart.getIdcart());
			ps.executeUpdate();
			ps.close();	
			return ResponseEntity.ok("Cart updated successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating cart");
	    }
	}
	public ResponseEntity<String> deleteBookFromCart(@PathVariable String idcart) throws SQLException {
		try(Connection con = getConnection()) {
			PreparedStatement ps = con.prepareStatement(delete_book_cart_sql);
			ps.setString(1, idcart);
			ps.executeUpdate();
			ps.close();	
			return ResponseEntity.ok("Book deleted successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting feedback");
	    }
	}
	public ResponseEntity<String> deleteBooksFromCart(@RequestBody List<String> idcarts) throws SQLException {
		try(Connection con = getConnection()) {
			for(String i:idcarts) {
				PreparedStatement ps = con.prepareStatement(delete_book_cart_sql);
				ps.setString(1, i);
				ps.executeUpdate();
			}
			
			return ResponseEntity.ok("Book deleted successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting book");
	    }
	}
}
