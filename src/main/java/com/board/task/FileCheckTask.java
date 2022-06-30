package com.board.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.board.domain.BoardAttachVO;
import com.board.mapper.BoardAttachMapper;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class FileCheckTask {

	@Autowired
	private BoardAttachMapper attachMapper;
	
	private String getFolderYesterDay(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DATE, -1);
		
		String str = sdf.format(cal.getTime());
		
		return str.replace("-", File.separator);
	}
	
	@Scheduled(cron="2 2 2 * * *")
	public void checkFiles() throws Exception {
		
		log.warn("File Check Task run............");
		log.warn(new Date());
		
		// 디비에 있는 어제 파일 조회
		List<BoardAttachVO> fileList = attachMapper.getOldFiles();
		
		// 디비에 있는 어제 파일 경로
		List<Path> fileListPaths = fileList.stream()
				.map(vo -> Paths.get("C:\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName()))
				.collect(Collectors.toList());
		
		// 디비에 있는  이미지 썸네일 파일 경로
		fileList.stream().filter(vo -> vo.isFileType() == true)
				.map(vo -> Paths.get("C:\\upload", vo.getUploadPath(), "s_" + vo.getUuid() + "_" + vo.getFileName()))
				.forEach(p -> fileListPaths.add(p));
		
		log.warn("===============================");
		
		fileListPaths.forEach(p -> log.warn(p));
		
		// 전날 파일 폴더
		File targetDir = Paths.get("C:\\upload", getFolderYesterDay()).toFile();
		
		// 전날 파일 폴더에서 디비에 없는 파일 추출
		File[] removeFiles = targetDir.listFiles(file -> fileListPaths.contains(file.toPath()) == false);
		
		log.warn("-------------------------------------------");
		// 파일 삭제
		for(File file : removeFiles){
			
			log.warn(file.getAbsolutePath());
			
			file.delete();
		}
		
	}
	
}
