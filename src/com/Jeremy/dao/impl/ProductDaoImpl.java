package com.Jeremy.dao.impl;

import com.Jeremy.dao.ProductDao;
import com.Jeremy.domain.Product;
import com.Jeremy.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public List<Product> findProductByword(String word) throws SQLException{
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from Product where pname like ? limit 0,8";
        List<Product> list = queryRunner.query(sql,new BeanListHandler<Product>(Product.class),"%"+word+"%");
        return list;

    }

    @Override
    public List<Product> findByHot() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where is_hot = ? and pflag = ? order by pdate desc limit ?";
        List<Product> list = queryRunner.query(sql,new BeanListHandler<Product>(Product.class),1,0,12);
        return list;
    }

    @Override
    public List<Product> findByNew() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where is_hot = ? and pflag = ? order by pdate desc limit ?";
        List<Product> list = queryRunner.query(sql,new BeanListHandler<Product>(Product.class),0,1,12);
        return list;
    }


    @Override
    public Product findById(String pid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where pid = ?";
        Product product = queryRunner.query(sql,new BeanHandler<Product>(Product.class),pid);
        return product;
    }

    //查询某一分类的商品的总数

    @Override
    public int findTotalRecordByCid(String cid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from product where cid = ?";
        Long count = (Long) queryRunner.query(sql,new ScalarHandler(),cid);
        return count.intValue();
    }

    //分页查询

    @Override
    public List<Product> findAllByCid(String cid, int startIndex, int pageSize) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where cid = ? order by pdate desc limit ?,?";
        List<Product> list = queryRunner.query(sql ,new BeanListHandler<Product>(Product.class),cid,startIndex,pageSize);
        return list;
    }


    @Override
    public int findTotalRecord() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from product";
        Long count = (Long) queryRunner.query(sql,new ScalarHandler());
        return count.intValue();
    }

    @Override
    public List<Product> findAll(int startIndex, int pageSize) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where pflag = ? order by pdate desc limit ?,?";
        List<Product> list = queryRunner.query(sql ,new BeanListHandler<Product>(Product.class),0,startIndex,pageSize);
        return list;
    }

    @Override
    public int productDelete(String pid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update orderitem set pid = null where pid = ?";
        queryRunner.update(sql,pid);

        sql = "delete from product where pid = ?";
        return queryRunner.update(sql,pid);
    }

    @Override
    public int productAdd(Product product) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into product values (?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {product.getPid(),product.getPname(),product.getMarket_price(),
                product.getShop_price(),product.getPimage(),product.getPdate(),
                product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCid()};
        return queryRunner.update(sql,params);
    }

    @Override
    public int findTotalRecordByWord(String word) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from product where pname like ?";
        Long count = (Long) queryRunner.query(sql,new ScalarHandler(),"%"+word+"%");
        return count.intValue();
    }

    @Override
    public List<Product> findAllByWord(String word, int startIndex, int pageSize) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where pname like ? order by pdate desc limit ?,?";
        List<Product> list = queryRunner.query(sql ,new BeanListHandler<Product>(Product.class),"%"+word+"%",startIndex,pageSize);
        return list;
    }
}
