package board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import board.dto.BoardDTO;
import board.dto.FileDTO;

@Mapper// 매퍼 인터 페이스임을 선언
public interface BoardMapper {

	List<BoardDTO> selectBoardList();
	//mapper.xml과 연동한다.
	//메서드의 이름이 xml의 id와 일치해서 만든다.

	void insertBoard(BoardDTO board);

	void updateBoardCount(int no);

	BoardDTO selectBoardDetail(int no);

	void updateBoard(BoardDTO dto);

	void deleteBoard(int no);

	void insertBoardFileList(List<FileDTO> fileList);

	List<FileDTO> selectFileList(int no);

	FileDTO downloadfile(int no);

}
