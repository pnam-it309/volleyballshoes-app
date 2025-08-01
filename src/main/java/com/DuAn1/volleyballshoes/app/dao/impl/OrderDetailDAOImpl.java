package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.OrderDetailDAO;
import com.DuAn1.volleyballshoes.app.entity.OrderDetail;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public List<OrderDetail> findByOrderId(Integer orderId) {
        String sql = "SELECT * FROM OrderDetails WHERE order_id = ?";
        return XJdbc.query(sql, this::mapResultSetToOrderDetail, orderId);
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM OrderDetails WHERE order_detail_id = ?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public void deleteByOrderId(Integer orderId) {
        String sql = "DELETE FROM OrderDetails WHERE order_id = ?";
        XJdbc.executeUpdate(sql, orderId);
    }

    @Override
    public List<OrderDetail> saveAll(List<OrderDetail> orderDetailList) {
        String sql = "INSERT INTO OrderDetails (order_id, variant_id, detail_quantity, detail_unit_price) "
                + "VALUES (?, ?, ?, ?)";

        // Sử dụng vòng lặp thông thường thay vì batchUpdate
        for (OrderDetail detail : orderDetailList) {
            XJdbc.executeUpdate(sql,
                    detail.getOrderId(),
                    detail.getVariantId(),
                    detail.getDetailQuantity(),
                    detail.getDetailUnitPrice()
            );

            // Lấy ID vừa tạo cho đơn hàng chi tiết
            String idSql = "SELECT IDENT_CURRENT('OrderDetails')";
            List<Integer> ids = XJdbc.query(idSql, rs -> {
                return rs.next() ? rs.getInt(1) : null;
            });
            if (!ids.isEmpty() && ids.get(0) != null) {
                detail.setOrderDetailId(ids.get(0));
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
        return detail;
    }

    @Override
    public Optional<OrderDetail> findById(Integer id) {
        String sql = "SELECT * FROM OrderDetails WHERE order_detail_id = ?";
        List<OrderDetail> list = XJdbc.query(sql, this::mapResultSetToOrderDetail, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        // Kiểm tra nếu orderDetailId <= 0 thì coi như là thêm mới
        if (orderDetail.getOrderDetailId() <= 0) {
            // Thêm mới
            String sql = "INSERT INTO OrderDetails (order_id, variant_id, detail_quantity, detail_unit_price) "
                    + "VALUES (?, ?, ?, ?)";

            XJdbc.executeUpdate(sql,
                    orderDetail.getOrderId(),
                    orderDetail.getVariantId(),
                    orderDetail.getDetailQuantity(),
                    orderDetail.getDetailUnitPrice()
            );

            // Lấy ID vừa tạo
            String idSql = "SELECT IDENT_CURRENT('OrderDetails')";
            List<Integer> ids = XJdbc.query(idSql, rs -> {
                return rs.next() ? rs.getInt(1) : null;
            });
            if (!ids.isEmpty() && ids.get(0) != null) {
                orderDetail.setOrderDetailId(ids.get(0));
            }

            return orderDetail;
        } else {
            // Cập nhật
            String sql = "UPDATE OrderDetails SET order_id = ?, variant_id = ?, "
                    + "detail_quantity = ?, detail_unit_price = ? "
                    + "WHERE order_detail_id = ?";

            XJdbc.executeUpdate(sql,
                    orderDetail.getOrderId(),
                    orderDetail.getVariantId(),
                    orderDetail.getDetailQuantity(),
                    orderDetail.getDetailUnitPrice(),
                    orderDetail.getOrderDetailId()
            );

            return orderDetail;
        }
    }

}
