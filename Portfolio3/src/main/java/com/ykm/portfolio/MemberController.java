package com.ykm.portfolio;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController // JSON 형태 결과값을 반환해줌 (@ResponseBody가 필요없음)
@RequiredArgsConstructor // final 객체를 Constructor Injection 해줌. (Autowired 역할)
@RequestMapping("/v1") // version1의 API

public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("member")
    public List<MemberEntity> selectCode() {
        return memberRepository.findAll();
    }
    
    @PostMapping("result")
    public MemberEntity InsertQuestion(@RequestBody	List<String> q ) {
		final MemberEntity member = MemberEntity.builder()
            .info(q.get(0))
            .mbti(q.get(1))
            .build();
        	return memberRepository.save(member);
    }
}
