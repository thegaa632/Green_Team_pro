package com.standout.sopang.springex.service;

import com.standout.sopang.springex.dto.PageRequestDTO;
import com.standout.sopang.springex.dto.PageResponseDTO;
import com.standout.sopang.springex.dto.TodoDTO;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public interface TodoService {

    void register(TodoDTO todoDTO);

    PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO);

    TodoDTO getOne(Long tno);

    void remove(Long tno);

    void modify(TodoDTO todoDTO);
}

