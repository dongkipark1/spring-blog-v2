package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Controller // new BoardController(IoC에서 BoardRepository를 찾아서 주입) -> IoC 컨테이너 등록
public class BoardController {

    private final BoardRepository boardRepository;
    private final HttpSession session;

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, BoardRequest.UpdateDTO reqDTO){
        boardRepository.updateById(id, reqDTO.getTitle(), reqDTO.getContent());
        return "redirect:/board/" + id;
    }

    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request){
        Board board = boardRepository.findById(id);
        request.setAttribute("board", board);
        return "board/update-form";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id){
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
        Board board = boardRepository.findByIdJoinUser(id);
        request.setAttribute("board", board);
        return "board/detail";
    }
}
