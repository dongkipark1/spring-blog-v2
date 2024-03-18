package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shop.mtcoding.blog.board.BoardRepository;

@Import(UserRepository.class) // IoC에 등록 해주는 것 (등록 코드) UserRepository를 쓰는 것은 내가 썼다는 것을 알리려고
@DataJpaTest //DataSource(connection pool), EntityManager 실제로는 퍼시스턴트 컨텍스트를 띄움, 핵심은 필요한 것만 메모리에 띄우겠다.
public class UserRepositoryTest {

    @Autowired //DI
    private UserRepository userRepository; // new가 되지 않기 때문에 final을 쓰지 않는다.

    @Autowired
    private EntityManager em;



    @Test
    public void updateById_test(){
        //given
        int id = 1;
        String password = "123456";
        String email = "kim@nate.com";
        //when
        userRepository.updateById(id, password, email);
        em.flush();

        //then
    }

    @Test
    public void findById_test(){
        //given
        int id = 1;
        //when
        userRepository.findById(id);
        //then
    }

    @Test
    public void findByUsername_test(){
        //given
        UserRequest.LoginDTO reqDTO = new UserRequest.LoginDTO();
        reqDTO.setUsername("ssar");
        reqDTO.setPassword("1234");
        //when
        User user = userRepository.findByUsernameAndPassword(reqDTO.getUsername(), reqDTO.getPassword());

        //then
        Assertions.assertThat(user.getUsername()).isEqualTo("ssar");
    }
}
