package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.OrderDAO;
import com.DuAn1.volleyballshoes.app.entity.Order;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.math.BigDecimal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class OrderDAOImpl implements OrderDAO {

    private static final String TABLE_NAME = "[Order]";

    /**
     * Chuyển đổi ResultSet thành đối tượng Order
     */
    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        
        // Handle nullable customer_id
        int customerId = rs.getInt("customer_id");
        if (!rs.wasNull()) {
            order.setCustomerId(customerId);
        } else {
            order.setCustomerId(null);
        }
        
        order.setStaffId(rs.getInt("staff_id"));
        order.setOrderFinalAmount(rs.getBigDecimal("order_final_amount"));
        order.setOrderPaymentMethod(rs.getString("order_payment_method"));
        order.setOrderStatus(rs.getString("order_status"));
        order.setOrderCode(rs.getString("order_code"));

        // Xử lý ngày giờ
        Object createdAt = rs.getObject("order_created_at");
        if (createdAt != null) {
            if (createdAt instanceof java.sql.Timestamp) {
                order.setOrderCreatedAt(((java.sql.Timestamp) createdAt).toLocalDateTime());
            } else if (createdAt instanceof LocalDateTime) {
                order.setOrderCreatedAt((LocalDateTime) createdAt);
            }
        }

        return order;
    }

    @Override
    public List<Order> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY order_created_at DESC";
        return XJdbc.query(sql, this::mapResultSetToOrder);
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
     * Find the maximum order code in format HD####
     * @return the maximum order code number found, or 0 if none found
     */
    private int findMaxOrderCodeNumber() {
        try {
            // First, try to find the maximum order code with the standard HD#### format
            String sql = "SELECT order_code FROM " + TABLE_NAME + " WHERE order_code LIKE 'HD%' ORDER BY order_code DESC";
            
            List<String> orderCodes = XJdbc.query(sql, rs -> {
                return rs.next() ? rs.getString(1) : null;
            });
            
            int maxNumber = 0;
            for (String code : orderCodes) {
                if (code != null && code.startsWith("HD") && code.length() > 2) {
                    try {
                        // Extract the numeric part after 'HD'
                        String numberStr = code.substring(2);
                        int number = Integer.parseInt(numberStr);
                        if (number > maxNumber) {
                            maxNumber = number;
                        }
                    } catch (NumberFormatException e) {
                        // Skip invalid number formats
                        continue;
                    }
                }
            }
            
            // If no valid order codes found, check if there are any orders at all
            if (maxNumber == 0) {
                sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
                List<Integer> counts = XJdbc.query(sql, rs -> {
                    return rs.next() ? rs.getInt(1) : 0;
                });
                
                // If there are orders but none with valid HD#### format, start from 1
                if (!counts.isEmpty() && counts.get(0) > 0) {
                    return 0; // This will make the next number 1
                }
            }
            
            return maxNumber;
        } catch (Exception e) {
            e.printStackTrace();
            // In case of any error, return 0 to start from HD0001
            return 0;
        }
    }

    /**
     * Check if an order code already exists in the database
     * @param code the order code to check
     * @return true if the code exists, false otherwise
     */
    private boolean orderCodeExists(String code) {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE order_code = ?";
        List<Integer> counts = XJdbc.query(sql, rs -> {
            return rs.next() ? rs.getInt(1) : 0;
        }, code);
        return !counts.isEmpty() && counts.get(0) > 0;
    }

    /**
     * Generate the next order code in format HD####
     * @return the next order code (e.g., HD0001, HD0002, ...)
     */
    private String generateNextOrderCode() {
        // First, try to find the maximum order code number from the database
        int maxNumber = findMaxOrderCodeNumber();
        
        // If no orders found, start from 1
        if (maxNumber == 0) {
            return "HD0001";
        }
        
        // Generate next number and format it
        int nextNumber = maxNumber + 1;
        return String.format("HD%04d", nextNumber);
    }

    @Override
    public Order save(Order order) {
        if (order.getOrderId() <= 0) {
            // Generate order code if not provided
            if (order.getOrderCode() == null || order.getOrderCode().isEmpty()) {
                // Try up to 10 times to get a unique order code
                String newCode = null;
                for (int i = 0; i < 10; i++) {
                    newCode = generateNextOrderCode();
                    // Check if code already exists
                    if (!orderCodeExists(newCode)) {
                        order.setOrderCode(newCode);
                        break;
                    }
                }
                
                // If all attempts failed, use timestamp as fallback
                if (order.getOrderCode() == null) {
                    order.setOrderCode("HD" + System.currentTimeMillis());
                }
            }

            // Đặt thời gian tạo nếu chưa có
            if (order.getOrderCreatedAt() == null) {
                order.setOrderCreatedAt(LocalDateTime.now());
            }
            
            // Xây dựng câu lệnh SQL tùy thuộc vào việc customer_id có null không
            String sql;
            if (order.getCustomerId() != null) {
                sql = "INSERT INTO " + TABLE_NAME + " (customer_id, staff_id, order_final_amount, "
                    + "order_payment_method, order_status, order_code, order_created_at) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
                
                XJdbc.executeUpdate(sql,
                    order.getCustomerId(),
                    order.getStaffId(),
                    order.getOrderFinalAmount(),
                    order.getOrderPaymentMethod(),
                    order.getOrderStatus(),
                    order.getOrderCode(),
                    order.getOrderCreatedAt()
                );
            } else {
                sql = "INSERT INTO " + TABLE_NAME + " (staff_id, order_final_amount, "
                    + "order_payment_method, order_status, order_code, order_created_at) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
                
                XJdbc.executeUpdate(sql,
                    order.getStaffId(),
                    order.getOrderFinalAmount(),
                    order.getOrderPaymentMethod(),
                    order.getOrderStatus(),
                    order.getOrderCode(),
                    order.getOrderCreatedAt()
                );
            }

            // Lấy ID vừa tạo
            String idSql = "SELECT IDENT_CURRENT('" + TABLE_NAME + "')";
            List<Integer> ids = XJdbc.query(idSql, rs -> {
                return rs.next() ? rs.getInt(1) : 0;
            });
            
            int newId = !ids.isEmpty() ? ids.get(0) : 0;
            
            if (newId > 0) {
                order.setOrderId(newId);
                System.out.println("[DEBUG] New order created with ID: " + newId);
            } else {
                // Fallback: Try to find the order by code if ID retrieval failed
                System.err.println("[WARN] Failed to get generated ID, trying to find by code...");
                Optional<Order> foundOrder = findByCode(order.getOrderCode());
                if (foundOrder.isPresent()) {
                    order.setOrderId(foundOrder.get().getOrderId());
                    System.out.println("[DEBUG] Found order by code with ID: " + order.getOrderId());
                } else {
                    System.err.println("[ERROR] Could not retrieve generated order ID");
                    throw new RuntimeException("Could not retrieve generated order ID");
                }
            }

            return order;
        } else {
            // Cập nhật đơn hàng hiện có
            String sql;
            if (order.getCustomerId() != null) {
                sql = "UPDATE " + TABLE_NAME + " SET customer_id = ?, staff_id = ?, "
                    + "order_final_amount = ?, order_payment_method = ?, order_status = ? "
                    + "WHERE order_id = ?";

                XJdbc.executeUpdate(sql,
                    order.getCustomerId(),
                    order.getStaffId(),
                    order.getOrderFinalAmount(),
                    order.getOrderPaymentMethod(),
                    order.getOrderStatus(),
                    order.getOrderId()
                );
            } else {
                sql = "UPDATE " + TABLE_NAME + " SET customer_id = NULL, staff_id = ?, "
                    + "order_final_amount = ?, order_payment_method = ?, order_status = ? "
                    + "WHERE order_id = ?";

                XJdbc.executeUpdate(sql,
                    order.getStaffId(),
                    order.getOrderFinalAmount(),
                    order.getOrderPaymentMethod(),
                    order.getOrderStatus(),
                    order.getOrderId()
                );
            }

            return order;
        }
    }

    @Override
    public void deleteById(Integer id) {
        // Xóa chi tiết đơn hàng trước
        String deleteDetailsSql = "DELETE FROM OrderDetails WHERE order_id = ?";
        XJdbc.executeUpdate(deleteDetailsSql, id);

        // Sau đó xóa đơn hàng
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

        String sql = "UPDATE " + TABLE_NAME + " SET customer_id = ?, staff_id = ?, "
                + "order_final_amount = ?, order_payment_method = ?, order_status = ?, "
                + "order_code = ?, order_created_at = ? "
                + "WHERE order_id = ?";

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
        String sql = "SELECT SUM(final_amount) FROM orders WHERE status = 'Hoàn thành'";
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
        String sql = "SELECT COUNT(*) FROM orders";
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
    public BigDecimal getRevenueByDateRange(Date fromDate, Date toDate) {
        String sql = "SELECT SUM(final_amount) FROM orders WHERE created_at BETWEEN ? AND ?";
        try (ResultSet rs = XJdbc.executeQuery(sql, fromDate, toDate)) {
            if (rs.next()) {
                return rs.getBigDecimal(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public int getCanceledOrders() {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE order_status = 'Đã hủy'";
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
        // Lấy ngày đầu tháng hiện tại
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        
        String sql = "SELECT COUNT(*) FROM Customer WHERE created_at >= ?";
        try (ResultSet rs = XJdbc.executeQuery(sql, startOfMonth)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Object[][] getRevenueDataByYear(int year) {
        Object[][] data = new Object[12][2];
        String sql = "SELECT MONTH(order_created_at) as month, SUM(order_final_amount) as revenue "
                   + "FROM " + TABLE_NAME 
                   + " WHERE YEAR(order_created_at) = ? AND order_status = 'Hoàn thành' "
                   + "GROUP BY MONTH(order_created_at)";
        
        try (ResultSet rs = XJdbc.executeQuery(sql, year)) {
            // Khởi tạo mảng với các tháng và doanh thu ban đầu là 0
            for (int i = 0; i < 12; i++) {
                data[i][0] = String.format("Tháng %d", i + 1);
                data[i][1] = 0.0;
            }
            
            // Cập nhật dữ liệu từ kết quả truy vấn
            while (rs.next()) {
                int month = rs.getInt("month") - 1; // Chỉ số mảng bắt đầu từ 0
                double revenue = rs.getDouble("revenue");
                if (!rs.wasNull()) {
                    data[month][1] = revenue;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return data;
    }

    @Override
    public Object[][] getCanceledOrderDataByYear(int year) {
        Object[][] data = new Object[12][2];
        String sql = "SELECT MONTH(order_created_at) as month, COUNT(*) as count "
                   + "FROM " + TABLE_NAME 
                   + " WHERE YEAR(order_created_at) = ? AND order_status = 'Đã hủy' "
                   + "GROUP BY MONTH(order_created_at)";
        
        try (ResultSet rs = XJdbc.executeQuery(sql, year)) {
            // Khởi tạo mảng với các tháng và số lượng đơn hủy ban đầu là 0
            for (int i = 0; i < 12; i++) {
                data[i][0] = String.format("Tháng %d", i + 1);
                data[i][1] = 0;
            }
            
            // Cập nhật dữ liệu từ kết quả truy vấn
            while (rs.next()) {
                int month = rs.getInt("month") - 1; // Chỉ số mảng bắt đầu từ 0
                int count = rs.getInt("count");
                data[month][1] = count;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return data;
    }

    @Override
    public List<Order> findByCreatedDateBetween(Date from, Date to) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Order> findByTotalAmountBetween(double min, double max) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public List<Order> findByStatus(String status) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE order_status = ? ORDER BY order_created_at DESC";
        return XJdbc.query(sql, this::mapResultSetToOrder, status);
    }
}
