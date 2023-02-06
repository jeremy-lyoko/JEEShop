package com.Jeremy.service.impl;

import com.Jeremy.dao.CategoryDao;
import com.Jeremy.dao.impl.CategoryDaoImpl;
import com.Jeremy.domain.Category;
import com.Jeremy.service.CategoryService;

import java.sql.SQLException;
import java.util.List;

//购物车相关业务实现类
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() throws SQLException {
        List<Category> list = categoryDao.findAll();
        return list;
    }

    @Override
    public int remove(String cid) throws SQLException {
        return categoryDao.remove(cid);
    }

    @Override
    public int add(String cid, String cname) throws SQLException {
        return categoryDao.add(cid, cname);
    }

    @Override
    public int update(String cid, String cname) throws SQLException {
        return categoryDao.update(cid, cname);
    }
}
