package board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.common.JpafileUtils;
import board.dto.BoardDTO;
import board.entity.BoardEntity;
import board.entity.BoardFileEntity;
import board.repository.JpaBoardRepository;

@Service
public class JpaBoardServiceimpl implements JpaBoardService {
	@Autowired
	private JpaBoardRepository jpaBoardRepository;
	
	@Autowired
	private JpafileUtils jpafileUtils;
	
	@Override
	public List<BoardEntity> selectBoardList() {
		
		
		return jpaBoardRepository.findAllByOrderByBoardNoDesc(); //스네이크 표기법 금지
	}

	@Override
	public void saveBoard(BoardEntity boardEntity, MultipartHttpServletRequest mr) throws Exception {
		List<BoardFileEntity> fileList=jpafileUtils.parseFileInfo(mr);
		if(CollectionUtils.isEmpty(fileList)==false) {
		boardEntity.setFileList(fileList);
		}
		jpaBoardRepository.save(boardEntity);
	}

	@Override
	public BoardEntity selectBoardDetail(int no) {
		Optional<BoardEntity> optional=jpaBoardRepository.findById(no);// jpa 2.0이상 부터 사용가능
		if(optional.isPresent()) {
			BoardEntity board=optional.get();
			//read카운트 증가 처리
			board.setCount(board.getCount()+1);
			jpaBoardRepository.save(board);
			return board;
		}else {
			throw new NullPointerException();
		}
	}

	@Override
	public BoardFileEntity selectBoardFileInformation(int fileNO) {
		BoardFileEntity file=jpaBoardRepository.findBoard(fileNO);
		return file;
	}

	@Override
	public void deleteBoard(int no) {
		jpaBoardRepository.deleteById(no);;
		
	}

}
