package com.sample.web.controller;

import java.io.File;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sample.dto.PostDetailDto;
import com.sample.exception.ApplicationException;
import com.sample.service.PostService;
import com.sample.web.login.LoginUser;
import com.sample.web.login.LoginUserInfo;
import com.sample.web.request.PostRegisterForm;
import com.sample.web.view.FileDownloadView;


@Controller
@RequestMapping("/post")
public class PostController {
	
	private final String directory = "c:/files";
	
	// di해서 스캔되게함
	@Autowired
	private PostService postService;
	@Autowired
	private FileDownloadView fileDownloadView;
	
	@GetMapping("/insert")
	@LoginUser
	public String form() {
		return "post/form";
	}
	
	@PostMapping("/insert")
	public String insertPost(@LoginUser LoginUserInfo loginUserInfo, PostRegisterForm form) throws IOException {
		// 첨부파일 업로드 처리
		// PostRegisterForm 안에 첨부파일이 들어있으니까
		MultipartFile upfile =form.getUpfile();
		if (!upfile.isEmpty()) {
			// 첨부파일 이름을 조회하고, PostRegisterForm객체에 대입한다.
			String filename = upfile.getOriginalFilename();
			form.setFilename(filename); // form에다가 주는 이유는 서비스에게 넘겨주려고!
			// 첨부파일을 지정된 디렉토리에 저장한다.
			FileCopyUtils.copy(upfile.getInputStream(), new FileOutputStream(new File(directory, filename)));
		}
		
		postService.insertPost(loginUserInfo.getId(), form);
		
		return "redirect:list";
	}
	
	// 요청 URL - http://localhost/post/list
	//           http://localhost/post/list?page=2
	@GetMapping("/list")
	public String list(@RequestParam(name = "page", required = false, defaultValue = "1") int page, Model model) {
		
		Map<String, Object> result = postService.getPosts(page);
		model.addAttribute("posts", result.get("posts"));
		model.addAttribute("pagination", result.get("pagination"));
		
		return "post/list";
	}
	
	// 요청 URL - http://localhost/post/read?postNo=2
	@GetMapping("/read")
	public String read(@RequestParam("postNo") int postNo) {
		postService.increaseReadCount(postNo);
		
		return "redirect:detail?postNo=" + postNo;
	}
	
	// 요청 URL - http://localhost/post/detail?postNo=2
	@GetMapping("/detail")
	public String detail(@RequestParam("postNo") int postNo, Model model) {
		
		PostDetailDto postDetailDto = postService.getPostDetail(postNo);
		model.addAttribute("post", postDetailDto);
		
		return "post/detail";
	}
	
	@GetMapping("/download")
	public ModelAndView FileDownloadBodyHandler(@RequestParam("filename") String filename) { //@RequestParam은 생략가능
		// 지정된 파일정보를 표현하는 File객체를 생성
		File file = new File(directory, filename);
		// 파일이 존재하지 않더라도 파일을 생성할 수 있다.
		// 파일이 존재하지 않으면 예외를 던진다.
		if (!file.exists()) {
			throw new ApplicationException("["+filename+"] 파일이 존재하지 않습니다");
		}
		
		ModelAndView mav = new ModelAndView();
		// ModelAndView의 Model에 값 저장
		mav.addObject("file", file);
		
		//ModelAndView의 View에 DownloadView객체 저장
		mav.setView(fileDownloadView);
		return mav;
	}
	
	@PostMapping("/insert-comment")
	public String insertComment(@LoginUser LoginUserInfo loginUserInfo,
			@RequestParam("postNo") int postNo,
			@RequestParam("content") String content) {
		
		postService.insertComment(loginUserInfo.getId(), content, postNo);
		
		return "redirect:detail?postNo=" + postNo;
	}
	
}
