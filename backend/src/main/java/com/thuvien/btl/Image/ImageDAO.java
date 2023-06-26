package com.thuvien.btl.Image;

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


public class ImageDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/jdbc_thuvien";
	private String jdbcUsername = "root";
	private String jdbcPassword = "Dang30032002.";
	private static final String select_image_by_idbook = "select * from jdbc_thuvien.image where idbook=?";
	private static final String select_first_image_by_idbook = "select imageurl from jdbc_thuvien.image where idbook=? limit 1";
	private static final String insert_image_sql = "INSERT INTO `jdbc_thuvien`.`image` (`idbook`, `imageurl`) VALUES (?, ?);";
	private static final String update_image_sql = "UPDATE `jdbc_thuvien`.`image` SET `imageurl` = ? WHERE (`idimage` = ?);";
	private static final String delete_image_sql = "DELETE FROM `jdbc_thuvien`.`image` WHERE (`idimage` = ?);";
	private static final String delete_image_by_idbook = "DELETE FROM `jdbc_thuvien`.`image` WHERE (`idbook` = ?);";
	public ImageDAO() {
		
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
	
	
	public List<Image> selectImagesByIdbook(@PathVariable String idb) {
		List<Image> images = new ArrayList<>();
		try(Connection con = getConnection()) {
			PreparedStatement ps=con.prepareStatement(select_image_by_idbook);
			ps.setInt(1, Integer.valueOf(idb));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int idimage = rs.getInt("idimage");
				int idbook = rs.getInt("idbook");
				String imageurl = rs.getString("imageurl");
				images.add(new Image(idimage,idbook, imageurl));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return images;
	}
	public String selectFirstImage(String idbook) {
		try(Connection con = getConnection()) {
			PreparedStatement ps=con.prepareStatement(select_first_image_by_idbook);
			String imageurl = "";
			ps.setInt(1, Integer.valueOf(idbook));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				imageurl = rs.getString("imageurl");			
			}
			return imageurl;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return "Error";
		}
	}
	public ResponseEntity<String> insertImages(@RequestBody List<Image> images, @PathVariable String idbook) throws SQLException {
		try(Connection con = getConnection()) {	
			
			if(images.size()==0) {
				PreparedStatement ps = con.prepareStatement(insert_image_sql);
				ps.setInt(1, images.get(0).getIdbook());
				ps.setString(2, "https://firebasestorage.googleapis.com/v0/b/bookstore-5336d.appspot.com/o/safe_image.png?alt=media&token=207ed5e0-f543-419a-a38c-44427cdb8953");
				ps.executeUpdate();
				ps.close();	
			}
			else {
				for(Image image : images) {
					PreparedStatement ps = con.prepareStatement(insert_image_sql);
					ps.setInt(1, image.getIdbook());
					ps.setString(2, image.getImageurl());
					ps.executeUpdate();
					ps.close();	
				}
			}
			return ResponseEntity.ok("Imagas added successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding images");
	    }
	}
	
	public ResponseEntity<String> updateImages(@RequestBody List<Image> images) throws SQLException {
		try(Connection con = getConnection()) {
			for(Image image : images) {
				PreparedStatement ps = con.prepareStatement(update_image_sql);
				ps.setString(1, image.getImageurl());
				ps.setInt(2, image.getIdimage());
				ps.executeUpdate();
				ps.close();
			}
			return ResponseEntity.ok("Imagas updated successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating images");
		}
	}
	
	public ResponseEntity<String> deleteImage(@RequestBody List<Image> images) throws SQLException {
		try(Connection con = getConnection()) {
			for(Image image:images) {
				PreparedStatement ps = con.prepareStatement(delete_image_sql);
				ps.setInt(1, image.getIdimage());
				ps.executeUpdate();
				ps.close();
			}	
			return ResponseEntity.ok("Imagas deleted successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting images");
		}
	}
	public ResponseEntity<String> deleteImageByIdBook(@PathVariable String idbook) throws SQLException {
		try(Connection con = getConnection()) {
			PreparedStatement ps = con.prepareStatement(delete_image_by_idbook);
			ps.setInt(1, Integer.parseInt(idbook));
			ps.executeUpdate();
			ps.close();
			return ResponseEntity.ok("Imagas deleted successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting images");
		}
	}
}
