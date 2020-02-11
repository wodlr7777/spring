package board.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.common.FileUtils;
import board.dto.BoardDTO;
import board.dto.FileDTO;
import board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	BoardMapper boardMapper;

	@Override
	public List<BoardDTO> selectBoardList() {
		// 비지니스 로직
		// DB처리---> 
		System.out.println("selectBoardList 처리");
		return boardMapper.selectBoardList();
	}
	
	@Autowired
	FileUtils fileUtils;
	
	@Transactional
	@Override
	public void insertBoard(BoardDTO board, MultipartHttpServletRequest mr) throws Exception {
		//board DB 저장
		System.out.println("before no : "+board.getNo());
		boardMapper.insertBoard(board);
		System.out.println("after no : "+board.getNo());
		//여러파일을 List에 저장
		//FileUtils fileUtils=new FileUtils();
		List<FileDTO> fileList=fileUtils.parseFileInfo(board.getNo(), mr);
		//t_file DB 저장
		//if(CollectionUtils.isEmpty(fileList)==false) {}
		System.out.println("list-size : "+fileList.size());
		if(fileList!=null) {
			boardMapper.insertBoardFileList(fileList);
		}
		
	}
	
	@Transactional
	@Override
	public BoardDTO selectBoardDetail(int no) {
		// 조회수 증가처리...
		boardMapper.updateBoardCount(no);
		System.out.println("조회수 증가 완료!");
		//Detail정보 리턴
		BoardDTO board=boardMapper.selectBoardDetail(no);
		//첨부파일 정보 갖고오기
		List<FileDTO> fileList=boardMapper.selectFileList(no);
		board.setFileList(fileList);
		return board;
	}
	
	@Transactional
	@Override
	public void updateBoard(BoardDTO dto) {
		boardMapper.updateBoard(dto);
		System.out.println("수정완료!");
	}

	@Override
	public void deleteBoard(int no) {
		boardMapper.deleteBoard(no);
		
	}

	@Override
	public FileDTO downloadfile(int no) {
		return boardMapper.downloadfile(no);
		
	}



}
