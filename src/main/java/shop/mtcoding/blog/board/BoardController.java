package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Controller // new BoardController(IoC에서 BoardRepository를 찾아서 주입) -> IoC 컨테이너 등록
public class BoardController {

    private final BoardRepository boardRepository;
    private final HttpSession session;

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, BoardRequest.UpdateDTO reqDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardRepository.findById(id);

        if (sessionUser.getId() != board.getUser().getId()){
            throw new Exception403("게시글 수정 권한 없음");
        }

        boardRepository.updateById(id, reqDTO.getTitle(), reqDTO.getContent());
        return "redirect:/board/" + id;
    }



    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id){
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardRepository.findById(id);

        if (sessionUser.getId() != board.getUser().getId()){
            throw new Exception403("게시글 삭제 권한 없음");
        }

        boardRepository.deleteById(id);
        return "redirect:/";
    }


    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO reqDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardRepository.save(reqDTO.toEntity(sessionUser));

        return "redirect:/";
    }

    @GetMapping("/")
    public String index(HttpServletRequest request) {  //index(Model model)model이란 객체를 담는다. 실질적으로 request model내부에 request가 있다
        List<Board> boardList = boardRepository.findAll();
        request.setAttribute("boardList", boardList);
        return "index";
    }

    @GetMapping("/board/save-form")
    public String saveForm() {
        return "/board/save-form";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) {

        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardRepository.findByIdJoinUser(id);

        // 게시글의 주인이 아니라면 -> 로그인을 했으면 ->
        // 로그인 한 유저가 = 게시글 쓴 사람과 일치하면 ->
        // 주인이 맞다 -> else면 아니다
        boolean isOwner = false;
        if (sessionUser != null){
            if (sessionUser.getId() == board.getUser().getId()){
                isOwner = true;
            }
        }
        request.setAttribute("isOwner", isOwner);
        request.setAttribute("board", board);
        return "board/detail";
    }
}

