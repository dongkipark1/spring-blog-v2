package shop.mtcoding.blog.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


// JpaRepository -> CRUD 제공
// JPAREPOSITORY는 타입이 다르면

public interface BoardJPARepository extends JpaRepository<Board, Integer> {

    @Query("select new shop.mtcoding.blog.board.BoardResponse$CountDTO(b.id, b.title, b.content, b.user.id, (select count(r.id) from Reply r where r.board.id = b.id)) from Board b")
    List<BoardResponse.CountDTO> findAllWithReplyCount();

    // 1건만 넣을 때는 param 안넣어도 되지만, 넣어라
    @Query("select b from Board b join fetch b.user u where b.id = :id")
    Optional<Board> findByIdJoinUser(@Param("id") int id);


    @Query("select b from Board b join fetch b.user u left join fetch b.replies r where b.id = :id")
    Optional<Board> findByIdJoinUserAndReplies(@Param("id") int id);

}
