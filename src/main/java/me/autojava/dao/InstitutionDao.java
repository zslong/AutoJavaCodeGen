package me.autojava.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by shilong.zhang on 2018/1/23.
 */

@Component
public class InstitutionDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> queryBaseUsers() {
        return jdbcTemplate.queryForList("SELECT username from base_user");
    }
}
