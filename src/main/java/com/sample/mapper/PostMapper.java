package com.sample.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sample.dto.PostDetailDto;
import com.sample.dto.PostListDto;
import com.sample.vo.AttachedFile;
import com.sample.vo.Post;

@Mapper
public interface PostMapper {

	void insertPost(Post post);
	
	int getTotalRows();
	List<PostListDto> getPosts(Map<String, Object> param);
	
	Post getPostByNo(int postNo);
	void updatePost(Post post);
	
	PostDetailDto getPostDetailByNo(int postNo);
	
	void insertAttachedFile(AttachedFile attachedFile);

}
