package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.mtcoding.blog.user.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository // new BoardRepository IoC 컨테이너 등록
public class BoardRepository {
    private final EntityManager em;

    public List<Board> findAllV3(){
        String q1 = "select b from Board b order by b.id desc";
        List<Board> boardList = em.createQuery(q1 , Board.class).getResultList();

        int[] userIds = boardList.stream().mapToInt(board -> board.getUser().getId()).distinct().toArray();

        String q2 = "select u from User u where u.id in (";
        for (int i = 0; i < userIds.length ; i++) {
            if (i == userIds.length - 1){
                q2 = q2 + userIds[i] + ")";
            }else {
                q2 = q2 + userIds[i] + ",";
            }
        }
        List<User> userList = em.createQuery(q2 , User.class).getResultList();

        for (Board board : boardList){
            for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);
                if (user.getId() == board.getUser().getId()){
                    board.setUser(user);
                }
            }
        }

        return boardList; // user가 채워져 있어야함.
    }

    public List<Board> findAllV2(){
        Query q1 = em.createQuery("select b from Board b order by b.id desc", Board.class);
        List<Board> boardList = q1.getResultList();

        List<Integer> userIds = boardList.stream().mapToInt(value -> value.getUser().getId()).distinct().boxed().toList();

        Query q2 = em.createQuery("select u from User u where u.id IN :userIds", User.class);
        q2.setParameter("userIds", userIds);
        List<User> userList = q2.getResultList();

        boardList.stream().forEach(b -> {
            User user = userList.stream().filter(u -> u.getId() == b.getUser().getId()).findFirst().get();
        });
        return boardList; // user가 채워져 있어야 한다.
    }

    //        String q2 = "select u from User u where u.id in (";
//        q2 = q2 + ":id" + 1 + ",";
//        q2 = q2 + ":id" + 2 + ",";
//        q2 = q2 + ":id" + 3 + ")";
//        Query query2 = em.createQuery(q2, User.class);
//        query2.setParameter("id1", 3);
//        query2.setParameter("id2", 2);
//        query2.setParameter("id3", 1);


    public List<Board> findAll(){
        Query query = em.createQuery("select b from Board b order by b.id desc" , Board.class);
        return query.getResultList();
    }

    public Board findByIdJoinUser(int id){
        Query query = em.createQuery("select b from Board b join fetch b.user u where b.id = :id", Board.class);
        query.setParameter("id" , id);
        return (Board) query.getSingleResult();
    }

    public Board findById(int id){
        // id, title, content, user_id(이질감 생김), created_at
        Board board = em.find(Board.class, id);
        return board;
    }
}
