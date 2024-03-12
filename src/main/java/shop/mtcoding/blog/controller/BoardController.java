package shop.mtcoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class BoardController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/board/save-form")
    public String saveForm() {
        return "save-form";
    }

    // 래핑 클래스를 쓰면 null이 들어오는 지 확인이 가능함(null 처리가 편함)
    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id) {
        return "board/detail";
    }
}
