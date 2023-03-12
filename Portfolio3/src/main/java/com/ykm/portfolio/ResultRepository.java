package com.ykm.portfolio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<ResultEntity, Integer> {
	
	@Query(value="SELECT UNIX_TIMESTAMP(Rtime) * 1000, COUNT(*) FROM mbtiresult GrOUP BY MINUTE(Rtime) OrDER BY Rtime;", nativeQuery =true)
	List<Object> selectResult();
	
	
}
