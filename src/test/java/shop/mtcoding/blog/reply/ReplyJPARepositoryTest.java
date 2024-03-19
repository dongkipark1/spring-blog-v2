package shop.mtcoding.blog.reply;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/*
    1. One관계는 join Many관계는 조회를 한 번 더 한다 -> DTO에 담는다.
    2. Many 관계를 양방향 매핑하기
 */
@DataJpaTest
public class ReplyJPARepositoryTest {

    @Autowired
    private ReplyJPARepository replyJPARepository;

}
