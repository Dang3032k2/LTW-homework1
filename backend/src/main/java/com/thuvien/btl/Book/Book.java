package com.thuvien.btl.Book;

import java.sql.Date;
import java.util.List;

import com.thuvien.btl.Image.Image;

public class Book {
	private int idbook;
	private String title;
	private String author;
	private String category;
	private Date issuedate;
	private int numberpage;
	private int idcategory;
	private int numbersold;
	private String description;
	private List<Image> images;
	
	public Book() {
		
	}

	public Book(int idbook, String title, String author, String category, Date issuedate, int numberpage,
			int idcategory, int numbersold, String description, List<Image> images) {
		super();
		this.idbook = idbook;
		this.title = title;
		this.author = author;
		this.category = category;
		this.issuedate = issuedate;
		this.numberpage = numberpage;
		this.idcategory = idcategory;
		this.numbersold = numbersold;
		this.description = description;
		this.images = images;
	}

	public Book(int idbook, String title, String author, List<Image> images) {
		super();
		this.idbook = idbook;
		this.title = title;
		this.author = author;
		this.images = images;
	}



	public int getIdbook() {
		return idbook;
	}

	public void setIdbook(int idbook) {
		this.idbook = idbook;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	

	public Date getIssuedate() {
		return issuedate;
	}



	public void setIssuedate(Date issuedate) {
		this.issuedate = issuedate;
	}



	public int getNumberpage() {
		return numberpage;
	}

	public void setNumberpage(int numberpage) {
		this.numberpage = numberpage;
	}

	public int getIdcategory() {
		return idcategory;
	}

	public void setIdcategory(int idcategory) {
		this.idcategory = idcategory;
	}

	public int getNumbersold() {
		return numbersold;
	}

	public void setNumbersold(int numbersold) {
		this.numbersold = numbersold;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

}
