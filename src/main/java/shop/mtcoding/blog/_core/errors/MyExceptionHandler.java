package shop.mtcoding.blog._core.errors;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import shop.mtcoding.blog._core.errors.exception.*;

//RuntimeException이 터지면 해당 파일로 오류가 모인다.
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(Exception400.class)
    public String ex400(RuntimeException e , HttpServletRequest request){
        request.setAttribute("msg", e.getMessage());
        return "err/400";
    }

    @ExceptionHandler(Exception401.class)
    public String ex401(RuntimeException e , HttpServletRequest request){
        request.setAttribute("msg", e.getMessage());
        return "err/401";
    }

    @ExceptionHandler(Exception403.class)
    public String ex403(RuntimeException e , HttpServletRequest request){
        request.setAttribute("msg", e.getMessage());
        return "err/403";
    }

    @ExceptionHandler(Exception404.class)
    public String ex404(RuntimeException e , HttpServletRequest request){
        request.setAttribute("msg", e.getMessage());
        return "err/404";
    }

    @ExceptionHandler(Exception500.class)
    public String ex500(RuntimeException e , HttpServletRequest request){
        request.setAttribute("msg", e.getMessage());
        return "err/500";
    }

    // nullpointerException이 뜨면 여기로 온다
    // ClassCastingException 여기로
    // 실무에서는 사용해야
    // DB에러 로그도 남겨야 하고
    // 관리자에게 문자도 날려주고
    // 담당자에게 이메일도 보내줘야 한다.
//    @ExceptionHandler(Exception.class)
//    public String exUnknown(RuntimeException e){
//        return "err/unknown";
//    }
}
