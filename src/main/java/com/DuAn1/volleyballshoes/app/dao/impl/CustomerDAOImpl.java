package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.CustomerDAO;
import com.DuAn1.volleyballshoes.app.entity.Customer;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of CustomerDAO interface for managing customer data.
 */
public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public Customer create(Customer customer) {
        String sql = "INSERT INTO Customer (customer_username, customer_password, "
                  + "customer_full_name, customer_email, customer_phone, customer_sdt, customer_code) "
                  + "OUTPUT INSERTED.* "
                  + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        return XJdbc.queryForObject(sql, this::mapResultSetToCustomer,
            customer.getCustomerUsername(),
            customer.getCustomerPassword(),
            customer.getCustomerFullName(),
            customer.getCustomerEmail(),
            customer.getCustomerPhone(),
            customer.getCustomerSdt(),
            customer.getCustomerCode(),
            customer.getCustomerPoints() != null ? customer.getCustomerPoints() : 0
        );
    }

//    @Override
//    public Customer update(Customer customer) {
//        String sql = "UPDATE Customer SET customer_code = ?, customer_username = ?, "
//                  + "customer_password = ?, customer_full_name = ?, customer_email = ?, "
//                  + "customer_phone = ?, customer_gender = ?, customer_birth_date = ?, "
//                  + "customer_address = ?, customer_points = ?, updated_at = ? "
//                  + "WHERE customer_id = ?";
//        
//        customer.setUpdatedAt(LocalDate.now());
//        
//        XJdbc.executeUpdate(sql, 
//            customer.getCustomerCode(),
//            customer.getCustomerUsername(),
//
//            customer.getCustomerEmail(),
//            customer.getCustomerSdt(),
////            customer.getUpdatedAt(),
//            customer.getCustomerId()
//        );
//        
//        return customer;
//    }
//    @Override
//    public boolean deleteById(Integer id) {
//        String sql = "DELETE FROM Customer WHERE customer_id = ?";
//        return XJdbc.executeUpdate(sql, id) > 0;
//    }
    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM Customer ORDER BY customer_id DESC";
        return XJdbc.query(sql, this::mapResultSetToCustomer);
    }

    @Override
    public Customer findById(Integer id) {
        String sql = "SELECT * FROM Customer WHERE customer_id = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToCustomer, id);
    }

    @Override
    public Customer findByCode(String customerCode) {
        String sql = "SELECT * FROM Customer WHERE customer_code = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToCustomer, customerCode);
    }

    @Override
    public Customer findByPhone(String phone) {
        String sql = "SELECT * FROM Customer WHERE customer_phone = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToCustomer, phone);
    }

    @Override
    public Customer findByEmail(String email) {
        String sql = "SELECT * FROM Customer WHERE customer_email = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToCustomer, email);
    }

    @Override
    public List<Customer> searchByName(String name) {
        String sql = "SELECT * FROM Customer WHERE customer_full_name LIKE ? ORDER BY customer_full_name";
        return XJdbc.query(sql, this::mapResultSetToCustomer, "%" + name + "%");
    }

    @Override
    public List<Customer> searchByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }

        String searchPattern = "%" + keyword.trim() + "%";
        String sql = "SELECT * FROM Customer WHERE "
                + "customer_code LIKE ? OR "
                + "customer_username LIKE ? OR "
                + "customer_full_name LIKE ? OR "
                + "customer_email LIKE ? OR "
                + "customer_phone LIKE ? OR "
                + "customer_sdt LIKE ? "
                + "ORDER BY customer_id DESC";

        return XJdbc.query(sql, this::mapResultSetToCustomer,
                searchPattern, searchPattern, searchPattern, 
                searchPattern, searchPattern, searchPattern);
    }

    @Override
    public List<Customer> findWithPagination(int offset, int limit) {
        String sql = "SELECT * FROM Customer ORDER BY customer_id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return XJdbc.query(sql, this::mapResultSetToCustomer, offset, limit);
    }


    @Override
    public int getNewCustomersCount() {
        // Lấy ngày đầu tháng hiện tại
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);

        java.util.Date firstDayOfMonth = cal.getTime();

        String sql = "SELECT COUNT(*) FROM Customer WHERE created_at >= ?";
        Long count = XJdbc.getValue(sql, firstDayOfMonth);
        return count != null ? count.intValue() : 0;
    }

    /**
     * Chuyển đổi ResultSet thành đối tượng Customer
     */
    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        if (rs == null || !rs.next()) {
            return null;
        }

        Customer customer = new Customer();
        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setCustomerUsername(rs.getString("customer_username"));
        customer.setCustomerPassword(rs.getString("customer_password"));
        customer.setCustomerFullName(rs.getString("customer_full_name"));
        customer.setCustomerEmail(rs.getString("customer_email"));
        customer.setCustomerPhone(rs.getString("customer_phone"));
        customer.setCustomerSdt(rs.getString("customer_sdt"));
        customer.setCustomerCode(rs.getString("customer_code"));
        customer.setCustomerPoints(rs.getInt("customer_points"));
        
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        String sql = "UPDATE Customer SET customer_username = ?, customer_password = ?, "
                  + "customer_full_name = ?, customer_email = ?, customer_phone = ?, "
                  + "customer_sdt = ?, customer_code = ?, customer_points = ? "
                  + "OUTPUT INSERTED.* WHERE customer_id = ?";
        
        return XJdbc.queryForObject(sql, this::mapResultSetToCustomer,
            customer.getCustomerUsername(),
            customer.getCustomerPassword(),
            customer.getCustomerFullName(),
            customer.getCustomerEmail(),
            customer.getCustomerPhone(),
            customer.getCustomerSdt(),
            customer.getCustomerCode(),
            customer.getCustomerPoints() != null ? customer.getCustomerPoints() : 0,
            customer.getCustomerId()
        );
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM Customer WHERE customer_id = ?";
        return XJdbc.executeUpdate(sql, id) > 0;
    }
    
    @Override
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM Customer WHERE customer_username = ?";
        Integer count = XJdbc.getValue(sql, username);
        return count != null && count > 0;
    }
    
    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM Customer WHERE customer_email = ?";
        Integer count = XJdbc.getValue(sql, email);
        return count != null && count > 0;
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Customer";
        Long count = XJdbc.getValue(sql);
        return count != null ? count : 0;
    }
}
