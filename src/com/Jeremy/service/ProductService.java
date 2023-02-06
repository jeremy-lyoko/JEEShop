package com.Jeremy.service;

import com.Jeremy.domain.PageBean;
import com.Jeremy.domain.Product;

import java.sql.SQLException;
import java.util.List;
//对商品业务的处理
public interface ProductService {
    List<Product> findProductByword(String word) throws SQLException;

    List<Product> findByHOt() throws SQLException;

    List<Product> findByNew() throws SQLException;

    Product findById(String pid) throws SQLException;

    PageBean<Product> findByCid(String cid, int pageNumber, int pageSize) throws SQLException;

    PageBean<Product> findByWord(String word, int pageNumber, int pageSize) throws SQLException;

    PageBean<Product> findAll(int pageNumber, int pageSize) throws SQLException;

    int productDelete(String pid) throws SQLException;

    int productAdd(Product product) throws SQLException;
}
