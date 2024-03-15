package shop.mtcoding.blog.board;

import lombok.Data;
import shop.mtcoding.blog.user.User;

public class BoardRequest {

    @Data
    public static class UpdateDTO{
        private String title;
        private String content;

    }

    @Data
    public static class SaveDTO{
        private String title;
        private String content;

        // 빌드 패턴,  언제 사용하는가? DTO를 클라이언트로 부터 받아서, PC(퍼시스트 컨텍스트)에 전달하기 위해 사용
        public Board toEntity(User user){
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .build();
            //.user(User.builder().id(1).build()) 위험한 코드 1번이 없을 수가 있어서
            // session 넣어도 괜찮다 user객체는 반드시 존재하기 때문에
        }
    }
}
