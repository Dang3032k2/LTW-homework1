package com.thuvien.btl.Order;

public class Order {
	private int idorder;
	private int idbook;
	private int iduser;
	private String title;
	private int number;
	private int cancel;
	private String imageurl;
	
	public Order() {
		
	}
	
	public Order(int idorder, int idbook, int iduser, String title, int number, int cancel, String imageurl) {
		this.idorder = idorder;
		this.idbook = idbook;
		this.iduser = iduser;
		this.title = title;
		this.number = number;
		this.cancel = cancel;
		this.imageurl = imageurl;
	}


	public Order(int idorder, int idbook, int cancel) {
		super();
		this.idorder = idorder;
		this.idbook = idbook;
		this.cancel = cancel;
	}

	public int getIdorder() {
		return idorder;
	}

	public void setIdorder(int idorder) {
		this.idorder = idorder;
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

	public int getCancel() {
		return cancel;
	}

	public void setCancel(int cancel) {
		this.cancel = cancel;
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
