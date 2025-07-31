package com.DuAn1.volleyballshoes.app.controller;

import com.DuAn1.volleyballshoes.app.dao.StaffDAO;
import com.DuAn1.volleyballshoes.app.dto.request.StaffCreateRequest;
import com.DuAn1.volleyballshoes.app.dto.request.StaffUpdateRequest;
import com.DuAn1.volleyballshoes.app.dto.response.StaffResponse;
import com.DuAn1.volleyballshoes.app.entity.Staff;
import com.DuAn1.volleyballshoes.app.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class StaffController {
    private final StaffDAO staffDAO;
    private final PasswordEncoder passwordEncoder;

    public List<StaffResponse> getAllStaff() {
        return staffDAO.findAll().stream()
                .map(this::mapToStaffResponse)
                .collect(Collectors.toList());
    }

    public StaffResponse getStaffById(Long id) {
        return staffDAO.findById(id)
                .map(this::mapToStaffResponse)
                .orElseThrow(() -> new BusinessException("Không tìm thấy nhân viên với ID: " + id));
    }

    public StaffResponse createStaff(StaffCreateRequest request) {
        if (staffDAO.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email đã được sử dụng");
        }

        Staff staff = new Staff();
        staff.setName(request.getName());
        staff.setEmail(request.getEmail());
        staff.setPassword(passwordEncoder.encode(request.getPassword()));
        staff.setPhone(request.getPhone());
        staff.setAddress(request.getAddress());
        staff.setRole(request.getRole());
        staff.setActive(true);

        Staff savedStaff = staffDAO.save(staff);
        return mapToStaffResponse(savedStaff);
    }

    public StaffResponse updateStaff(Long id, StaffUpdateRequest request) {
        Staff staff = staffDAO.findById(id)
                .orElseThrow(() -> new BusinessException("Không tìm thấy nhân viên với ID: " + id));

        if (request.getName() != null) {
            staff.setName(request.getName());
        }
        if (request.getPhone() != null) {
            staff.setPhone(request.getPhone());
        }
        if (request.getAddress() != null) {
            staff.setAddress(request.getAddress());
        }
        if (request.getRole() != null) {
            staff.setRole(request.getRole());
        }
        if (request.getActive() != null) {
            staff.setActive(request.getActive());
        }

        Staff updatedStaff = staffDAO.save(staff);
        return mapToStaffResponse(updatedStaff);
    }

    public void deleteStaff(Long id) {
        if (!staffDAO.existsById(id)) {
            throw new BusinessException("Không tìm thấy nhân viên với ID: " + id);
        }
        staffDAO.deleteById(id);
    }

    private StaffResponse mapToStaffResponse(Staff staff) {
        return StaffResponse.builder()
                .id(staff.getId())
                .name(staff.getName())
                .email(staff.getEmail())
                .phone(staff.getPhone())
                .address(staff.getAddress())
                .role(staff.getRole())
                .active(staff.isActive())
                .build();
    }
}
