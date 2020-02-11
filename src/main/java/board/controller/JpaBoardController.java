package board.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.dto.BoardDTO;
import board.entity.BoardEntity;
import board.entity.BoardFileEntity;
import board.service.JpaBoardService;

@Controller
public class JpaBoardController {

	@Autowired
	private JpaBoardService jpaBoardService;
	
	@RequestMapping(value = "/jpa/board",method = RequestMethod.GET)
	public ModelAndView boardList() {
		System.out.println("BoardController : boardList() 실행");
		ModelAndView mv=new ModelAndView();
		mv.setViewName("jpa/boardList");
		// .html 확장자를 쓰지않아도 
		//	Thymeleaf에 의해  templates 폴더아래 있는 
		//  boardList.html 파일을 의미한다.
		
		List<BoardEntity> list=jpaBoardService.selectBoardList();
		//System.out.println(list);
		System.out.println("list 사이즈 : "+ list.size());
		//if(list.size()!=0)
		mv.addObject("list", list);
		//게시글 정보
		
		return mv;
	}
	
	@GetMapping("/jpa/board/write")
	public String openBoardWrite() {
		return "/jpa/boardWrite";
	}
	@PostMapping("/jpa/board/write")
	public String boardWrite(BoardEntity boardEntity,MultipartHttpServletRequest mr) throws Exception {
		jpaBoardService.saveBoard(boardEntity,mr);
		return "redirect:/jpa/board";
	}
	@RequestMapping("/jpa/board/{no}")
	public ModelAndView openBoardDetail(@PathVariable(name = "no") int no) {
		System.out.println(no);
		ModelAndView mv=new ModelAndView();
		//주소정보
		mv.setViewName("jpa/boardDetail");
		BoardEntity detailDTO=jpaBoardService.selectBoardDetail(no);
		mv.addObject("detail", detailDTO);
		return mv;
	}
	@GetMapping("/jpa/board/file")
	public void downloadBoardFile(@RequestParam("boardno") int fileNO,HttpServletResponse response) throws IOException {
		BoardFileEntity file=jpaBoardService.selectBoardFileInformation(fileNO);
		byte[] files=FileUtils.readFileToByteArray(new File(file.getFile_path()));
		response.setContentType("application/octet-stream");
		response.setContentLength(files.length);
		response.setHeader("Content-Disposition", "attachment; filename=\""+URLEncoder.encode(file.getFile_name(),"utf-8")+"\";");
		response.getOutputStream().write(files);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	//수정하기
	//@RequestMapping(value = "/jpa/board/{boardNo}",	method = RequestMethod.PATCH)
	@PutMapping("/jpa/board/{boardNo}")
	public String updateBoard(BoardEntity board) throws Exception {
		jpaBoardService.saveBoard(board, null);
		System.out.println("??????????");
		return "redirect:/jpa/board";
	}
	
	//삭제하기
	@DeleteMapping("/jpa/board/{boardNo}")
	public String deleteBoard(@PathVariable("boardNo")int no) throws Exception {
		jpaBoardService.deleteBoard(no);
		return "redirect:/jpa/board";
}
}
