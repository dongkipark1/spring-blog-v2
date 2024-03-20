package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog._core.utils.ApiUtil;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@RestController // new BoardController(IoC에서 BoardRepository를 찾아서 주입) -> IoC 컨테이너 등록
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;

    //TODO: 글목록 조회 API 필요 -> @GetMapping("/")
    @GetMapping("/")
    public ResponseEntity<?> main(){
        List<BoardResponse.MainDTO> respDTO = boardService.글목록조회();
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    //TODO: 글 조회 API 작성 필요 -> @GetMapping("/api/boards/{id}")
    @GetMapping("/api/boards/{id}")
    public ResponseEntity<?> findOne(@PathVariable Integer id){
        Board board = boardService.글조회(id);
        return ResponseEntity.ok(new ApiUtil<>(board));
    }

    //TODO: 글 상세보기 API 작성 필요 -> @GetMapping("/api/boards/{id}/detail")
    @GetMapping("/api/boards/{id}/detail")
    public ResponseEntity<?> detail(@PathVariable Integer id){
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.글상세보기(id,sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(board));
    }



    @PutMapping("/api/board/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody BoardRequest.UpdateDTO reqDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.글수정(id, sessionUser.getId(), reqDTO);
        return ResponseEntity.ok(new ApiUtil<>(board));
    }

    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.글삭제(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(null));
    }

    @PostMapping("/api/boards")
    public ResponseEntity<?> save(@RequestBody BoardRequest.SaveDTO reqDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.글쓰기(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(board));

        //return entity가 위험한 이유 : json으로 변환할 때 레이지로딩이 걸려서 무한 순환 참조가 된다
        //즉 return에 DTO를 넘기면 된다.
    }
    //view는 서버사이드렌더링 할 때 필요한 것들만 화면에 만들어서 주기 때문에 DTO만들 필요없다.

}

