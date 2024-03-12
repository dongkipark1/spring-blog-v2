package shop.mtcoding.blog.board;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.util.MyDateUtil;


import java.sql.Timestamp;

@NoArgsConstructor // 디폴트 생성자는 무조건 있어야 한다.
@Data
@Table(name = "board_tb")
@Entity
public class Board {
    //모델링
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private String username;

    @CreationTimestamp // pc -> DB (날짜가 주입된다)
    private Timestamp createdAt;

    public Board(String title, String content, String username) {
        this.title = title;
        this.content = content;
        this.username = username;
    }

    public String getTime(){
        return MyDateUtil.timestampFormat(createdAt);
    }
}
