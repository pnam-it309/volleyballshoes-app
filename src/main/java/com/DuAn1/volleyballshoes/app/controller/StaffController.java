package com.DuAn1.volleyballshoes.app.controller;

import com.DuAn1.volleyballshoes.app.dao.StaffDAO;
import com.DuAn1.volleyballshoes.app.dto.request.StaffCreateRequest;
import com.DuAn1.volleyballshoes.app.dto.request.StaffUpdateRequest;
import com.DuAn1.volleyballshoes.app.dto.response.StaffResponse;
import com.DuAn1.volleyballshoes.app.entity.Staff;
import com.DuAn1.volleyballshoes.app.dao.impl.StaffDAOImpl;
import com.DuAn1.volleyballshoes.app.utils.NotificationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.JFrame;
public class StaffController {
    private StaffDAO staffDAO;
    private JFrame parentFrame;
    
    public StaffController() {
        this(null);
    }
    
    public StaffController(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        try {
            this.staffDAO = new StaffDAOImpl();
        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Không thể khởi tạo StaffController: " + e.getMessage());
        }
    }

    public List<StaffResponse> getAllStaff() {
        if (staffDAO == null) {
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return new ArrayList<>();
        }
        try {
            return staffDAO.findAll().stream()
                    .map(this::mapToStaffResponse)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Lỗi khi tải danh sách nhân viên: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public StaffResponse getStaffById(Integer id) {
        if (staffDAO == null) {
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return null;
        }
        
        try {
            Staff staff = staffDAO.findById(id);
            if (staff == null) {
                NotificationUtil.showError(parentFrame, "Không tìm thấy nhân viên với ID: " + id);
                return null;
            }
            return mapToStaffResponse(staff);
        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Lỗi khi tải thông tin nhân viên: " + e.getMessage());
            return null;
        }
    }

    public StaffResponse createStaff(StaffCreateRequest request) {
        if (staffDAO == null) {
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return null;
        }
        
        try {
            if (staffDAO.existsByEmail(request.getEmail())) {
                NotificationUtil.showError(parentFrame, "Email đã được sử dụng");
                return null;
            }

            Staff staff = new Staff();
            staff.setStaffFullName(request.getName());
            staff.setStaffEmail(request.getEmail());
            // TODO: Thêm mã hóa mật khẩu nếu cần
            staff.setStaffPassword(request.getPassword());
            staff.setStaffSdt(request.getPhone());
            staff.setStaffRole(0);
            staff.setStaffCode("NV" + System.currentTimeMillis()); // Tạo mã nhân viên tự động

            Staff savedStaff = staffDAO.create(staff);
            NotificationUtil.showSuccess(parentFrame, "Tạo nhân viên mới thành công");
            return mapToStaffResponse(savedStaff);
        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Lỗi khi tạo nhân viên: " + e.getMessage());
            return null;
        }
    }

    public StaffResponse updateStaff(Integer id, StaffUpdateRequest request) {
        if (staffDAO == null) {
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return null;
        }
        
        try {
            Staff staff = staffDAO.findById(id);
            if (staff == null) {
                NotificationUtil.showError(parentFrame, "Không tìm thấy nhân viên với ID: " + id);
                return null;
            }

            if (request.getName() != null) {
                staff.setStaffFullName(request.getName());
            }
            if (request.getPhone() != null) {
                staff.setStaffSdt(request.getPhone());
            }
            if (request.getRole() != null && !request.getRole().isEmpty()) {
                try {
                    staff.setStaffRole(Integer.parseInt(request.getRole()));
                } catch (NumberFormatException e) {
                    NotificationUtil.showError(parentFrame, "Vai trò không hợp lệ");
                    return null;
                }
            }

            Staff updatedStaff = staffDAO.update(staff);
            NotificationUtil.showSuccess(parentFrame, "Cập nhật thông tin nhân viên thành công");
            return mapToStaffResponse(updatedStaff);
        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Lỗi khi cập nhật thông tin nhân viên: " + e.getMessage());
            return null;
        }
    }

    public boolean deleteStaff(Integer id) {
        if (staffDAO == null) {
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return false;
        }
        
        try {
            if (staffDAO.findById(id) == null) {
                NotificationUtil.showError(parentFrame, "Không tìm thấy nhân viên với ID: " + id);
                return false;
            }
            staffDAO.deleteById(id);
            NotificationUtil.showSuccess(parentFrame, "Đã xóa nhân viên thành công");
            return true;
        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Lỗi khi xóa nhân viên: " + e.getMessage());
            return false;
        }
    }

    private StaffResponse mapToStaffResponse(Staff staff) {
        if (staff == null) {
            return null;
        }
        return StaffResponse.builder()
                .id((long)staff.getStaffId())
                .name(staff.getStaffFullName())
                .email(staff.getStaffEmail())
                .phone(staff.getStaffSdt())
                .role(String.valueOf(staff.getStaffRole()))
                .build();
    }
}
