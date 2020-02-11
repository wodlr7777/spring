package board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.dto.BoardDTO;
import board.dto.FileDTO;
import board.service.BoardService;
@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	@RequestMapping("/n")
	public String load() {
		return "/NewFile";
	}
	@RequestMapping("/board/boardList.bo")
	public ModelAndView boardList() {
		System.out.println("BoardController : boardList() 실행");
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/board/boardList");
		// .html 확장자를 쓰지않아도 
		//	Thymeleaf에 의해  templates 폴더아래 있는 
		//  boardList.html 파일을 의미한다.
		
		List<BoardDTO> list=boardService.selectBoardList();
		//System.out.println(list);
		System.out.println("list 사이즈 : "+ list.size());
		//if(list.size()!=0)
		mv.addObject("list", list);
		//게시글 정보
		
		return mv;
	}
	
	//게시글 작성 페이지 이동
	@RequestMapping("/board/openBoardWrite.bo")
	public String openBoardWrite() {
		return "/board/boardWrite";
	}
	//게시글 등록 처리
	@RequestMapping("/board/insertBoard.bo")
	public String insertBoard(BoardDTO board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {//form 데이터가 파라미터 BoardDTO 에 자동 set
		System.out.println("board:"+board);
		boardService.insertBoard(board, multipartHttpServletRequest);
		return "redirect:/board/boardList.bo";
	}
	//상세페이지 이동
	@RequestMapping("/board/openBoardDetail.bo")
	public ModelAndView openBoardDetail(@RequestParam int no) {
		ModelAndView mv=new ModelAndView();
		//주소정보
		mv.setViewName("/board/boardDetail");
		BoardDTO detailDTO=boardService.selectBoardDetail(no);
		mv.addObject("detail", detailDTO);
		return mv;
	}
	//수정하기
	@RequestMapping("/board/updateBoard.bo")
	public String updateBoard(BoardDTO dto) {
		boardService.updateBoard(dto);
		return "redirect:/board/boardList.bo";
	}
	//삭제하기
	@RequestMapping("board/deleteBoard.bo")
	public String deleteBoard(int no) {
		System.out.println("no: "+no);
		boardService.deleteBoard(no);
		return "redirect:/board/boardList.bo";
	}
	@RequestMapping("/board/downloadFile.bo")
	public void downloadFile(@RequestParam int no,HttpServletResponse response) throws Exception{
		FileDTO fileDTO=boardService.downloadfile(no);
		if(ObjectUtils.isEmpty(fileDTO)==false) {//비어있지않으면
			String file_name=fileDTO.getFile_name();
			String file_path=fileDTO.getFile_path();
			File file=new File(file_path);
			//file_path에서 파일정보를 읽어서 byte[]형태로 변환
			//FileUtils==>org.apache.commons.io.FileUtils
			byte[] files=FileUtils.readFileToByteArray(file);
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; filename=\""+URLEncoder.encode(file_name,"utf-8")+"\";");
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
		
	}
}
