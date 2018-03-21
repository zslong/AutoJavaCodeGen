package me.autojava.dao;

import me.autojava.model.DcsField;
import me.autojava.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by shilong.zhang on 2018/2/2.
 */

@Component
public class DcsFieldDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<DcsField> getAllFields() {
        String sql = "SELECT fid, name, description, editable FROM dcs_field_index WHERE status=1";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            DcsField dcsField = new DcsField();
            dcsField.setFid(resultSet.getInt("fid"));
            dcsField.setDescription(resultSet.getString("description"));
            dcsField.setEditable(resultSet.getInt("editable") == 1);
            dcsField.setName(resultSet.getString("name"));
            dcsField.setNeedCompare(false);
            return dcsField;
        });
    }

    public List<DcsField> getProductFields(Product product) {
        String sql = "SELECT dfi.fid, dfi.name, dfi.description, dfi.editable, dpf.compare from dcs_field_index dfi " +
                "inner join dcs_product_field dpf on dfi.fid=dpf.field_index_id and dpf.product_id=? and dfi.status=1 and dpf.status=1";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            DcsField dcsField = new DcsField();
            dcsField.setFid(resultSet.getInt("fid"));
            dcsField.setDescription(resultSet.getString("description"));
            dcsField.setEditable(resultSet.getInt("editable") == 1);
            dcsField.setName(resultSet.getString("name"));
            dcsField.setNeedCompare(resultSet.getInt("compare") == 1);
            return dcsField;
        }, product.getOrdinal());
    }

}
