package board.common;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.dto.FileDTO;

@Component//스프링빈으로 등록
public class FileUtils {
	
	public List<FileDTO> parseFileInfo(int b_no, MultipartHttpServletRequest mr) throws Exception {
		//첨부파일 미존재시..
		if(ObjectUtils.isEmpty(mr)) {
			return null;
		}
		//첨부파일 존재시..처리
		
		List<FileDTO> fileList=new ArrayList<>();
		//파일이 저장될 폴더 날짜를 이용하여 생성
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyyMMdd");
		ZonedDateTime now=ZonedDateTime.now();
		String path="files/"+now.format(formatter);
		System.out.println("path : "+path);
		File dir=new File(path);
		if(dir.exists()==false) {
			dir.mkdir();//파일경로가 생성 되지 않았으면 디렉터리 생성
		}
		///////////////////////////////
		Iterator<String> it=mr.getFileNames();
		while(it.hasNext()) {
			String files=it.next();
			List<MultipartFile> list=mr.getFiles(files);//파일이름으로 파일 얻어오기 
			for(MultipartFile mf:list) {
				/*
				 * mf.getContentType()//파일의 타입을 if문을 이용해서 파일 제한 할수도 있다.
				 */
				String orgName=mf.getOriginalFilename();
				System.out.println("파일이름 :"+orgName);
				String[] names=orgName.split("[.]");
				System.out.println("길이:"+names.length);
				System.out.println(names[1]);
				String extension=names[names.length-1];//확장자만 저장
				System.out.println("확장자 : "+extension);
				String newName=Long.toString(System.nanoTime())+"."+extension;
				System.out.println("새로운 파일이름 : "+newName);
			
				
				FileDTO dto=new FileDTO();
				dto.setB_no(b_no);//게시글 번호
				dto.setFile_name(mf.getOriginalFilename());//원래 파일이름
				dto.setFile_path(dir+"/"+newName);//실제저장위치
				dto.setFile_size(mf.getSize());
				
				fileList.add(dto);
				
				//실제저장위치로 저장
				//업로드된 파일을 새로운 이름으로 바꾸어서 지정된 경로에 저장
				File file=new File(path+"/"+newName);
				mf.transferTo(file);
			}
			
		}
		return fileList;
	}
}
