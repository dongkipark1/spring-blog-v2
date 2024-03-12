package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardNativeRepository boardNativeRepository;

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, String title, String content, String username){
//        System.out.println("id: " + id);
//        System.out.println("title: " + title);
//        System.out.println("content: " + content);
//        System.out.println("username: " + username);



        return "redirect:/board/" + id;
    }

    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request){
        Board board = boardNativeRepository.findById(id);
        request.setAttribute("board", board);
        return "board/update-form";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id){
        boardNativeRepository.deleteById(id);
        return "redirect:/";
    }


    @PostMapping("/board/save")
    public String save(String title, String content, String username){
        boardNativeRepository.save(title,content,username);
        return "redirect:/";
    }

    @GetMapping("/")
    public String index(HttpServletRequest request) {  //index(Model model)model이란 객체를 담는다. 실질적으로 request model내부에 request가 있다
        // request에 담으면 버릴 수가 있다. session은 메모리가 남기 때문에 용량을 차지한다.


        // 실제로는 화면에 필요한 정보만을 줘야 한다(id, title) 프론트엔드가 일하기 힘들다.
        List<Board> boardList = boardNativeRepository.findAll();
        request.setAttribute("boardList", boardList); // 꺼내 볼 수 있기 때문에 request에 담는다

        return "index";
    }

    @GetMapping("/board/save-form")
    public String saveForm() {
        return "/board/save-form";
    }

    // 래핑 클래스를 쓰면 null이 들어오는 지 확인이 가능함(null 처리가 편함)
    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) {

        Board board = boardNativeRepository.findById(id);
        request.setAttribute("board", board);

        return "board/detail";
    }
}
