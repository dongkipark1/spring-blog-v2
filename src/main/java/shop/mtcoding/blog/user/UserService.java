package shop.mtcoding.blog.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception400;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.errors.exception.Exception404;

import java.util.Optional;

@RequiredArgsConstructor
@Service // IoC 등록
public class UserService {

    private final UserJPARepository userJPARepository; // DI

    @Transactional
    public User 회원수정(int id, UserRequest.UpdateDTO reqDTO){
        User user = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("회원정보를 찾을 수 없음"));

        user.setPassword(reqDTO.getPassword());
        user.setEmail(reqDTO.getEmail());

        return user;
    }

    public User 회원수정폼(int id){
       User user = userJPARepository.findById(id)
               .orElseThrow(() -> new Exception404("회원정보를 찾을 수 없음")); // 예외처리
       return user;
    }

    public User 로그인(UserRequest.LoginDTO reqDTO){
        // 해시검사 비교

        User sessionUser = userJPARepository.findByUsernameAndPassword(reqDTO.getUsername(), reqDTO.getPassword())
                .orElseThrow(() -> new Exception401("인증되지 않았습니다.")); // ex) ssar, 12345를 넣으면 옵셔널에 null이 뜬다 그래서 값이 null이면 orElse로 throw를 날린다.
        return sessionUser;
    }

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
