package com.sample.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sample.dto.PostCommentListDto;
import com.sample.dto.PostDetailDto;
import com.sample.dto.PostListDto;
import com.sample.exception.ApplicationException;
import com.sample.mapper.PostCommentMapper;
import com.sample.mapper.PostMapper;
import com.sample.utils.Pagination;
import com.sample.vo.AttachedFile;
import com.sample.vo.Comment;
import com.sample.vo.Post;
import com.sample.web.request.PostRegisterForm;

@Service
@Transactional
public class PostService {

	@Autowired
	private PostCommentMapper postCommentMapper;
	@Autowired
	private PostMapper postMapper;

	// 게시글 등록 서비스
	public void insertPost(String userId, PostRegisterForm form) {
		Post post = new Post();
		post.setUserId(userId);
		BeanUtils.copyProperties(form, post);
		
		postMapper.insertPost(post); // insert가 먼저 실행되야 select가 실행되니까 순서가 먼저 와야한다
		// insertPost가 실행되고 나면 PostNo에 값이 들어가서 받아올 수 있다.
		
		AttachedFile attachedFile = new AttachedFile();
		attachedFile.setPostNo(post.getNo());
		attachedFile.setFilename(form.getFilename());
		
		postMapper.insertAttachedFile(attachedFile);		
	}

	// 게시글 목록 조회 서비스
	public Map<String, Object> getPosts(int page) {
		int totalRows = postMapper.getTotalRows();
		Pagination pagination = new Pagination(page, totalRows);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("begin", pagination.getBegin());
		param.put("end", pagination.getEnd());
		
		List<PostListDto> posts = postMapper.getPosts(param);
		
		Map<String, Object> result = new HashMap<>();
		result.put("posts", posts);
		result.put("pagination", pagination);
		
		return result;
	}

	// 게시글의 조회수를 증가시키는 서비스
	public void increaseReadCount(int postNo) {
		Post post = postMapper.getPostByNo(postNo);
		if (post == null) {
			throw new ApplicationException("["+postNo+"] 번 게시글이 존재하지 않습니다.");
		}
		if ("Y".equals(post.getDeleted())) {
			throw new ApplicationException("["+postNo+"] 번 게시글은 삭제된 게시글입니다.");
		}
		
		post.setReadCount(post.getReadCount() + 1);
		postMapper.updatePost(post);
	}
	
	// 게시글의 상세정보(게시글 정보와 댓글목록 정보)를 제공하는 서비스
	public PostDetailDto getPostDetail(int postNo) {
		PostDetailDto postDetailDto = postMapper.getPostDetailByNo(postNo);
		if (postDetailDto == null) {
			throw new ApplicationException("["+postNo+"] 번 게시글이 존재하지 않습니다.");
		}
		
		List<PostCommentListDto> postCommentListDtos = postCommentMapper.getPostCommentsByPostNo(postNo);
		postDetailDto.setComments(postCommentListDtos);
		
		return postDetailDto;
	}

	// 댓글을 등록하는 기능
	public void insertComment(String userId, String content, int postNo) {
		Post post = postMapper.getPostByNo(postNo);
		if (post == null) {
			throw new ApplicationException("["+postNo+"] 번 게시글이 존재하지 않습니다.");
		}
		
		Comment comment = new Comment();
		comment.setUserId(userId);
		comment.setContent(content);
		comment.setPostNo(postNo);		
		postCommentMapper.insertPostComment(comment);
		
		post.setCommentCount(post.getCommentCount() + 1);
		postMapper.updatePost(post);		
	}
}













