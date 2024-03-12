package shop.mtcoding.blog.board;

import lombok.Data;

public class BoardRequest {

    @Data
    public static class SaveDTO{
        private String title;
        private String content;
        private String username;

        public Board toEntity(){
            return new Board(title, content, username);
        } // 나는 DTO로 바꾼 것을 ENTITY로 바꾼다
    }
}
