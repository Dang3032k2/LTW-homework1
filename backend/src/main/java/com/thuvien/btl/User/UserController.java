package com.thuvien.btl.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class UserController {
	private UserDAO userDAO = new UserDAO();
	
	@GetMapping("/users")
	public List<User> getUsers() {
		List<User> users= userDAO.selectAllUsers();
		return users;
	}
	
	
	@GetMapping("/user/{iduser}")
	public User getuserById(@PathVariable String iduser) throws SQLException {
		User user = userDAO.selectUserById(iduser);
		return user;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<String> addUser(@RequestBody User user) throws SQLException {
		return userDAO.insertUser(user);
	}
	
	@PutMapping("/user/{iduser}")
	public void updateUser(@RequestBody User user, @PathVariable String iduser) throws SQLException {
		userDAO.updateUser(user, iduser);
	}
	
	@PostMapping("/resetpw")
	public ResponseEntity<String> updatePassword(@RequestBody User user) throws SQLException {
		return userDAO.updatePassword(user);
	}
	
	@DeleteMapping("/user/{iduser}")
	public ResponseEntity<String> deleteUser(@PathVariable String iduser) throws SQLException {
		return userDAO.deleteUser(iduser);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> checkUser(@RequestBody User user) throws Exception {
		List<User> users = userDAO.selectAllUsers();
		List<String> names = new ArrayList<>();
		List<String> pws = new ArrayList<>();
		for(int i=0; i<users.size(); i++) {
			names.add(users.get(i).getName().toLowerCase());
			pws.add(users.get(i).getPassword());
		}
		if(names.contains(user.getName().toLowerCase())) {
			int index = names.indexOf(user.getName().toLowerCase());
			if(user.getPassword().equals(pws.get(index)))
				{
					User loggedInUser = users.get(index);
					return ResponseEntity.ok(loggedInUser);
				}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai tài khoản hoặc mật khẩu");
	}
}
