package shop.mtcoding.blog.board;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.user.User;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardJPARepository boardJPARepository;



    public Board 게시글수정폼(int boardId, int sessionUserId){
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글 찾을 수 없음"));

        // 글 수정하러 갈 때도 권한이 필요하다 즉 권한 없이 페이지를 이동하게 해서도 안 됨
        if (sessionUserId != board.getUser().getId()){
            throw new Exception403("게시글 수정페이지로 이동 할 권한 없음");
        }

        return board;
    }

    @Transactional
    public void 글수정(int boardId, int sessionUserId, BoardRequest.UpdateDTO reqDTO){  // 명확하게 만들어주자

        // 조회, 예외처리
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없음"));

        // 권한 처리
        if (sessionUserId != board.getUser().getId()){
            throw new Exception403("게시글 수정 권한 없음");
        }

        // 글 수정
        board.setTitle(reqDTO.getTitle());
        board.setContent(reqDTO.getContent());
    } // 더티 체킹

    @Transactional
    public void 글쓰기(BoardRequest.SaveDTO reqDTO, User sessionUser){
        boardJPARepository.save(reqDTO.toEntity(sessionUser));
    }
}
