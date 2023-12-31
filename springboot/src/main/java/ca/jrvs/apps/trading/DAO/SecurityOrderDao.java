package ca.jrvs.apps.trading.DAO;

import ca.jrvs.apps.trading.Models.domain.SecurityOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder> {
    private static final Logger logger = LoggerFactory.getLogger(SecurityOrder.class);
    private final String TABLE_NAME = "security_order";
    private final String ID_COLUMN = "id";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public SecurityOrderDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID_COLUMN);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleJdbcInsert;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdColumnName() {
        return ID_COLUMN;
    }

    @Override
    Class<SecurityOrder> getEntityClass() {
        return SecurityOrder.class;
    }
    public void deleteByAccountId(Integer id) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE account_id=?";
        getJdbcTemplate().update(query, id);
    }
    @Override
    public int updateOne(SecurityOrder entity) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends SecurityOrder> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(SecurityOrder securityOrder) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends SecurityOrder> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }
}