package ca.jrvs.apps.trading.DAO;
import ca.jrvs.apps.trading.Models.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class AccountDao extends JdbcCrudDao<Account> {
    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);
    private final String TABLE_NAME = "account";
    private final String ID_COLUMN = "id";
    private final String TRADER_ID_COLUMN = "trader_id";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public AccountDao(DataSource dataSource){
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
    Class<Account> getEntityClass() {
        return Account.class;
    }

    public Account findAccountByTraderId(Integer traderId){
        String selectSql = "SELECT * FROM " + getTableName() + " WHERE trader_id=?";
        Account account = null;
        try {
            account = getJdbcTemplate().queryForObject(selectSql,
                    BeanPropertyRowMapper.newInstance(getEntityClass()), traderId);
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.debug("Cannot find account associated with trader id: " + traderId);
        }
        return account;
    }

    public void updateAmountById(Integer id, Double amount){
        String query = "UPDATE " + getTableName() + " SET amount =? " + "WHERE "
                + getIdColumnName() + " =?";
        try {
            getJdbcTemplate().update(query, amount, id);
        }catch(IncorrectResultSizeDataAccessException e){
            logger.debug("Can't update trader account: " + id, e);
        }
    }
    @Override
    public int updateOne(Account entity) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends Account> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(Account account) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Account> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }
}