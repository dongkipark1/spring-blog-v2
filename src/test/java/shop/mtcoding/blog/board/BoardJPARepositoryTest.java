package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import shop.mtcoding.blog.user.User;

import java.util.List;
import java.util.Optional;
import java.util.TimerTask;

@DataJpaTest
public class BoardJPARepositoryTest {

    @Autowired
    private BoardJPARepository boardJPARepository;

    @Autowired
    private EntityManager em;

    // SAVE
    @Test
    public void save_test(){
        //given
        User sessionUser = User.builder().id(1).build();
        Board board = Board.builder()
                .title("제목5")
                .content("내용5")
                .user(sessionUser)
                .build();

        //when
        boardJPARepository.save(board);

        //then
        System.out.println("save_test :" +board.getId());
    }

    // FINDBYID
    @Test
    public void findById_test(){
        //given
        int id = 1;
        //when
        Optional<Board> boardOP = boardJPARepository.findById(id);

        if (boardOP.isPresent()){
            Board board = boardOP.get();
            System.out.println("findById_test : " + board.getTitle());
        }
        //then
    }

    // FINDBYIDJOINUSER
    @Test
    public void findByIdJoinUser_test(){
        //given
        int id = 1;


        //when
        Optional<Board> boardOP = boardJPARepository.findByIdJoinUser(id);

        //then

    }

    // FINDALL (SORT)
    // h2이용해서
    @Test
    public void findAll_test(){
        //given
        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        //when

        List<Board> boardList = boardJPARepository.findAll();

        //then
        System.out.println("findAll_test : " + boardList);
    }


    // FINDALL (PAGEABLE)


    // DELETEBYID

    @Test
    public void deleteById_test(){
        //given
        int id = 1;

        //when
        boardJPARepository.deleteById(id);
        em.flush();
        // 조회쿼리가 발동
        // 딜리트 쿼리가 보이지 않음
        // 트랜잭션이 종료 되서 쿼리가 보이지 않음
        // 퍼시스턴스 컨텍스트를 사용하면 em.remove를 사용해서 지우면 된다
        // 그러나 엔티티매니저로 직접 쿼리를 만져서 삭제를 시키려면 먼저 조회를 하고 나서 삭제 쿼리를 작성해야 한다.

        //then
    }

}
