package board.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardDTO {

	private int no;
	private String title;
	private String content;
	private int count;
	private String writer;
	private Timestamp reg_date;
	private Timestamp edit_date;
	private List<FileDTO> fileList;
}
