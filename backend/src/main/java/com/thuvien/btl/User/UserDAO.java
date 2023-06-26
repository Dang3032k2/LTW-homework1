package com.thuvien.btl.User;

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
import org.springframework.web.bind.annotation.RequestParam;



public class UserDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/jdbc_thuvien";
	private String jdbcUsername = "root";
	private String jdbcPassword = "Dang30032002.";
	private static final String select_all_users = "select * from user";
	private static final String select_user_by_id = "select * from jdbc_thuvien.user where iduser=?";
	private static final String insert_users_sql = "INSERT INTO `jdbc_thuvien`.`user` (`name`, `email`, `password`) VALUES (?, ?, ?);";
	private static final String update_users_sql = "UPDATE `jdbc_thuvien`.`user` SET `name` = ?, `email` = ?,"
			+ "`password` = ? WHERE (`iduser` = ?);";
	private static final String update_pw_sql = "UPDATE `jdbc_thuvien`.`user` SET `password` = ? WHERE (`iduser` = ?);";
	private static final String delete_user_sql = "DELETE FROM `jdbc_thuvien`.`user` WHERE (`iduser` = ?);";
	
	
	public UserDAO() {
		
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
	
	
	public List<User> selectAllUsers() {
		List<User> users = new ArrayList<>();
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(select_all_users);)
		{
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int iduser = rs.getInt("iduser");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				int admin = rs.getInt("admin");
				users.add(new User(iduser,name,email,password,admin!=0?true:false));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public User selectUserById(@PathVariable String iduser) throws SQLException {
		User user =new User();
		try(Connection con = getConnection()) {
			PreparedStatement ps=con.prepareStatement(select_user_by_id);
			ResultSet rs = null;
			ps.setInt(1, Integer.valueOf(iduser));
			rs=ps.executeQuery();
			while(rs.next()) {
				user.setIduser(rs.getInt("iduser"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setAdmin(rs.getInt("admin")!=0 ? true:false);
			}
		}
		catch (Exception e) {
				e.printStackTrace();
		}
		return user;
	}
	
	public ResponseEntity<String> insertUser(@RequestBody User user) throws SQLException {
		try(Connection con = getConnection()) {
			if(user.getName().equals("")) {
                 throw new Exception("Name is required!");
            }
			List<String> names = new ArrayList<>();
			PreparedStatement p = con.prepareStatement(select_all_users);
			ResultSet rs = p.executeQuery();
			while(rs.next()) {
				String name = rs.getString("name");
				names.add(name.toLowerCase());
			}
			if(names.contains(user.getName().toLowerCase())) {
				throw new Exception("Username is already exist!");
			}
			
			PreparedStatement ps = con.prepareStatement(insert_users_sql);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.executeUpdate();
			ps.close();	
			return ResponseEntity.ok("User updated successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating user");
	    }
	}
	
	public void updateUser(@RequestBody User user, @PathVariable String iduser) throws SQLException {
		try(Connection con = getConnection()) {
			PreparedStatement ps = con.prepareStatement(update_users_sql);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setInt(4, Integer.valueOf(iduser));
			ps.executeUpdate();
			ps.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ResponseEntity<String> updatePassword(@RequestBody User user) throws SQLException {
		try(Connection con = getConnection()) {
			String pw = "";
			PreparedStatement ps1 = con.prepareStatement("select password from user where iduser=?");
			ps1.setInt(1, user.getIduser());
			ResultSet rs = ps1.executeQuery();
			while(rs.next()) {
				pw = rs.getString("password");
			}
			if(pw.equals(user.getPassword())) {
				PreparedStatement ps = con.prepareStatement(update_pw_sql);
				ps.setString(1, user.getNewpw());
				ps.setInt(2, user.getIduser());
				ps.executeUpdate();
				ps.close();
				return ResponseEntity.ok("Password updated successfully");
			}
			else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai mật khẩu");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating password!");
		}
	}
	
	public ResponseEntity<String> deleteUser(@PathVariable String iduser) throws SQLException {
		try(Connection con = getConnection()) {
			PreparedStatement ps1 = con.prepareStatement("delete from cart where iduser=?");
			ps1.setString(1, iduser);
			ps1.executeUpdate();
			PreparedStatement ps2 = con.prepareStatement("delete from feedback where iduser=?");
			ps2.setString(1, iduser);
			ps2.executeUpdate();
			PreparedStatement ps3 = con.prepareStatement("delete from `order` where iduser=?");
			ps3.setString(1, iduser);
			ps3.executeUpdate();
			PreparedStatement ps = con.prepareStatement(delete_user_sql);
			ps.setString(1, iduser);
			ps.executeUpdate();
			ps.close();
			return ResponseEntity.ok("User deleted successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting user!");
		}
	}
	
//	public void deleteUser(@PathVariable String iduser) throws SQLException {
//		try(Connection con = getConnection()) {
//			PreparedStatement ps = con.prepareStatement(delete_user_sql);
//			ps.setString(1, iduser);
//			ps.executeUpdate();
//			ps.close();
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
