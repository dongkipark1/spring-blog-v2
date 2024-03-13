package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id){
        return "redirect:/board/" + id;
    }

    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request){
        return "board/update-form";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id){
        return "redirect:/";
    }


    @PostMapping("/board/save")
    public String save(){
        return "redirect:/";
    }

    @GetMapping("/")
    public String index(HttpServletRequest request) {  //index(Model model)model이란 객체를 담는다. 실질적으로 request model내부에 request가 있다
        return "index";
    }

    @GetMapping("/board/save-form")
    public String saveForm() {
        return "/board/save-form";
    }

    // 래핑 클래스를 쓰면 null이 들어오는 지 확인이 가능함(null 처리가 편함)
    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) {

        return "board/detail";
    }
}
