package com.thuvien.btl.Book;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.thuvien.btl.Feedback.Feedback;
import com.thuvien.btl.Feedback.FeedbackDAO;
import com.thuvien.btl.Image.Image;
import com.thuvien.btl.Image.ImageDAO;
import com.thuvien.btl.Order.Order;



@RestController
@CrossOrigin
public class BookController {
	private ImageDAO imageDAO = new ImageDAO();
	
	@GetMapping("/books")
	public List<Book> getBooks() throws IOException{
		Connection con = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs, rs1 = null;
		List<Book> books = new ArrayList<Book>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_thuvien", "root", "Dang30032002.");
			ps=con.prepareStatement("select sum(number)as numbersold from jdbc_thuvien.order where idbook=? and cancel=0");
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM jdbc_thuvien.book b join jdbc_thuvien.category c on c.idcategory=b.category");
			while(rs.next()) {
				int idbook = rs.getInt("idbook");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String category = rs.getString("c.categoryname");
				Date issuedate = rs.getDate("issuedate");
				int numberpage = rs.getInt("numberpage");
				int idcategory = rs.getInt("c.idcategory");
				String description = rs.getString("description");
				ps.setInt(1, idbook);
				rs1 = ps.executeQuery();
				rs1.next();
				int numbersold = rs1.getInt("numbersold");
				List<Image> images = imageDAO.selectImagesByIdbook(String.valueOf(idbook)); 
				
				books.add(new Book(idbook, title, author, category, issuedate, numberpage, idcategory, numbersold, description, images));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	@GetMapping("/books/search")
	public List<Book> getBooksByKw(@RequestParam String keyword) throws IOException{
		Connection con = null;
		PreparedStatement ps, ps1 = null;
		ResultSet rs, rs1 = null;
		List<Book> books = new ArrayList<Book>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_thuvien", "root", "Dang30032002.");
			ps1=con.prepareStatement("select sum(number)as numbersold from jdbc_thuvien.order where idbook=? and cancel=0");
			
			if(keyword.equals(""))  
				ps = con.prepareStatement("SELECT * FROM jdbc_thuvien.book join jdbc_thuvien.category on idcategory=category");
			else {
				ps = con.prepareStatement("SELECT * FROM jdbc_thuvien.book join jdbc_thuvien.category on idcategory=category where title like ?");
				ps.setString(1, '%'+keyword+'%');
			}	
			rs=ps.executeQuery();
			while(rs.next()) {
				int idbook = rs.getInt("idbook");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String category = rs.getString("category.categoryName");
				Date issuedate = rs.getDate("issuedate");
				int numberpage = rs.getInt("numberpage");
				int idcategory = rs.getInt("idcategory");
				String description = rs.getString("description");
				List<Image> images = imageDAO.selectImagesByIdbook(String.valueOf(idbook));
				ps1.setInt(1, idbook);
				rs1 = ps1.executeQuery();
				rs1.next();
				int numbersold = rs1.getInt("numbersold");
				books.add(new Book(idbook, title, author, category, issuedate, numberpage, idcategory, numbersold, description, images));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	@GetMapping("/books/{idcategory}")
	public List<Book> getBooksByC(@PathVariable String idcategory) throws IOException{
		Connection con = null;
		PreparedStatement ps, ps1 = null;
		ResultSet rs, rs1 = null;
		List<Book> books = new ArrayList<Book>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_thuvien", "root", "Dang30032002.");
			ps = con.prepareStatement("SELECT * FROM jdbc_thuvien.book where category = ?");
			ps.setInt(1, Integer.parseInt(idcategory));	
			rs=ps.executeQuery();
			while(rs.next()) {
				int idbook = rs.getInt("idbook");
				String title = rs.getString("title");
				String author = rs.getString("author");
				List<Image> images = imageDAO.selectImagesByIdbook(String.valueOf(idbook)); 
				books.add(new Book(idbook, title, author, images));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	@GetMapping("/book/{idbook}")
	public Book getBook(@PathVariable int idbook) throws IOException{
		Connection con = null;
		PreparedStatement ps, ps1 = null;
		ResultSet rs, rs1 = null;
		Book book = new Book();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_thuvien", "root", "Dang30032002.");
			ps1=con.prepareStatement("select sum(number)as numbersold from jdbc_thuvien.order where idbook=? and cancel=0");
			ps = con.prepareStatement("SELECT * FROM jdbc_thuvien.book join jdbc_thuvien.category on idcategory=category where idbook=?");
			ps.setInt(1, idbook);
			rs = ps.executeQuery();
			while(rs.next()) {
				book.setIdbook(rs.getInt("idbook"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setCategory(rs.getString("category.categoryName"));
				book.setIssuedate(rs.getDate("issuedate"));
				book.setNumberpage(rs.getInt("numberpage"));
				book.setIdcategory(rs.getInt("idcategory"));
				book.setDescription(rs.getString("description"));
				List<Image> images = imageDAO.selectImagesByIdbook(String.valueOf(idbook)); 
				book.setImages(images);
				ps1.setInt(1, idbook);
				rs1 = ps1.executeQuery();
				rs1.next();
				book.setNumbersold(rs1.getInt("numbersold"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return book;
	}
	@PostMapping("/book")
	public ResponseEntity<String> addBook(@RequestBody Book book) {
		Connection con = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs, rs1=null;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_demo", "root", "Dang30032002.");
			List<String> titles = new ArrayList<>();
			List<String> authors = new ArrayList<>();
			st = con.createStatement();
			rs = st.executeQuery("select * from `jdbc_thuvien`.`book`");
			while(rs.next()) {
				titles.add(rs.getString("title").toLowerCase());
				authors.add(rs.getString("author").toLowerCase());
			}
			if(titles.contains(book.getTitle().toLowerCase()) && authors.contains(book.getAuthor().toLowerCase())) {
				throw new Exception("Book is already exist!");
			}
			ps = con.prepareStatement("INSERT INTO `jdbc_thuvien`.`book` (`title`, `author`, `category`, `issuedate`," + 
					"`numberpage`, `description`) VALUES (?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			if(book.getCategory() == null)
				ps.setString(3, "6");
			else
				ps.setString(3, book.getCategory());
			ps.setDate(4, (java.sql.Date) book.getIssuedate());
			ps.setInt(5, book.getNumberpage());
			if(book.getDescription() == null)
				ps.setString(6, "N/A");
			else 
				ps.setString(6, book.getDescription());
			ps.executeUpdate();
			rs1 = ps.getGeneratedKeys();
            if (rs1.next()) {
                return ResponseEntity.ok(String.valueOf(rs1.getInt(1)));
            }
			return ResponseEntity.ok("Book added successfully");
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding book");
		}
		
	}
	@PutMapping("/book/{idbook}")
	public ResponseEntity<String> updateBook(@RequestBody Book book, @PathVariable String idbook) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_demo", "root", "Dang30032002.");
			ps = con.prepareStatement("UPDATE `jdbc_thuvien`.`book` SET `title` = ?, `author` = ?, `category` = ?,"+
			"`issuedate` = ?, `numberpage` = ?, `description` = ? WHERE (`idbook` = ?)");
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getCategory());
			ps.setDate(4, (java.sql.Date) book.getIssuedate());
			ps.setInt(5, book.getNumberpage());
			ps.setString(6, book.getDescription());
			ps.setInt(7, Integer.parseInt(idbook));
			ps.executeUpdate();
			ps.close();
			con.close();
			return ResponseEntity.ok(idbook);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating book");
		}
		
	}
	private FeedbackDAO fbDAO = new FeedbackDAO();
	@DeleteMapping("/book/{idbook}")
	public ResponseEntity<String> delete(@PathVariable String idbook) {
		Connection con = null;
		PreparedStatement ps, ps1, ps2, ps3, ps4 = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_demo", "root", "Dang30032002.");
			ps = con.prepareStatement("DELETE FROM `jdbc_thuvien`.`book` WHERE (`idbook` = ?);");
			ps1 = con.prepareStatement("select * from `jdbc_thuvien`.`order` where idbook = ?");
			ps2 =  con.prepareStatement("DELETE FROM `jdbc_thuvien`.`order` WHERE (`idbook` = ?);");
			ps3 =  con.prepareStatement("DELETE FROM `jdbc_thuvien`.`feedback` WHERE (`idbook` = ?);");
			ps4 = con.prepareStatement("DELETE FROM `jdbc_thuvien`.`image` WHERE (`idbook` = ?)");
			ps1.setInt(1, Integer.parseInt(idbook));
			rs = ps1.executeQuery();
			List<Order> orders = new ArrayList<>();
			List<Feedback> fbs = fbDAO.selectFbsByIdbook(idbook);
			while(rs.next()) {
				int idorder = rs.getInt("idorder");
				int idb = rs.getInt("idbook");
				int cancel = rs.getInt("cancel");
				orders.add(new Order(idorder, idb, cancel));
			}
			if(orders.size()==0 && fbs.size()==0) {
				ps4.setInt(1, Integer.parseInt(idbook));
				ps4.executeUpdate();
				ps.setInt(1, Integer.parseInt(idbook));
				ps.executeUpdate();
			}
			else {
				if(orders.size()>0) {
					for(int i=0; i<orders.size(); i++) {
					if(orders.get(i).getCancel()==0) 
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting book");
					}
					ps2.setInt(1, Integer.parseInt(idbook));
					ps2.executeUpdate();
				}
				if(fbs.size()>0) {
					ps3.setInt(1, Integer.parseInt(idbook));
					ps3.executeUpdate();
				}		
				ps4.setInt(1, Integer.parseInt(idbook));
				ps4.executeUpdate();
				ps.setString(1, idbook);
				ps.executeUpdate();
			}
			
			ps.close();
			con.close();
			return ResponseEntity.ok("Book deleted successfully");
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting book");
		}
	}
}
