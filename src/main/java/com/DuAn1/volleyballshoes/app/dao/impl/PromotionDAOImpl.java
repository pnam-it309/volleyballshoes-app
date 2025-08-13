package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.PromotionDAO;
import com.DuAn1.volleyballshoes.app.entity.Promotion;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PromotionDAOImpl implements PromotionDAO {

    private Promotion mapResultSetToPromotion(ResultSet rs) throws SQLException {
        Promotion promotion = new Promotion();
        promotion.setPromotionId(rs.getInt("promotion_id"));
        promotion.setPromoName(rs.getString("promo_name"));
        promotion.setPromoDiscountValue(rs.getBigDecimal("promo_discount_value"));

        // Xử lý ngày tháng
        Object startDate = rs.getObject("promo_start_date");
        if (startDate != null) {
            if (startDate instanceof java.sql.Timestamp) {
                promotion.setPromoStartDate(((java.sql.Timestamp) startDate).toLocalDateTime());
            } else if (startDate instanceof LocalDateTime) {
                promotion.setPromoStartDate((LocalDateTime) startDate);
            }
        }

        Object endDate = rs.getObject("promo_end_date");
        if (endDate != null) {
            if (endDate instanceof java.sql.Timestamp) {
                promotion.setPromoEndDate(((java.sql.Timestamp) endDate).toLocalDateTime());
            } else if (endDate instanceof LocalDateTime) {
                promotion.setPromoEndDate((LocalDateTime) endDate);
            }
        }

        promotion.setPromoCode(rs.getString("promo_code"));
        return promotion;
    }

    @Override
    public Promotion findByCode(String code) {
        String sql = "SELECT * FROM Promotion WHERE promo_code = ?";
        List<Promotion> list = XJdbc.query(sql, this::mapResultSetToPromotion, code);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Promotion> findActivePromotions() {
        String sql = "SELECT * FROM Promotion WHERE promo_start_date <= GETDATE() AND promo_end_date >= GETDATE()";
        return XJdbc.query(sql, this::mapResultSetToPromotion);
    }

    @Override
    public List<Promotion> search() {
        // Triển khai tìm kiếm nếu cần
        return new ArrayList<>();
    }

    @Override
    public List<Promotion> findByDateRange() {
        // Triển khai tìm kiếm theo khoảng ngày nếu cần
        return new ArrayList<>();
    }

    @Override
    public List<Promotion> findByDiscountType() {
        // Triển khai tìm kiếm theo loại giảm giá nếu cần
        return new ArrayList<>();
    }

    @Override
    public Promotion update(Promotion promotion) {
        String sql = "UPDATE Promotion SET "
                + "promo_name = ?, "
                + "promo_description = ?, "
                + "promo_discount_value = ?, "
                + "promo_start_date = ?, "
                + "promo_end_date = ?, "
                + "promo_code = ? "
                + "WHERE promotion_id = ?";

        XJdbc.executeUpdate(sql,
                promotion.getPromoName(),
                promotion.getPromoDiscountValue(),
                promotion.getPromoStartDate(),
                promotion.getPromoEndDate(),
                promotion.getPromoCode(),
                promotion.getPromotionId()
        );

        return promotion;
    }

    @Override
    public boolean existsById(Integer id) {
//        String sql = "SELECT COUNT(*) FROM Promotion WHERE promotion_id = ?";
//        Integer count = XJdbc.getValue(sql, id);
//        return count != null && count > 0;
        return false;
//        String sql = "SELECT COUNT(*) FROM Promotion WHERE promotion_id = ?";
//        Integer count = XJdbc.getValue(sql, id);
//        return count != null && count > 0;
    }

    @Override
    public void deleteById() {
        throw new UnsupportedOperationException("Please use deleteById(Integer id) instead.");
    }

    @Override
    public Promotion create(Promotion entity) {
        String sql = "INSERT INTO Promotion (promo_name, promo_discount_value, "
                + "promo_start_date, promo_end_date, promo_code) "
                + "VALUES (?, ?, ?, ?, ?)";

        XJdbc.executeUpdate(sql,
                entity.getPromoName(),
                entity.getPromoDiscountValue(),
                entity.getPromoStartDate(),
                entity.getPromoEndDate(),
                entity.getPromoCode()
        );

        // Get the newly created promotion by code
        return findByCode(entity.getPromoCode());
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM Promotion WHERE promotion_id = ?";
        int rowsAffected = XJdbc.executeUpdate(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<Promotion> findAll() {
        String sql = "SELECT * FROM Promotion";
        return XJdbc.query(sql, this::mapResultSetToPromotion);
    }

    @Override
    public Promotion findById(Integer id) {
        String sql = "SELECT * FROM Promotion WHERE promotion_id = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToPromotion, id);
    }

    @Override
    public boolean existsById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
