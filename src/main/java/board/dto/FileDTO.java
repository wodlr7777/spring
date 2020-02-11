package board.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class FileDTO {
	private int no;
	private int b_no;
	private String file_name;
	private String file_path;
	private long file_size;
	private Timestamp reg_date;
	private Timestamp edit_date;
	private char delete_yn;
	
}
