package shop.mtcoding.blog._core.errors;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.mtcoding.blog._core.errors.exception.*;
import shop.mtcoding.blog._core.utils.ApiUtil;


//RuntimeException이 터지면 해당 파일로 오류가 모인다.
@RestControllerAdvice // 데이터 응답
public class MyExceptionHandler {

    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> ex400(RuntimeException e){
        ApiUtil<?> apiUtil = new ApiUtil<>(400, e.getMessage());

        return new ResponseEntity<>(apiUtil, HttpStatus.BAD_REQUEST); //http body, http header
        //<?> = object 타입
    }

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> ex401(RuntimeException e){
        ApiUtil<?> apiUtil = new ApiUtil<>(401, e.getMessage());
        return new ResponseEntity<>(apiUtil, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> ex403(RuntimeException e){
        ApiUtil<?> apiUtil = new ApiUtil<>(403, e.getMessage());
        return new ResponseEntity<>(apiUtil, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception404.class)
    public ResponseEntity<?> ex404(RuntimeException e){
        ApiUtil<?> apiUtil = new ApiUtil<>(404, e.getMessage());
        return new ResponseEntity<>(apiUtil, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception500.class)
    public ResponseEntity<?> ex500(RuntimeException e){
        ApiUtil<?> apiUtil = new ApiUtil<>(500, e.getMessage());
        return new ResponseEntity<>(apiUtil, HttpStatus.INTERNAL_SERVER_ERROR);
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
