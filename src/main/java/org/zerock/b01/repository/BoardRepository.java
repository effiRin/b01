package org.zerock.b01.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.Board;
import org.zerock.b01.repository.search.BoardSearch;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {


    @EntityGraph(attributePaths = "imgSet")
    // board를 가지고 올때 entityGraph(이미지 세트)까지 같이 함께 로딩해서 쓰겠다는 것.
    // 한번에 조인해서 가져올 것임. but 조회할땐 좋은데 목록 뽑을땐 n+1문제가 생긴다.

    @Query("select b from Board b where b.bno = :bno")
    Optional<Board> getWithImage(Long bno);

}
