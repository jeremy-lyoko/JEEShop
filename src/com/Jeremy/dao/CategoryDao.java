package com.Jeremy.dao;

import com.Jeremy.domain.Category;

import java.sql.SQLException;
import java.util.List;

//购物车操作
//增删改查
public interface CategoryDao {
    List<Category> findAll() throws SQLException;

    int remove(String cid) throws SQLException;

    int add(String cid, String cname) throws SQLException;

    int update(String cid, String cname) throws SQLException;

}
