package com.thuvien.btl.Feedback;

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

import com.thuvien.btl.User.User;

public class FeedbackDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/jdbc_thuvien";
	private String jdbcUsername = "root";
	private String jdbcPassword = "Dang30032002.";
	private static final String select_fb_by_idbook = "SELECT f.idfb, f.iduser, f.idbook, f.comment, f.star, u.name " +
	"FROM jdbc_thuvien.feedback f join jdbc_thuvien.user u on f.iduser=u.iduser where idbook=?;";
	private static final String insert_fb_sql = "INSERT INTO `jdbc_thuvien`.`feedback` (`idbook`, `iduser`, `comment`, `star`) VALUES (?, ?, ?, ?);";
	private static final String update_fb_sql = "UPDATE `jdbc_thuvien`.`feedback` SET `comment` = ?, `star` = ? WHERE (`iduser` = ?);";
	
	public FeedbackDAO() {
	
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
	
	
	public List<Feedback> selectFbsByIdbook(@PathVariable String idbook) {
		List<Feedback> fbs = new ArrayList<>();
		try(Connection con = getConnection()
				)
		{
			PreparedStatement ps = con.prepareStatement(select_fb_by_idbook);
			ps.setString(1, idbook);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int idfb = rs.getInt("idfb");
				int iduser = rs.getInt("iduser");
				int idbook1 = rs.getInt("idbook");
				String name = rs.getString("name");
				String comment = rs.getString("comment");
				int star = rs.getInt("star");
				fbs.add(new Feedback(idfb,idbook1,iduser,name,comment,star));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return fbs;
	}
	
	
	public ResponseEntity<String> insertFb(@RequestBody Feedback fb) throws SQLException {
		try(Connection con = getConnection()) {
			List<Integer> idusers = new ArrayList<>();
			PreparedStatement p = con.prepareStatement(select_fb_by_idbook);
			p.setInt(1, fb.getIdbook());
			ResultSet rs = p.executeQuery();
			while(rs.next()) {
				idusers.add(rs.getInt("iduser"));
			}
			if(idusers.contains(fb.getIduser())) {
				PreparedStatement ps1 = con.prepareStatement(update_fb_sql);
				ps1.setString(1, fb.getComment());
				ps1.setInt(2, fb.getStar());
				ps1.setInt(3, fb.getIduser());
				ps1.executeUpdate();
				ps1.close();	
				return ResponseEntity.ok("Feedback added successfully");
			}
			
			PreparedStatement ps = con.prepareStatement(insert_fb_sql);
			ps.setInt(1, fb.getIdbook());
			ps.setInt(2, fb.getIduser());
			if(fb.getComment()==null) 
				ps.setString(3, "N/A");
			else
				ps.setString(3, fb.getComment());
			ps.setInt(4, fb.getStar());
			ps.executeUpdate();
			ps.close();	
			return ResponseEntity.ok("Feedback added successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding feedback");
	    }
	}
}
