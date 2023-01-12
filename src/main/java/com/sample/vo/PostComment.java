package com.sample.vo;

import java.util.Date;

public class PostComment {
	
	private int no;
	private String id;
	private String content;
	private Date updatedDate;
	private Date createdDate;
	private Post postNo;
	
	public PostComment() {}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Post getPostNo() {
		return postNo;
	}

	public void setPostNo(Post postNo) {
		this.postNo = postNo;
	}
	
	

}
