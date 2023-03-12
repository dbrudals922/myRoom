package com.ykm.portfolio;

import lombok.*;

import javax.persistence.*;

@Getter // getter 메소드 생성
@Builder // 빌더를 사용할 수 있게 함
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="questions") // 테이블 명을 작성
public class QuestionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 2)
    private String type;
    
    @Column(nullable = false, length = 100)
    private String s1;
    
    @Column(nullable = false, length = 100)
    private String s2;
    
    public QuestionsEntity(String title, String type, String s1, String s2) {
    	this.title=title;
    	this.type=type;
    	this.s1=s1;
    	this.s2=s2;
    }
}
