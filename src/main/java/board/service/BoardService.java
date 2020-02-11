package board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.dto.BoardDTO;
import board.dto.FileDTO;

public interface BoardService {

	List<BoardDTO> selectBoardList();

	void insertBoard(BoardDTO board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

	BoardDTO selectBoardDetail(int no);

	void updateBoard(BoardDTO dto);

	void deleteBoard(int no);

	FileDTO downloadfile(int no);

}
