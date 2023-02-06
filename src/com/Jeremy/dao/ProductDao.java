package com.Jeremy.dao;

import com.Jeremy.domain.Product;

import java.sql.SQLException;
import java.util.List;

//商品数据的操作
public interface ProductDao {
    List<Product> findProductByword(String word) throws SQLException;

    List<Product> findByHot() throws SQLException;

    List<Product> findByNew() throws SQLException;

    Product findById(String pid) throws SQLException;

    int findTotalRecordByCid(String cid) throws SQLException;

    List<Product> findAllByCid(String cid, int startIndex, int pageSize) throws SQLException;

    int findTotalRecord() throws SQLException;

    List<Product> findAll(int startIndex, int pageSize) throws SQLException;

    int productDelete(String pid) throws SQLException;

    int productAdd(Product product) throws SQLException;

    int findTotalRecordByWord(String word) throws SQLException;

    List<Product> findAllByWord(String word, int startIndex, int pageSize) throws SQLException;

}
