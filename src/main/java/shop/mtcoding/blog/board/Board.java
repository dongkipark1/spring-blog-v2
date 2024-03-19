package shop.mtcoding.blog.board;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.reply.Reply;
import shop.mtcoding.blog.user.User;
import shop.mtcoding.blog.util.MyDateUtil;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    // LAZY - 필요한 것만 가져옴, EAGER - 모두 JOIN이 일어난다
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // MANY = BOARD, ONE = USER  pk를 만들어 줄게 user_id // ORM

    @CreationTimestamp // pc -> DB (날짜가 주입된다)
    private Timestamp createdAt;

    // 조회할 때 담는 용도로만 쓴다.
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY) //Entity 객체의 변수명 = foreign key의 주인
    private List<Reply> replies = new ArrayList<>();
    // ManyToOne은 디폴트가 EAGER이 기본전략, OneToMany는 디폴트가 LAZY가 기본전략

    @Transient // 테이블 생성이 안됨
    private boolean isOwner;

    @Builder
    public Board(Integer id, String title, String content, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }

    public void update(){  //의미있는 메서드를 만들자

    }
}
