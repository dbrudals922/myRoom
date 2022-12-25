package com.ykm.portfolio;

import lombok.*;

import javax.persistence.*;

@Getter // getter 메소드 생성
@Builder // 빌더를 사용할 수 있게 함
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="h_well_lo") // 테이블 명을 작성
public class __MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String code;

    @Column(nullable = false, length = 2)
    private String name;
    
}
