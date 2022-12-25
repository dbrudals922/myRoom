package com.ykm.portfolio;

import lombok.*;

import javax.persistence.*;

@Getter // getter 메소드 생성
@Builder // 빌더를 사용할 수 있게 함
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="mbtiresult") // 테이블 명을 작성
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 4)
    private String info;
    
    @Column(nullable = false, length = 12)
    private String mbti;
    
    public MemberEntity(String info, String mbti) {
        this.info = info;
        this.mbti = mbti;
    }
    
}
