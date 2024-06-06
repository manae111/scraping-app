package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Item;

/**
 * リポジトリクラス
 */
@Repository
public class ScrapingRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs,i) -> {
        Item item = new Item();
        item.setId(rs.getInt("id"));
        item.setUrl(rs.getString("url"));
        item.setItemName(rs.getString("item_name"));
        item.setPriceOriginal(rs.getInt("price_original"));
        item.setPriceLatest(rs.getInt("price_latest"));
        item.setUserId(rs.getInt("user_id"));
        return item;
    };

    private static final RowMapper<String> URL_ROW_MAPPER = (rs,i) -> {
        String url = rs.getString("url");
        return url;
    };


    /**
     * 商品情報を挿入するメソッド
     * @param item
     */
    public void insert(Item item) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(item);
        String sql = "INSERT INTO items (url, item_name, price_original, price_latest, user_id) VALUES (:url, :itemName, :priceOriginal, :priceLatest, :userId)";
        template.update(sql, param);
    }

    /**
     * 最新価格を更新するメソッド
     * @param item
     * @return
     */
    public void update(String url ,Integer price) {
        String sql = "UPDATE items SET price_latest = :priceLatest WHERE URL = :url";
        SqlParameterSource param = new MapSqlParameterSource().addValue("priceLatest", price).addValue("url", url);
        int updatedRows = template.update(sql, param);
        System.out.println(updatedRows + "件更新しました");
    }

    /**
     * バッチ処理のためにDBのURLを全件取得するメソッド
     */
    public List<String> findAllUrl() {
        String sql = "SELECT url FROM items";
        List<String> urlList = template.query(sql, URL_ROW_MAPPER);
        return urlList;
    }

    /**
     * URLからpriceOriginalを取得するメソッド
     */
    public Integer findPriceOriginal(String url) {
        String sql = "SELECT price_original FROM items WHERE url = :url";
        SqlParameterSource param = new MapSqlParameterSource().addValue("url", url);
        Integer priceOriginal = template.queryForObject(sql, param, Integer.class);
        return priceOriginal;
    }

    /**
     * 定時送信メールのためにitemsテーブルの情報を全件取得するメソッド
     */
    public List<Item> findAll() {
        String sql = "SELECT id, url, item_name, price_original, price_latest, user_id FROM items";
        List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
        return itemList;
    }
}
