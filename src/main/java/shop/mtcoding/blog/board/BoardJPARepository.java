package shop.mtcoding.blog.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


// JpaRepository -> CRUD 제공

public interface BoardJPARepository extends JpaRepository<Board, Integer> {


    // 1건만 넣을 때는 param 안넣어도 되지만, 넣어라
    @Query("select b from Board b join fetch b.user u where b.id = :id")
    Board findByIdJoinUser(@Param("id") int id);

}