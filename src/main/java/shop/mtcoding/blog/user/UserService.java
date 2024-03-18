package shop.mtcoding.blog.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception400;

import java.util.Optional;

@RequiredArgsConstructor
@Service // IoC 등록
public class UserService {

    private final UserJPARepository userJPARepository; // DI

    //회원 가입
    @Transactional
    public void 회원가입(UserRequest.JoinDTO reqDTO){ // 서비스명 // ssar이 들어옴
        // 기능
        // 1. 유효성 검사 ( 컨트롤러 책임)

        // 2. 유저네임 중복검사 (서비스 체크) - DB연결 필요
        Optional<User> userOP = userJPARepository.findByUsername(reqDTO.getUsername());

        if (userOP.isPresent()){ //아이디가 중복(현재 존재 한다)이 되었다라는 뜻
            throw new Exception400("중복된 아이디야");
        }

        // 아이디가 중복되지 않으면 이쪽으로 넘어 온다
        userJPARepository.save(reqDTO.toEntity());

    }
}
