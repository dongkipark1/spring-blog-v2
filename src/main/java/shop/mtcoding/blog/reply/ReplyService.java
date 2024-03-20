package shop.mtcoding.blog.reply;


import jakarta.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.board.Board;
import shop.mtcoding.blog.board.BoardJPARepository;
import shop.mtcoding.blog.user.User;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final BoardJPARepository boardJPARepository;
    private final ReplyJPARepository replyJPARepository;

    @Transactional
    public Reply 댓글쓰기(ReplyRequest.SaveDTO reqDTO, User sessionUser) {
        Board board = boardJPARepository.findById(reqDTO.getBoardId())
                        .orElseThrow(() -> new Exception404("없는 게시글에 댓글 작성 안됨"));

        Reply reply = reqDTO.toEntity(sessionUser, board);

        return replyJPARepository.save(reply);
    }

    @Transactional
    public void 댓글삭제(Integer replyId, Integer sessionUserId) {
        Reply reply = replyJPARepository.findById(replyId)
                .orElseThrow(() -> new Exception404("댓글을 찾을 수 없음"));

        if (sessionUserId != reply.getUser().getId()){
            throw new Exception403("댓글 삭제 권한 없음");
        }

        replyJPARepository.deleteById(replyId);
    }

}
