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
        order.setCustomerId(rs.getInt("customer_id"));
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

    @Override
    public Order save(Order order) {
        if (order.getOrderId() <= 0) {
            // Thêm mới đơn hàng
            String sql = "INSERT INTO " + TABLE_NAME + " (customer_id, staff_id, order_final_amount, "
                    + "order_payment_method, order_status, order_code, order_created_at) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            // Tạo mã đơn hàng nếu chưa có
            if (order.getOrderCode() == null || order.getOrderCode().isEmpty()) {
                order.setOrderCode("ORD" + System.currentTimeMillis());
            }

            // Đặt thời gian tạo nếu chưa có
            if (order.getOrderCreatedAt() == null) {
                order.setOrderCreatedAt(LocalDateTime.now());
            }

            XJdbc.executeUpdate(sql,
                    order.getCustomerId(),
                    order.getStaffId(),
                    order.getOrderFinalAmount(),
                    order.getOrderPaymentMethod(),
                    order.getOrderStatus(),
                    order.getOrderCode(),
                    order.getOrderCreatedAt()
            );

            // Lấy ID vừa tạo
            String idSql = "SELECT IDENT_CURRENT('" + TABLE_NAME + "')";
            List<Integer> ids = XJdbc.query(idSql, rs -> {
                return rs.next() ? rs.getInt(1) : null;
            });
            if (!ids.isEmpty() && ids.get(0) != null) {
                order.setOrderId(ids.get(0));
            }

            return order;
        } else {
            // Cập nhật đơn hàng hiện có
            String sql = "UPDATE " + TABLE_NAME + " SET customer_id = ?, staff_id = ?, "
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

}
