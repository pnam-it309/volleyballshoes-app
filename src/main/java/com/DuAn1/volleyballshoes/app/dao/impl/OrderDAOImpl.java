package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.OrderDAO;
import com.DuAn1.volleyballshoes.app.dao.OrderDetailDAO;
import com.DuAn1.volleyballshoes.app.dao.ProductVariantDAO;
import com.DuAn1.volleyballshoes.app.dto.response.OrderDetailResponse;
import com.DuAn1.volleyballshoes.app.dto.response.OrderResponse;
import com.DuAn1.volleyballshoes.app.dto.response.OrderWithDetailsResponse;
import com.DuAn1.volleyballshoes.app.entity.Order;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderDAOImpl implements OrderDAO {

    private static final String TABLE_NAME = "[Order]";

    /**
     * Map ResultSet -> Order entity
     */
    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        try {
            // Log column names for debugging
            // java.sql.ResultSetMetaData rsmd = rs.getMetaData();
            // int columnCount = rsmd.getColumnCount();
            // System.out.println("\nColumns in result set (" + columnCount + "):");
            // for (int i = 1; i <= columnCount; i++) {
            //     System.out.println(i + ". " + rsmd.getColumnName(i) + " (" + rsmd.getColumnTypeName(i) + ")");
            // }
            
            int orderId = rs.getInt("order_id");
            order.setOrderId(orderId);
            
            // customer_id can be null
            int customerId = rs.getInt("customer_id");
            if (!rs.wasNull()) {
                order.setCustomerId(customerId);
            } else {
                order.setCustomerId(null);
                // System.out.println("customer_id is NULL for order " + orderId);
            }

            int staffId = rs.getInt("staff_id");
            order.setStaffId(staffId);
            
            BigDecimal finalAmount = rs.getBigDecimal("order_final_amount");
            order.setOrderFinalAmount(finalAmount);
            
            String paymentMethod = rs.getString("order_payment_method");
            order.setOrderPaymentMethod(paymentMethod);
            
            String status = rs.getString("order_status");
            order.setOrderStatus(status);
            
            String orderCode = rs.getString("order_code");
            order.setOrderCode(orderCode);

            Timestamp ts = rs.getTimestamp("order_created_at");
            if (ts != null) {
                order.setOrderCreatedAt(ts.toLocalDateTime());
            } else {
                // System.out.println("order_created_at is NULL for order " + orderId);
            }
            
            // Debug log the mapped order
            // System.out.println("Mapped Order: ID=" + orderId + 
            //                  ", CustomerID=" + customerId + 
            //                  ", StaffID=" + staffId + 
            //                  ", Status=" + status + 
            //                  ", Code=" + orderCode);
            
        } catch (SQLException e) {
            System.err.println("Error mapping Order from ResultSet: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("Unexpected error in mapResultSetToOrder: " + e.getMessage());
            e.printStackTrace();
            throw new SQLException("Failed to map Order from ResultSet", e);
        }

        return order;
    }

    @Override
    public List<Order> findAll() {
        System.out.println("OrderDAOImpl.findAll() - Starting to fetch all orders...");
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY order_created_at DESC";
        System.out.println("Executing SQL: " + sql);
        
        List<Order> orders = XJdbc.query(sql, this::mapResultSetToOrder);
        
        System.out.println("OrderDAOImpl.findAll() - Fetched " + (orders != null ? orders.size() : 0) + " orders");
        if (orders != null && !orders.isEmpty()) {
            System.out.println("Sample order - ID: " + orders.get(0).getOrderId() + 
                             ", Status: " + orders.get(0).getOrderStatus() +
                             ", Customer ID: " + orders.get(0).getCustomerId() +
                             ", Staff ID: " + orders.get(0).getStaffId());
        }
        
        return orders;
    }

    @Override
    public List<Order> findAll(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        String sql = String.format(
                "SELECT * FROM %s ORDER BY order_created_at DESC OFFSET %d ROWS FETCH NEXT %d ROWS ONLY",
                TABLE_NAME, offset, pageSize
        );
        return XJdbc.query(sql, this::mapResultSetToOrder);
    }

    @Override
    public Optional<Order> findById(Integer id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE order_id = ?";
        List<Order> list = XJdbc.query(sql, this::mapResultSetToOrder, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public Optional<Order> findByCode(String code) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE order_code = ?";
        List<Order> list = XJdbc.query(sql, this::mapResultSetToOrder, code);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    /**
     * Tạo mã đơn dạng ORDyyyymmddNNN
     */
    @Override
    public String generateOrderCode() {
        String prefix = "ORD";
        String datePart = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
        String likePattern = prefix + datePart + "%";

        String sql = "SELECT TOP 1 order_code FROM " + TABLE_NAME +
                     " WHERE order_code LIKE ? ORDER BY order_code DESC";

        List<String> codes = XJdbc.query(sql, rs -> rs.getString("order_code"), likePattern);

        if (!codes.isEmpty()) {
            String lastCode = codes.get(0);
            int lastNumber = Integer.parseInt(lastCode.substring(prefix.length() + datePart.length()));
            return prefix + datePart + String.format("%03d", lastNumber + 1);
        }
        return prefix + datePart + "001";
    }

    @Override
    public Order save(Order order) {
        if (order.getOrderId() <= 0) {
            // insert
            if (order.getOrderCode() == null || order.getOrderCode().isEmpty()) {
                order.setOrderCode(generateOrderCode());
            }
            if (order.getOrderCreatedAt() == null) {
                order.setOrderCreatedAt(LocalDateTime.now());
            }

            String sqlWithCustomer = "INSERT INTO " + TABLE_NAME +
                    " (customer_id, staff_id, order_final_amount, order_payment_method, order_status, order_code, order_created_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            String sqlNoCustomer = "INSERT INTO " + TABLE_NAME +
                    " (staff_id, order_final_amount, order_payment_method, order_status, order_code, order_created_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conn = XJdbc.openConnection();
                 PreparedStatement ps = conn.prepareStatement(
                         (order.getCustomerId() != null ? sqlWithCustomer : sqlNoCustomer),
                         Statement.RETURN_GENERATED_KEYS
                 )) {

                if (order.getCustomerId() != null) {
                    ps.setInt(1, order.getCustomerId());
                    ps.setInt(2, order.getStaffId());
                    ps.setBigDecimal(3, order.getOrderFinalAmount());
                    ps.setString(4, order.getOrderPaymentMethod());
                    ps.setString(5, order.getOrderStatus());
                    ps.setString(6, order.getOrderCode());
                    ps.setTimestamp(7, Timestamp.valueOf(order.getOrderCreatedAt()));
                } else {
                    ps.setInt(1, order.getStaffId());
                    ps.setBigDecimal(2, order.getOrderFinalAmount());
                    ps.setString(3, order.getOrderPaymentMethod());
                    ps.setString(4, order.getOrderStatus());
                    ps.setString(5, order.getOrderCode());
                    ps.setTimestamp(6, Timestamp.valueOf(order.getOrderCreatedAt()));
                }

                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        order.setOrderId(rs.getInt(1));
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException("Error inserting order", e);
            }

        } else {
            // update
            String sql = "UPDATE " + TABLE_NAME +
                    " SET customer_id = ?, staff_id = ?, order_final_amount = ?, order_payment_method = ?, order_status = ?, order_code = ?, order_created_at = ? " +
                    "WHERE order_id = ?";
            XJdbc.executeUpdate(sql,
                    order.getCustomerId(),
                    order.getStaffId(),
                    order.getOrderFinalAmount(),
                    order.getOrderPaymentMethod(),
                    order.getOrderStatus(),
                    order.getOrderCode(),
                    order.getOrderCreatedAt(),
                    order.getOrderId()
            );
        }
        return order;
    }

    @Override
    public void deleteById(Integer id) {
        String deleteDetailsSql = "DELETE FROM OrderDetail WHERE order_id = ?";
        XJdbc.executeUpdate(deleteDetailsSql, id);

        String sql = "DELETE FROM " + TABLE_NAME + " WHERE order_id = ?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        List<Long> counts = XJdbc.query(sql, rs -> rs.getLong(1));
        return counts.isEmpty() ? 0 : counts.get(0);
    }

    @Override
    public List<Order> findByCustomerId(Integer customerId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE customer_id = ? ORDER BY order_created_at DESC";
        return XJdbc.query(sql, this::mapResultSetToOrder, customerId);
    }

    @Override
    public List<Order> findByStaffId(Integer staffId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE staff_id = ? ORDER BY order_created_at DESC";
        return XJdbc.query(sql, this::mapResultSetToOrder, staffId);
    }

    @Override
    public List<Order> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String searchPattern = "%" + keyword.trim() + "%";
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE order_code LIKE ? OR CAST(order_id AS VARCHAR(20)) LIKE ?";
        return XJdbc.query(sql, this::mapResultSetToOrder, searchPattern, searchPattern);
    }

    @Override
    public Order update(Order order) {
        if (order.getOrderId() <= 0) {
            throw new IllegalArgumentException("Order ID must be greater than 0 for update");
        }
        String sql = "UPDATE " + TABLE_NAME +
                " SET customer_id = ?, staff_id = ?, order_final_amount = ?, order_payment_method = ?, order_status = ?, order_code = ?, order_created_at = ? " +
                "WHERE order_id = ?";
        XJdbc.executeUpdate(sql,
                order.getCustomerId(),
                order.getStaffId(),
                order.getOrderFinalAmount(),
                order.getOrderPaymentMethod(),
                order.getOrderStatus(),
                order.getOrderCode(),
                order.getOrderCreatedAt(),
                order.getOrderId()
        );
        return order;
    }

    @Override
    public BigDecimal getTotalRevenue() {
        String sql = "SELECT SUM(order_final_amount) FROM " + TABLE_NAME + " WHERE order_status = N'Hoàn thành'";
        try (ResultSet rs = XJdbc.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getBigDecimal(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public int getTotalOrders() {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        try (ResultSet rs = XJdbc.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public int getCanceledOrders() {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE order_status = N'Đã hủy'";
        try (ResultSet rs = XJdbc.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public int getNewCustomersCount() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object[][] getRevenueDataByYear(int year) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object[][] getCanceledOrderDataByYear(int year) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

 
    @Override
    public List<Order> findByTotalAmountBetween(double min, double max) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Order> findByStatus(String status) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getStaffIdByCode(String staffCode) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Order getOrderByCode(String orderCode) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Order processPayment(Order order, Map<Integer, Integer> orderDetails) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public OrderWithDetailsResponse findOrderWithDetails(String orderCode) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BigDecimal getRevenueByDateRange(java.util.Date fromDate, java.util.Date toDate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Order> findByCreatedDateBetween(java.util.Date from, java.util.Date to) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
