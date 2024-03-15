package shop.mtcoding.blog.board;


import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(BoardRepository.class)
@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void updateById_test(){
        //given
        int id = 1;
        String title = "hi";
        String content = "안녕하세요";
        //when
        boardRepository.updateById(id, title, content);
        em.flush(); // 실제 코드에는 작성 안해도 된다? why? 트랜잭션 종료가 될거니까 여기는 트랜잭션 종료가 되면 테스트 종료되서 코드 못 봄
        //then
    }

    @Test
    public void deleteById_test(){
        //given
        int id = 1;
        //when
        boardRepository.deleteById(id); // delete 쿼리 발동함 (직접 날라감) flush x
        //then
        System.out.println("deleteById_test: " + boardRepository.findAll().size());
    }

    @Test
    public void findAllV2_test(){
        List<Board> boardList = boardRepository.findAllV2();
        System.out.println("findAllV2_test : 조회완료 쿼리 2번");
        boardList.forEach(board -> {
            System.out.println(board);
        });
    }

    @Test
    public void randomquery_test(){
        int[] ids = {1,2};

        // select u from User u where u.id in (?,?);
        String q = "select u from User u where u.id in (";
        for (int i=0; i<ids.length; i++){
            if(i==ids.length-1){
                q = q + "?)";
            }else{
                q = q + "?,";
            }
        }
        System.out.println(q);
    }

    @Test
    public void findAll_custom_inquery_test(){
        //given

        //when
        List<Board> boardList = boardRepository.findAll();

        int[] usedIds = boardList.stream().mapToInt(board -> board.getUser().getId()).distinct().toArray();
        for (int i : usedIds){
            System.out.println(i);
        }
        //then
    }

    @Test
    public void findAll_lazyloading_test(){
        //given

        //when
        List<Board> boardList = boardRepository.findAll();
        boardList.forEach(board -> {
            System.out.println(board.getUser().getUsername()); // lazy loading
        });
        //then
    }

    @Test
    public void findAll_test(){
        //given

        //when
        List<Board> boardList = boardRepository.findAll();
        //then
    }

    @Test
    public void findByIdJoinUser(){
        int id = 1;

        Board board = boardRepository.findByIdJoinUser(id);
    }

    @Test
    public void findById_test(){
        int id = 1;
        System.out.println("start - 1");
        Board board = boardRepository.findById(id);
        System.out.println("start - 2");
        System.out.println(board.getUser().getId());
        System.out.println("start - 3");
        System.out.println(board.getUser().getUsername());

//        Board board = new Board(1, "제목1", "내용", "날짜");

    }
}
