package com.standout.sopang.springex.service;

import com.standout.sopang.springex.domain.TodoVO;
import com.standout.sopang.springex.dto.PageRequestDTO;
import com.standout.sopang.springex.dto.PageResponseDTO;
import com.standout.sopang.springex.dto.TodoDTO;
import com.standout.sopang.springex.mapper.TodoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    @Autowired
    private final TodoMapper todoMapper;

    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public void register(TodoDTO todoDTO) {

        log.info(modelMapper);

        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class );

        log.info(todoVO);

        todoMapper.insert(todoVO);

    }

    @Override
    public TodoDTO getOne(Long tno) {

        TodoVO todoVO = todoMapper.selectOne(tno);

        TodoDTO todoDTO = modelMapper.map(todoVO, TodoDTO.class);

        return todoDTO;
    }

    @Override
    public void remove(Long tno) {

        todoMapper.delete(tno);

    }

    @Override
    public void modify(TodoDTO todoDTO) {

        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class );

        todoMapper.update(todoVO);

    }

    @Override
    public PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO) {


        List<TodoVO> voList = todoMapper.selectList(pageRequestDTO);
        List<TodoDTO> dtoList = voList.stream()
                .map(vo -> modelMapper.map(vo, TodoDTO.class))
                .collect(Collectors.toList());

        int total = todoMapper.getCount(pageRequestDTO);

        PageResponseDTO<TodoDTO> pageResponseDTO = PageResponseDTO.<TodoDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();

        return pageResponseDTO;

    }


}
