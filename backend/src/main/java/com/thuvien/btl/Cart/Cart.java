package com.thuvien.btl.Cart;

public class Cart {
	private int idcart;
	private int idbook;
	private int iduser;
	private int number;
	private String title;
	private String imageurl;
	public Cart() {
		
	}

	public Cart(int idcart, int idbook, int iduser, int number, String title, String imageurl) {
		this.idcart = idcart;
		this.idbook = idbook;
		this.iduser = iduser;
		this.number = number;
		this.title = title;
		this.imageurl = imageurl;
	}

	public int getIdcart() {
		return idcart;
	}

	public void setIdcart(int idcart) {
		this.idcart = idcart;
	}

	public int getIdbook() {
		return idbook;
	}

	public void setIdbook(int idbook) {
		this.idbook = idbook;
	}

	public int getIduser() {
		return iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	
	
}
