
package com.standout.sopang.springex.mapper;

import com.standout.sopang.springex.domain.TodoVO;
import com.standout.sopang.springex.dto.PageRequestDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoMapper {

    void insert(TodoVO todoVO);

    List<TodoVO> selectAll();

    TodoVO selectOne(Long tno);

    void delete(Long tno);

    void update(TodoVO todoVO);

    List<TodoVO> selectList(PageRequestDTO pageRequestDTO);

    int getCount(PageRequestDTO pageRequestDTO);
}

