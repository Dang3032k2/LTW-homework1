package com.thuvien.btl.Image;

public class Image {
	private int idimage;
	private int idbook;
	private String imageurl;
	

	public Image() {
		
	}
	
	public Image(int idimage, int idbook, String imageurl) {
		this.idimage = idimage;
		this.idbook = idbook;
		this.imageurl = imageurl;
	}

	public int getIdimage() {
		return idimage;
	}
	public void setIdimage(int idimage) {
		this.idimage = idimage;
	}
	public int getIdbook() {
		return idbook;
	}
	public void setIdbook(int idbook) {
		this.idbook = idbook;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	
}
