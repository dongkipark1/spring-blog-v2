package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    public Board findById(int id){
        // id, title, content, user_id(이질감 생김), created_at
        Board board = em.find(Board.class, id);
        return board;
    }
}
