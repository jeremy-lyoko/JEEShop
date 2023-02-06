package com.Jeremy.dao.impl;

import com.Jeremy.dao.CategoryDao;
import com.Jeremy.domain.Category;
import com.Jeremy.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

//购物车操作实现类
public class CategoryDaoImpl implements CategoryDao {
    @Override
    public List<Category> findAll() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from category";
        return queryRunner.query(sql, new BeanListHandler<Category>(Category.class));

    }

    @Override
    public int remove(String cid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update product set cid = null where cid = ?";
        queryRunner.update(sql, cid);

        sql = "delete from category where cid = ?";
        return queryRunner.update(sql, cid);
    }

    @Override
    public int add(String cid, String cname) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into category values(?,?)";
        Object[] params = {cid, cname};
        return queryRunner.update(sql, params);
    }

    @Override
    public int update(String cid, String cname) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update category set cname = ? where cid = ?";
        return queryRunner.update(sql, cname, cid);
    }
}
