package org.effirin.b01.service;

import org.effirin.b01.domain.Board;
import org.effirin.b01.dto.*;

import java.util.stream.Collectors;

public interface BoardService {

    Long register(BoardDTO boardDTO);

    BoardDTO readOne(Long bno);

    void modify(BoardDTO boardDTO);

    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

    //댓글의 숫자까지 처리
    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);

    //댓글의 숫자와 이미지 처리
    PageResponseDTO<BoardListWithImageDTO> listWithImageDTO(PageRequestDTO pageRequestDTO);


    // default 메소드 2개 -> dto To Entity / entity To Dto
    default Board dtoToEntity(BoardDTO boardDTO){

        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())

                .build();

        if(boardDTO.getFileList() != null && boardDTO.getFileList().size() > 0) {
            boardDTO.getFileList().forEach(imgLink -> board.addImage(imgLink));
        } // modelmapper 단순한 테이블 하나짜리 처리할 땐 괜찮은데 이런 작업은 에러가 자주 남
        // board에 이미지 링크로 이미지를 저장함

        return board;
    }

    default BoardDTO entityToDto(Board board) {

        BoardDTO dto = BoardDTO.builder()
                .bno(board.getBno())
                .title((board.getTitle()))
                .content(board.getContent())
                .writer(board.getWriter())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();

        if(board.getImgSet() != null && board.getImgSet().size() > 0) {
            dto.setFileList(
                    board.getImgSet().stream().map(boardImage -> boardImage.getFileLink())
                            .collect(Collectors.toList()));
        }// 문자열로 바꿔서 타임리프에 전달해주면 루프돌면서 뿌린다.

        return dto;
    }

}
