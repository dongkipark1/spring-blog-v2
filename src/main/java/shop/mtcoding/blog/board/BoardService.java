package shop.mtcoding.blog.board;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardJPARepository boardJPARepository;



    public Board 글조회(int boardId){
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글 찾을 수 없음"));

        return board;
    }

    @Transactional
    public Board 글수정(int boardId, int sessionUserId, BoardRequest.UpdateDTO reqDTO){  // 명확하게 만들어주자

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

        return board;
    } // 더티 체킹

    @Transactional
    public Board 글쓰기(BoardRequest.SaveDTO reqDTO, User sessionUser){
        Board board = boardJPARepository.save(reqDTO.toEntity(sessionUser));
        return board;
    }

    @Transactional
    public void 글삭제(Integer boardId, Integer sessionUserId) {
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없음"));

        if (sessionUserId != board.getUser().getId()){
            throw new Exception403("게시글 삭제 권한 없음");
        }

        boardJPARepository.deleteById(boardId);
    }

    public List<BoardResponse.MainDTO> 글목록조회() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Board> boardList = boardJPARepository.findAll(sort);
        return boardList.stream().map(board -> new BoardResponse.MainDTO(board)).toList(); // 지금은 가공이 되지않은 순수한 DB데이터
    } // boardList를 물길에 흘려서 순회하고, 하나씩 집어넣어 BoardResponse.MainDTO로 변환시킨 뒤 리턴해준다


    // board, isBoardOwner
    public BoardResponse.DetailDTO 글상세보기(Integer boardId, User sessionUser) {

        Board board = boardJPARepository.findByIdJoinUser(boardId)
                .orElseThrow(() -> new Exception404("게시글 찾을 수 없음"));
        return new BoardResponse.DetailDTO(board, sessionUser);
    }
}
