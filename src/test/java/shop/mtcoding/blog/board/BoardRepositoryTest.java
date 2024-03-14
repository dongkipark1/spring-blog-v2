package shop.mtcoding.blog.board;


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
