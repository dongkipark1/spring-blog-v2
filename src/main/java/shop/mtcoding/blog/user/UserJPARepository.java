package shop.mtcoding.blog.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


//자동 COMPONENT SCAN이 된다.
//컴포넌트 스캔으로 NEW 하려고 했으나 INTERFACE라서 NEW가 불가능
//findByUsername
// 카멜로 꺾이는 것을 보고 만들어 준다

public interface UserJPARepository extends JpaRepository<User, Integer> {

    // 쿼리 메서드 추천하지 않는다, 직접 적는게 낫다
    // 간단한 쿼리는 JPQL로 JPA레포지토리
    // 복잡한 것은 쿼리레포지토리
//    @Query("SELECT u from User u where u.username = :username and u.password = :password ")
    Optional<User> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    Optional<User> findByUsername(@Param("username") String username);
    // NULL 체크 가능
}
