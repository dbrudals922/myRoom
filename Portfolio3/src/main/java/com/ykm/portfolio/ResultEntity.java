package com.ykm.portfolio;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter // getter 메소드 생성
@Builder // 빌더를 사용할 수 있게 함
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="mbtiresult") // 테이블 명을 작성
public class ResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false, length = 2)
    private String local;

    @Column(nullable = false, length = 2)
    private String age;
    
    @Column(nullable = false, length = 1)
    private String s;
    
    @Column(nullable = false, length = 4)
    private String mbti;
    
    @Column(nullable = false, length = 50)
    private String process;
    
	@CreationTimestamp
	@Column(nullable = false, name = "Rtime")
	private LocalDateTime Rtime;
    
    public ResultEntity(String local, String age, String s, String mbti, String process) {
    	this.local= local; 
        this.age = age;
        this.s = s;
        this.mbti = mbti;
        this.process = process;
    }
    
}
