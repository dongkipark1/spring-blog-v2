package shop.mtcoding.blog.board;


import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(BoardPersistRepository.class)
@DataJpaTest
public class BoardPersistRepositoryTest {

    @Autowired // DI   IoC에 있는 것을 DI
    private BoardPersistRepository boardPersistRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void updateById_test(){
        //given
        int id = 1;
        String title = "제목수정1";

        //when
        Board board = boardPersistRepository.findById(id); // 영속화
        board.setTitle(title);

        em.flush();

    }

    @Test
    public void deleteById_test(){
        //given
        int id = 1;

        //when
        boardPersistRepository.deleteById(id);

        //then
//        List<Board> boardList = boardPersistRepository.findAll();
//        assertThat(boardList.size()).isEqualTo(3);
    }

    @Test
    public void deleteByIdV2_test(){
        //given
        int id = 1;

        //when
        boardPersistRepository.deleteByIdV2(id);
        //쿼리를 강제로 날린다.
        em.flush(); // 버퍼에 쥐고 있는 쿼리를 즉시 전송 테스트 코드에서만
    } //테스트 코드는 기본값이 롤백이다 왜? 정보를 저장할 필요가 없스니까

    //트랜잭션이 날아갈 때 쿼리가 날아감
    //그러나 왜 안날아가는 가 자식 쿼리
    //내부에 들고 있는 어노테이션 레터 어노테이션



    @Test
    public void findAll_test() {
        // given

        // when
        List<Board> boardList = boardPersistRepository.findAll();

        // then
        System.out.println("findAll_test/size : " + boardList.size());
        System.out.println("findAll_test/username : " + boardList.get(2).getUsername());

        // org.assertj.core.api
        assertThat(boardList.size()).isEqualTo(4);
        assertThat(boardList.get(2).getUsername()).isEqualTo("ssar");
    }


    @Test
    public void save_test(){
        //given
        Board board = new Board("제목","내용5", "ssar");
        //when
        boardPersistRepository.save(board);
        System.out.println("save_test: " + board);
        //then
    }

    @Test
    public void findById_test() {

        //given
        int id = 1;

        //when
        Board board = boardPersistRepository.findById(id);
        em.clear(); //PC를 비운다.
        boardPersistRepository.findById(id); // 캐싱
        System.out.println("findById_test:" +board);

        //then

        assertThat(board.getTitle()).isEqualTo("제목1");
        assertThat(board.getContent()).isEqualTo("내용1");
    }


}
