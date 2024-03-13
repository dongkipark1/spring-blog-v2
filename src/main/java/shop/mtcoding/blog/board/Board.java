package shop.mtcoding.blog.board;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.user.User;
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

//    @JoinColumn(name = "user_id")
//    @ManyToOne
//    private User user; // MANY = BOARD, ONE = USER  pk를 만들어 줄게 user_id

    @CreationTimestamp // pc -> DB (날짜가 주입된다)
    private Timestamp createdAt;

}
