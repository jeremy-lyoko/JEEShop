package com.Jeremy.service.impl;

import com.Jeremy.dao.ProductDao;
import com.Jeremy.dao.impl.ProductDaoImpl;
import com.Jeremy.domain.PageBean;
import com.Jeremy.domain.Product;
import com.Jeremy.service.ProductService;

import java.sql.SQLException;
import java.util.List;

//商品业务处理的实现类
public class ProductServiceImpl implements ProductService {
    ProductDao productDao = new ProductDaoImpl();

    @Override
    public List<Product> findProductByword(String word) throws SQLException {
        return productDao.findProductByword(word);
    }

    @Override
    public List<Product> findByHOt() throws SQLException {
        List<Product> list = productDao.findByHot();
        return list;
    }

    @Override
    public List<Product> findByNew() throws SQLException {
        List<Product> list = productDao.findByNew();
        return list;
    }

    @Override
    public Product findById(String pid) throws SQLException {
        Product product = productDao.findById(pid);
        return product;
    }

    @Override
    public PageBean<Product> findByCid(String cid, int pageNumber, int pageSize) throws SQLException {
        //先查询此类别的商品的总数
        int totalRecord = productDao.findTotalRecordByCid(cid);
        //分页查询
        PageBean<Product> pageBean = new PageBean<Product>(pageNumber, pageSize, totalRecord);

        List<Product> data = productDao.findAllByCid(cid, pageBean.getStartIndex(), pageBean.getPageSize());

        pageBean.setData(data);

        return pageBean;

    }

    @Override
    public PageBean<Product> findByWord(String word, int pageNumber, int pageSize) throws SQLException {
        int totalRecord = productDao.findTotalRecordByWord(word);
        //同样是先查询商品的总数 利用page的构造求起始页码
        // 进数据库查数据集合传递到page
        PageBean<Product> pageBean = new PageBean<Product>(pageNumber, pageSize, totalRecord);

        List<Product> data = productDao.findAllByWord(word, pageBean.getStartIndex(), pageBean.getPageSize());

        pageBean.setData(data);

        return pageBean;
    }

    @Override
    public PageBean<Product> findAll(int pageNumber, int pageSize) throws SQLException {
        int totalRecord = productDao.findTotalRecord();

        PageBean<Product> pageBean = new PageBean<Product>(pageNumber, pageSize, totalRecord);

        List<Product> data = productDao.findAll(pageBean.getStartIndex(), pageBean.getPageSize());

        pageBean.setData(data);

        return pageBean;
    }

    @Override
    public int productDelete(String pid) throws SQLException {
        return productDao.productDelete(pid);
    }

    @Override
    public int productAdd(Product product) throws SQLException {
        return productDao.productAdd(product);
    }
}
