package org.effirin.b01.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.effirin.b01.domain.Board;
import org.effirin.b01.dto.BoardListReplyCountDTO;
import org.effirin.b01.dto.BoardListWithImageDTO;

public interface BoardSearch {

    Page<Board> search1(Pageable pageable);

    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

    Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types,
                                                      String keyword,
                                                      Pageable pageable);

    Page<BoardListWithImageDTO> searchWithImage(String[] types,
                                                String keyword,
                                                Pageable pageable);

}
