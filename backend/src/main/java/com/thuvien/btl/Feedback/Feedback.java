package com.thuvien.btl.Feedback;

public class Feedback {
	private int idfb;
	private int idbook;
	private int iduser;
	private String name;
	private String comment;
	private int star;
	public Feedback() {
	
	}
	public Feedback(int idfb, int idbook, int iduser,String name, String comment, int star) {
		this.idfb = idfb;
		this.idbook = idbook;
		this.iduser = iduser;
		this.name = name;
		this.comment = comment;
		this.star = star;
	}
	
	public int getIdfb() {
		return idfb;
	}
	public void setIdfb(int idfb) {
		this.idfb = idfb;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	
}
