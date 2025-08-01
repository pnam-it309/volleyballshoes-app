package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.CustomerDAO;
import com.DuAn1.volleyballshoes.app.entity.Customer;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of CustomerDAO interface for managing customer data.
 */
public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public Customer create(Customer customer) {
        String sql = "INSERT INTO Customers (customer_code, customer_username, customer_password, "
                + "customer_full_name, customer_email, customer_phone, customer_gender, "
                + "customer_birth_date, customer_address, customer_points, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

//        customer.setCreatedAt(LocalDate.now());
        XJdbc.executeUpdate(sql,
                customer.getCustomerCode(),
                customer.getCustomerUsername(),
                customer.getCustomerEmail(),
                customer.getCustomerPhone(),
                customer.getCreatedAt()
        );

//        // Lấy ID vừa tạo
//        sql = "SELECT IDENT_CURRENT('Customers') as id";
//        Integer id = XJdbc.queryForObject(sql, Integer.class);
//        customer.setCustomerId(id);
//        
        return customer;
    }

//    @Override
//    public Customer update(Customer customer) {
//        String sql = "UPDATE Customers SET customer_code = ?, customer_username = ?, "
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
//        String sql = "DELETE FROM Customers WHERE customer_id = ?";
//        return XJdbc.executeUpdate(sql, id) > 0;
//    }
    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM Customers ORDER BY customer_id DESC";
        return XJdbc.query(sql, this::mapResultSetToCustomer);
    }

    @Override
    public Customer findById(Integer id) {
        String sql = "SELECT * FROM Customers WHERE customer_id = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToCustomer, id);
    }

    @Override
    public Customer findByCode(String customerCode) {
        String sql = "SELECT * FROM Customers WHERE customer_code = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToCustomer, customerCode);
    }

    @Override
    public Customer findByPhone(String phone) {
        String sql = "SELECT * FROM Customers WHERE customer_phone = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToCustomer, phone);
    }

    @Override
    public Customer findByEmail(String email) {
        String sql = "SELECT * FROM Customers WHERE customer_email = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToCustomer, email);
    }

    @Override
    public List<Customer> searchByName(String name) {
        String sql = "SELECT * FROM Customers WHERE customer_full_name LIKE ? ORDER BY customer_full_name";
        return XJdbc.query(sql, this::mapResultSetToCustomer, "%" + name + "%");
    }

    @Override
    public List<Customer> searchByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }

        String searchPattern = "%" + keyword.trim() + "%";
        String sql = "SELECT * FROM Customers WHERE "
                + "customer_code LIKE ? OR "
                + "customer_full_name LIKE ? OR "
                + "customer_phone LIKE ? OR "
                + "customer_email LIKE ? "
                + "ORDER BY customer_id DESC";

        return XJdbc.query(sql, this::mapResultSetToCustomer,
                searchPattern, searchPattern, searchPattern, searchPattern);
    }

    @Override
    public List<Customer> findWithPagination(int offset, int limit) {
        String sql = "SELECT * FROM Customers ORDER BY customer_id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return XJdbc.query(sql, this::mapResultSetToCustomer, offset, limit);
    }

    @Override
    public long countAll() {
        String sql = "SELECT COUNT(*) FROM Customers";
        List<Long> counts = XJdbc.query(sql, rs -> rs.getLong(1));
        return counts.isEmpty() ? 0 : counts.get(0);
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
        customer.setCustomerCode(rs.getString("customer_code"));
        customer.setCustomerUsername(rs.getString("customer_username"));
        customer.setCustomerEmail(rs.getString("customer_email"));
        customer.setCustomerSdt(rs.getString("customer_phone"));

//        // Xử lý ngày tạo/cập nhật
//        Object createdAt = rs.getObject("created_at");
//        if (createdAt != null) {
//            if (createdAt instanceof java.sql.Date) {
//                customer.setCreatedAt(((java.sql.Date) createdAt).toLocalDate());
//            } else if (createdAt instanceof java.sql.Timestamp) {
//                customer.setCreatedAt(((java.sql.Timestamp) createdAt).toLocalDateTime().toLocalDate());
//            }
//        }
//        
//        Object updatedAt = rs.getObject("updated_at");
//        if (updatedAt != null) {
//            if (updatedAt instanceof java.sql.Date) {
//                customer.setUpdatedAt(((java.sql.Date) updatedAt).toLocalDate());
//            } else if (updatedAt instanceof java.sql.Timestamp) {
//                customer.setUpdatedAt(((java.sql.Timestamp) updatedAt).toLocalDateTime().toLocalDate());
//            }
//        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        String sql = "UPDATE Customers SET customer_code = ?, customer_username = ?, "
                + "customer_password = ?, customer_full_name = ?, customer_email = ?, "
                + "customer_phone = ?, customer_gender = ?, customer_birth_date = ?, "
                + "customer_address = ?, customer_points = ?, updated_at = GETDATE() "
                + "WHERE customer_id = ?";

        XJdbc.executeUpdate(sql,
                customer.getCustomerCode(),
                customer.getCustomerUsername(),
                customer.getCustomerEmail(),
                customer.getCustomerPhone(),
                customer.getCustomerId()
        );

        return customer;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM Customers WHERE customer_id = ?";
        return XJdbc.executeUpdate(sql, id) > 0;
    }
}
