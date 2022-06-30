package com.board.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.board.config.RootConfig;
import com.board.domain.Criteria;
import com.board.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {RootConfig.class}) 
@Log4j
public class ReplyMapperTests {

	private Long[] bnoArr = { 5242926L, 5242922L, 5242921L, 5242920L, 5242919L };
	
	@Setter(onMethod_ = { @Autowired })
	private ReplyMapper mapper;

	@Test
	public void testList2(){
		
		Criteria cri = new Criteria(1, 10);
		
		// 5242922
		List<ReplyVO> replies = mapper.getListWithPaging(cri, 5242922L);
		
		replies.forEach(reply -> log.info(reply));
		
	}
	
	@Test
	public void testList(){
		
		Criteria cri = new Criteria();
		
		// 5242926
		List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[0]);
		
		replies.forEach(reply -> log.info(reply));
		
	}
	
	@Test
	public void testUpdate(){
		
		Long targetRno = 10L;
		
		ReplyVO vo = mapper.read(targetRno);
		
		vo.setReply("Update Reply");
		
		int count = mapper.update(vo);
		
		log.info("UPDATE COUNT: " + count);
		
	}
	
	@Test
	public void testDelete(){
		
		Long targetRno = 1L;
		
		mapper.delete(targetRno);
		
	}
	
	@Test
	public void testRead(){
		
		Long targetRno = 5L;
		
		ReplyVO vo = mapper.read(targetRno);
		
		log.info(vo);
		
	}
	
	@Test
	public void testCreate(){
		
		IntStream.rangeClosed(1, 10).forEach(i -> {
			
			ReplyVO vo = new ReplyVO();
			
			// 게시물 번호
			vo.setBno(bnoArr[i % 5]);
			vo.setReply("댓글 테스트  " + i);
			vo.setReplyer("replyer " + i);
			
			mapper.insert(vo);
		});
		
	}
	
	@Test
	public void testMapper(){
		
		log.info(mapper);
	}
	
}
