package shop.mtcoding.blog.board;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardPersistRepository {
    private final EntityManager em;

    @Transactional
    public void updateById(int id, BoardRequest.UpdateDTO reqDTO){
        Board board = findById(id);
        board.update(reqDTO);
    }  // 영속화 된 객체의 상태를 변경하고 트랜잭션이 종료되면 업데이트가 된다 -> 더티 체킹

    //테스트 해보기
    @Transactional // 트랜잭션의 기본이 commit, rollback 트랜잭션이 안되면 롤백 이게 기본
    //스프링이 트랜잭션 어노테이션을 하면 리플렉션을 하게 된다 고립성을 가진다
    //종료 될때 기본이 commit이다.
    //예외가 발생하면 기본값이 롤백이다
    //이걸 자동으로 해준다
    //트랜잭션도 중복이 걸린다
    public void deleteByIdV2(int id){
        Board board = findById(id);
        em.remove(board); // PC에 객체를 채우고, (트랜잭션이 종료 시에) 쿼리를 전송을 한다 쿼리가 날아가지 않는다? 트랜잭션이 종료가 되지 않았다는 것
    }
    //트랜잭션이 날아갈 때 쿼리가 날아감

    @Transactional
    public void deleteById(int id){
        Query query = em.createQuery("delete from Board b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public Board findById(int id){
        Board board = em.find(Board.class, id);
        return board;
    }

    public List<Board> findAll(){
        Query query = em.createQuery("select b from Board b order by b.id desc", Board.class);
        return query.getResultList();
    }

    @Transactional
    public Board save(Board board){
        // 비영속 객체
        em.persist(board);
        // board -> 영속 객체
        return board;
    }


}
