package board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.dto.BoardDTO;
import board.entity.BoardEntity;
import board.entity.BoardFileEntity;

public interface JpaBoardService {

	List<BoardEntity> selectBoardList();

	void saveBoard(BoardEntity boardEntity, MultipartHttpServletRequest mr) throws Exception;

	BoardEntity selectBoardDetail(int no);

	BoardFileEntity selectBoardFileInformation(int fileNO);

	void deleteBoard(int no);

}
