package com.sample.dto;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("PostDetailDto")
public class PostDetailDto {

	private int no;
	private String title;
	private String userId;
	private String userName;
	private int readCount;
	private int commentCount;
	private Date createdDate;
	private Date updatedDate;
	private String content;
	private List<PostCommentListDto> comments;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<PostCommentListDto> getComments() {
		return comments;
	}
	public void setComments(List<PostCommentListDto> comments) {
		this.comments = comments;
	}
	
}
