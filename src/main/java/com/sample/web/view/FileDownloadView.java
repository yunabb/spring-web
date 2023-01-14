package com.sample.web.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.sample.exception.ApplicationException;

@Component
// 컨트롤러에서 사용한다.
public class FileDownloadView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 이 메소드안에 적어놓으면 응답으로 내려오게됨
	
		// 컨트롤러에서 담은 이름에서 꺼냄
		File file = (File) model.get("file");
		
		// 파일의 컨텐츠 타입을 지정하기
		// application/octet-stream - 일반적인 바이너리 데이터에 대한 컨텐츠 타입(그림, 영상, 워드 등 메모장으로 읽을 수 없는 파일)
		setContentType("application/octet-stream");
		// 응답메시지의 헤더부에 다운로드되는 펌부파일을 이름으로 설정한다.
		// attachment;는 브라우저에서 파일을 열지 않고, 항상 다운로드되게 한다.
		// URLEncoder.encode(text, encoding) 은 텍스트를 지정된 인코딩 방식으로 변환시킨다.
		// 텍스트에 하ㅣㄴ글이 포함되어 있는 경우 utf-8방식으로 인코딩(변환)하지 않으면 한글이 전부 깨진다.
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "utf-8"));
		
		// 파일을 읽어오는 스트림 객체를 생성한다.
		InputStream in = new FileInputStream(file);
		
		// 브라우저와 연결된 출력스트림을 획득한다.
		OutputStream out = response.getOutputStream();
		
		// 입력스트림으로 읽은 데이터를 출력스트림으로 복사해서 출력시킨다.
		IOUtils.copy(in, out);
		// 응답받아졌는지 체크용
//		System.out.println("### FileDownloadView의 renderMergedOutputModel() 실행됨");
//		System.out.println("### FileDownloadView의 model객체: " + model);
	}
}
