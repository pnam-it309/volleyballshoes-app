package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.StaffDAO;
import com.DuAn1.volleyballshoes.app.entity.Staff;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StaffDAOImpl implements StaffDAO {
    
    private Staff mapResultSetToStaff(ResultSet rs) throws SQLException {
        Staff staff = new Staff();
        staff.setStaffId(rs.getInt("staff_id"));
        staff.setStaffUsername(rs.getString("staff_username"));
        staff.setStaffPassword(rs.getString("staff_password"));
        staff.setStaffFullName(rs.getString("staff_full_name"));
        staff.setStaffEmail(rs.getString("staff_email"));
        staff.setStaffRole(rs.getInt("staff_role"));
        staff.setStaffSdt(rs.getString("staff_sdt"));
        staff.setStaffCode(rs.getString("staff_code"));
        return staff;
    }

    @Override
    public Optional<Staff> findByEmail(String email) {
        String sql = "SELECT * FROM Staffs WHERE staff_email = ?";
        Staff staff = XJdbc.queryForObject(sql, this::mapResultSetToStaff, email);
        return Optional.ofNullable(staff);
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM Staffs WHERE staff_email = ?";
        Integer count = XJdbc.getValue(sql, email);
        return count != null && count > 0;
    }

    @Override
    public List<Staff> search() {
        // Triển khai tìm kiếm nếu cần
        return new ArrayList<>();
    }

    @Override
    public List<Staff> findByRole() {
        // Triển khai tìm kiếm theo vai trò nếu cần
        return new ArrayList<>();
    }

    @Override
    public List<Staff> findByActive() {
        // Triển khai tìm kiếm theo trạng thái hoạt động nếu cần
        return new ArrayList<>();
    }

    @Override
    public List<Staff> findByRoleAndActive() {
        // Triển khai tìm kiếm theo cả vai trò và trạng thái hoạt động nếu cần
        return new ArrayList<>();
    }

    @Override
    public Staff create(Staff entity) {
        String sql = "INSERT INTO Staffs (staff_username, staff_password, staff_full_name, "
                + "staff_email, staff_role, staff_sdt, staff_code) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        XJdbc.executeUpdate(sql, 
                entity.getStaffUsername(),
                entity.getStaffPassword(),
                entity.getStaffFullName(),
                entity.getStaffEmail(),
                entity.getStaffRole(),
                entity.getStaffSdt(),
                entity.getStaffCode()
        );
        
        // Lấy ID vừa tạo
        String getIdSql = "SELECT TOP 1 * FROM Staffs ORDER BY staff_id DESC";
        return XJdbc.queryForObject(getIdSql, this::mapResultSetToStaff);
    }

    @Override
    public Staff update(Staff entity) {
        String sql = "UPDATE Staffs SET "
                + "staff_username = ?, "
                + "staff_password = ?, "
                + "staff_full_name = ?, "
                + "staff_email = ?, "
                + "staff_role = ?, "
                + "staff_sdt = ?, "
                + "staff_code = ? "
                + "WHERE staff_id = ?";
        
        XJdbc.executeUpdate(sql, 
                entity.getStaffUsername(),
                entity.getStaffPassword(),
                entity.getStaffFullName(),
                entity.getStaffEmail(),
                entity.getStaffRole(),
                entity.getStaffSdt(),
                entity.getStaffCode(),
                entity.getStaffId()
        );
        
        return entity;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM Staffs WHERE staff_id = ?";
        int rowsAffected = XJdbc.executeUpdate(sql, id);
        return rowsAffected > 0;
    }

    @Override
    public List<Staff> findAll() {
        String sql = "SELECT * FROM Staffs";
        return XJdbc.query(sql, this::mapResultSetToStaff);
    }

    @Override
    public Staff findById(Integer id) {
        String sql = "SELECT * FROM Staffs WHERE staff_id = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToStaff, id);
    }
}
