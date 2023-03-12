package com.ykm.portfolio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ykm.portfolio.ws.LinkWebSocketHandler;

import lombok.RequiredArgsConstructor;

@RestController // JSON 형태 결과값을 반환해줌 (@ResponseBody가 필요없음)
@RequiredArgsConstructor // final 객체를 Constructor Injection 해줌. (Autowired 역할)
@RequestMapping("/v1") // version1의 API
//@ComponentScan(basePackages = {"com.ykm.portfolio.quartzA", "com.ykm.protfolio.ws"} )
@ComponentScan(basePackages = {"com.ykm.protfolio.ws"})

public class MainController {
	
    private final ResultRepository resultRepository;
    private final LocalRepository localRepository;
    private final QuestionsRepository questionsRepository;
    
    @GetMapping("local")
    public List<LocalEntity> selectLocal(){
    	return localRepository.findAll();
    }

    @GetMapping("result")
    public List<ResultEntity> selectCode() {
        return resultRepository.findAll();
    }
    
    @GetMapping("getQuestions")
    public List<QuestionsEntity> selectQuestions() {
        return questionsRepository.findAll();
    }
    
    @GetMapping("getTodayCount")
    public int selectTCount() {
    	String date = String.valueOf(LocalDateTime.now()).split("T")[0];
    	
    	List<ResultEntity> resultList = this.selectCode().stream()
    	.filter(r -> String.valueOf(r.getRtime()).split("T")[0].equals(date))
    	.collect(Collectors.toList());
    	
    	return resultList.size();
    }
    
    @PostMapping("result")
    public ResultEntity InsertResult(@RequestBody	List<String> r ) {
		final ResultEntity member = ResultEntity.builder()
			.local(r.get(0))
            .age(r.get(1))
            .s(r.get(2))
            .mbti(r.get(3))
            .process(r.get(4))
            .build();
		
		
        	return resultRepository.save(member);
    }
    
    @PostMapping("insertQuestion")
    public QuestionsEntity InsertQuestion(@RequestBody List<String> question) {
    	final QuestionsEntity que = QuestionsEntity.builder()
    			.title(question.get(0))
    			.type(question.get(1))
    			.s1(question.get(2))
    			.s2(question.get(3))
    			.build();
    	return questionsRepository.save(que);
    }
}
