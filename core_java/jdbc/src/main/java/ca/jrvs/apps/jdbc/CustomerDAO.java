package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataAccessObject;
import ca.jrvs.apps.jdbc.util.DataTransferObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDAO extends DataAccessObject <Customer>{
    private static final String INSERT="Insert into customer(first_name,last_name,email,phone,address,city,state,zipcode) VALUES (?,?,?,?,?,?,?,?)";
    private static final String GET_ONE="SELECT customer_id,first_name,last_name,email,phone ,address,city,state,zipcode FROM customer WHERE customer_id=?";
    private static final String UPDATE="UPDATE customer SET first_name=?,last_name=?,email=?,phone=?,address=?,city=?,state=?,zipcode=? WHERE customer_id=?";
    private static final String DELETE="DELETE from customer WHERE customer_id=?";
    public CustomerDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Customer findbyId(long id) {
        Customer customer=new Customer();
        try(PreparedStatement statement=this.connection.prepareStatement(GET_ONE);){
            statement.setLong(1,id);
            ResultSet rs= statement.executeQuery();
            while(rs.next()) {
                customer.setId(rs.getLong("customer_id"));
                customer.setFirstname(rs.getString("first_name"));
                customer.setLastname(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("address"));
                customer.setCity(rs.getString("city"));
                customer.setState(rs.getString("state"));
                customer.setZipcode(rs.getString("zipcode"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Customer update(Customer dto) {
        Customer customer =null;
        try(PreparedStatement statement=this.connection.prepareStatement(UPDATE);){
            statement.setString(1,dto.getFirstname());
            statement.setString(2, dto.getLastname());
            statement.setString(3, dto.getEmail());
            statement.setString(4, dto.getPhone());
            statement.setString(5, dto.getAddress());
            statement.setString(6, dto.getCity());
            statement.setString(7, dto.getState());
            statement.setString(8, dto.getZipcode());
            statement.setLong(9, dto.getID());
            statement.execute();
            customer=this.findbyId(dto.getID());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public Customer create(Customer dto) {
        try(PreparedStatement statement=this.connection.prepareStatement(INSERT);){
            statement.setString(1,dto.getFirstname());
            statement.setString(2, dto.getLastname());
            statement.setString(3, dto.getEmail());
            statement.setString(4, dto.getPhone());
            statement.setString(5, dto.getAddress());
            statement.setString(6, dto.getCity());
            statement.setString(7, dto.getState());
            statement.setString(8, dto.getZipcode());
            statement.execute();
            int id= this.getLastVal(Customer_Sequence);
            return this.findbyId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(long id) {
       try(PreparedStatement statement= this.connection.prepareStatement(DELETE)){
           statement.setLong(1,id);
           statement.execute();

       } catch (SQLException e) {
           e.printStackTrace();
           throw new RuntimeException(e);
       }

    }
}
