package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.OrderDetailDAO;
import com.DuAn1.volleyballshoes.app.entity.OrderDetail;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public List<OrderDetail> findByOrderId(Integer orderId) {
        String sql = "SELECT * FROM OrderDetail WHERE order_id = ?";
        List<OrderDetail> results = XJdbc.query(sql, this::mapResultSetToOrderDetail, orderId);
        return results;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM OrderDetail WHERE order_detail_id = ?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public void deleteByOrderId(Integer orderId) {
        String sql = "DELETE FROM OrderDetail WHERE order_id = ?";
        XJdbc.executeUpdate(sql, orderId);
    }

    @Override
    public List<OrderDetail> saveAll(List<OrderDetail> orderDetailList) {
        String sql = "INSERT INTO OrderDetail (order_id, variant_id, detail_quantity, detail_unit_price, detail_discount_percent, detail_total) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        // Sử dụng vòng lặp thông thường thay vì batchUpdate
        for (OrderDetail detail : orderDetailList) {
            try (Connection conn = XJdbc.openConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setInt(1, detail.getOrderId());
                stmt.setInt(2, detail.getVariantId());
                stmt.setInt(3, detail.getDetailQuantity());
                stmt.setBigDecimal(4, detail.getDetailUnitPrice());
                stmt.setBigDecimal(5, detail.getDetailDiscountPercent() != null
                        ? detail.getDetailDiscountPercent() : BigDecimal.ZERO);
                stmt.setBigDecimal(6, detail.getDetailTotal() != null
                        ? detail.getDetailTotal() : detail.getDetailUnitPrice().multiply(BigDecimal.valueOf(detail.getDetailQuantity())));

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating order detail failed, no rows affected.");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        detail.setOrderDetailId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating order detail failed, no ID obtained.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Xử lý lỗi hoặc ném ngoại lệ tùy theo yêu cầu của bạn
            }
        }

        return orderDetailList;
    }

    /**
     * Chuyển đổi ResultSet thành đối tượng OrderDetail
     */
    private OrderDetail mapResultSetToOrderDetail(ResultSet rs) throws SQLException {
        OrderDetail detail = new OrderDetail();
        detail.setOrderDetailId(rs.getInt("order_detail_id"));
        detail.setOrderId(rs.getInt("order_id"));
        detail.setVariantId(rs.getInt("variant_id"));
        detail.setDetailQuantity(rs.getInt("detail_quantity"));
        detail.setDetailUnitPrice(rs.getBigDecimal("detail_unit_price"));
        detail.setDetailDiscountPercent(rs.getBigDecimal("detail_discount_percent"));
        detail.setDetailTotal(rs.getBigDecimal("detail_total"));
        return detail;
    }

    @Override
    public Optional<OrderDetail> findById(Integer id) {
        String sql = "SELECT * FROM OrderDetail WHERE order_detail_id = ?";
        List<OrderDetail> list = XJdbc.query(sql, this::mapResultSetToOrderDetail, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        Connection conn = null;
        try {
            conn = XJdbc.openConnection();

            if (orderDetail.getOrderDetailId() <= 0) {
                // Insert new
                String sql = "INSERT INTO OrderDetail (order_id, variant_id, detail_quantity, "
                        + "detail_unit_price, detail_discount_percent, detail_total) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";

                try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setInt(1, orderDetail.getOrderId());
                    stmt.setInt(2, orderDetail.getVariantId());
                    stmt.setInt(3, orderDetail.getDetailQuantity());
                    stmt.setBigDecimal(4, orderDetail.getDetailUnitPrice());
                    stmt.setBigDecimal(5, orderDetail.getDetailDiscountPercent());
                    stmt.setBigDecimal(6, orderDetail.getDetailTotal());

                    int affectedRows = stmt.executeUpdate();
                    if (affectedRows == 0) {
                        throw new SQLException("Creating order detail failed, no rows affected.");
                    }

                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            orderDetail.setOrderDetailId(generatedKeys.getInt(1));
                        } else {
                            throw new SQLException("Creating order detail failed, no ID obtained.");
                        }
                    }
                }
            } else {
                // Update existing
                String sql = "UPDATE OrderDetail SET order_id=?, variant_id=?, detail_quantity=?, "
                        + "detail_unit_price=?, detail_discount_percent=?, detail_total=? "
                        + "WHERE order_detail_id=?";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, orderDetail.getOrderId());
                    stmt.setInt(2, orderDetail.getVariantId());
                    stmt.setInt(3, orderDetail.getDetailQuantity());
                    stmt.setBigDecimal(4, orderDetail.getDetailUnitPrice());
                    stmt.setBigDecimal(5, orderDetail.getDetailDiscountPercent());
                    stmt.setBigDecimal(6, orderDetail.getDetailTotal());
                    stmt.setInt(7, orderDetail.getOrderDetailId());

                    int affectedRows = stmt.executeUpdate();
                    if (affectedRows == 0) {
                        throw new SQLException("Updating order detail failed, no rows affected.");
                    }
                }
            }

            return orderDetail;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
