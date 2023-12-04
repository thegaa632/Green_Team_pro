package com.standout.sopang.springex.mapper;

import com.standout.sopang.springex.domain.TodoVO;
import com.standout.sopang.springex.dto.PageRequestDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TodoMapperlmpl implements TodoMapper {

        @Autowired
        SqlSession sqlSession;

        @Override
        public void insert(TodoVO todoVO) {
          sqlSession.insert("mapper.TodoMapper",todoVO);

        }
        @Override
        public List<TodoVO> selectAll() {
            List<TodoVO> voList= sqlSession.selectList("mapper.TodoMapper.insert");
            return voList;
        }

        @Override
        public TodoVO selectOne(Long tno) {
            TodoVO result  = sqlSession.selectOne("mapper.TodoMapper.selectAll",tno);
            return result;
        }

        @Override
        public void delete(Long tno) {
            sqlSession.delete("mapper.TodoMapper.delete",tno);

        }

        @Override
        public void update(TodoVO todoVO) {
            sqlSession.update("mapper.TodoMapper.update",todoVO);
        }

        @Override

        public List<TodoVO> selectList(PageRequestDTO pageRequestDTO) {
            List<TodoVO> voList =  sqlSession.selectList("mapper.TodoMapper.selectList",pageRequestDTO);
            return voList;
        }

        @Override
        public int getCount(PageRequestDTO pageRequestDTO) {
            int result =sqlSession.selectOne("mapper.TodoMapper.getCount",pageRequestDTO);
            return result;
        }
    }
