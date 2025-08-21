package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.StaffDAO;
import com.DuAn1.volleyballshoes.app.entity.Staff;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StaffDAOImpl implements StaffDAO {

    private Staff mapResultSetToStaff(ResultSet rs) throws SQLException {
        if (rs == null) {
            return null;
        }
        try {
            Staff staff = new Staff();
            staff.setStaffId(rs.getInt("staff_id"));
            staff.setStaffUsername(rs.getString("staff_username"));
            staff.setStaffPassword(rs.getString("staff_password"));
            staff.setStaffEmail(rs.getString("staff_email"));
            staff.setStaffRole(rs.getInt("staff_role"));
            staff.setStaffSdt(rs.getString("staff_sdt"));
            staff.setStaffCode(rs.getString("staff_code"));
            staff.setStaff_status(rs.getInt("staff_status"));
            return staff;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Staff findByEmail(String email) {
        String sql = "SELECT * FROM Staff WHERE staff_email = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToStaff, email);
    }

    @Override
    public boolean existsByEmail(String email) {
//        String sql = "SELECT COUNT(*) FROM Staff WHERE staff_email = ?";
//        Integer count = XJdbc.getValue(sql, email);
//        return count != null && count > 0;
        return false;
//        String sql = "SELECT COUNT(*) FROM Staff WHERE staff_email = ?";
//        Integer count = XJdbc.getValue(sql, email);
//        return count != null && count > 0;
    }

    @Override
    public boolean existsByUsername(String username) {
//        String sql = "SELECT COUNT(*) FROM Staff WHERE staff_username = ?";
//        Integer count = XJdbc.getValue(sql, username);
//        return count != null && count > 0;
        return false;
//        String sql = "SELECT COUNT(*) FROM Staff WHERE staff_username = ?";
//        Integer count = XJdbc.getValue(sql, username);
//        return count != null && count > 0;
    }

    @Override
    public List<Staff> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }

        String searchPattern = "%" + keyword.trim() + "%";
        String sql = "SELECT * FROM Staff WHERE "
                + "staff_username LIKE ? OR "
                + "staff_email LIKE ? OR "
                + "staff_sdt LIKE ? OR "
                + "staff_code LIKE ? "
                + "ORDER BY staff_id DESC";

        return XJdbc.query(sql, this::mapResultSetToStaff,
                searchPattern, searchPattern, searchPattern, searchPattern);
    }

    @Override
    public List<Staff> findByRole(int role) {
        String sql = "SELECT * FROM Staff WHERE staff_role = ? ORDER BY staff_username";
        return XJdbc.query(sql, this::mapResultSetToStaff, role);
    }

    @Override
    public Staff create(Staff entity) {
        String sql = "INSERT INTO Staff (staff_username, staff_password, staff_email, staff_role, staff_sdt, staff_code, staff_status) "
                + "OUTPUT INSERTED.* "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return XJdbc.queryForObject(sql, this::mapResultSetToStaff,
                entity.getStaffUsername(),
                entity.getStaffPassword(),
                entity.getStaffEmail(),
                entity.getStaffRole(),
                entity.getStaffSdt(),
                entity.getStaffCode(),
                entity.getStaff_status()
        );
    }

    @Override
    public Staff update(Staff entity) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = XJdbc.openConnection();
            conn.setAutoCommit(false);

            String sql = "UPDATE Staff SET staff_username = ?, staff_password = ?, staff_email = ?, staff_role = ?, staff_sdt = ?, staff_code = ?, staff_status = ? WHERE staff_id = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, entity.getStaffUsername());
            stmt.setString(2, entity.getStaffPassword());
            stmt.setString(3, entity.getStaffEmail());
            stmt.setInt(4, entity.getStaffRole());  
            stmt.setString(5, entity.getStaffSdt());
            stmt.setString(6, entity.getStaffCode());
            stmt.setInt(7, entity.getStaff_status());
            stmt.setInt(8, entity.getStaffId());  

            int updated = stmt.executeUpdate();
            conn.commit();

            if (updated > 0) {
                return findById(entity.getStaffId());
            }
            return null;

        } catch (SQLException ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error rolling back transaction", e);
            }
            throw new RuntimeException("Error updating staff", ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error cleaning up resources", e);
            }
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM Staff WHERE staff_id = ?";
        return XJdbc.executeUpdate(sql, id) > 0;
    }

    @Override
    public List<Staff> findAll() {
        String sql = "SELECT * FROM Staff ORDER BY staff_id DESC";
        return XJdbc.query(sql, this::mapResultSetToStaff);
    }

    @Override
    public Staff findById(Integer id) {
        String sql = "SELECT * FROM Staff WHERE staff_id = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToStaff, id);
    }

    @Override
    public Staff findByUsername(String username) {
        String sql = "SELECT * FROM Staff WHERE staff_username = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToStaff, username);
    }

    @Override
    public long count() {
//        String sql = "SELECT COUNT(*) FROM Staff";
//        Long count = XJdbc.getValue(sql);
//        return count != null ? count : 0;
        return 0;
//        String sql = "SELECT COUNT(*) FROM Staff";
//        Long count = XJdbc.getValue(sql);
//        return count != null ? count : 0;
    }

    @Override
    public List<Staff> findWithPagination(int page, int pageSize, String filter) {
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM Staff "
                + "WHERE staff_username LIKE ? OR staff_email LIKE ? "
                + "ORDER BY staff_id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        String searchPattern = "%" + (filter != null ? filter : "") + "%";
        return XJdbc.query(sql, this::mapResultSetToStaff,
                searchPattern, searchPattern, offset, pageSize);
    }

    @Override
    public Staff findByStaffCode(String staffCode) {
        String sql = "SELECT * FROM Staff WHERE staff_code = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToStaff, staffCode);
    }
}
