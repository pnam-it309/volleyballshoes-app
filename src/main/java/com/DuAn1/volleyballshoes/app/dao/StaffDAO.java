package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.Staff;
import java.util.List;
import java.util.Optional;

public interface StaffDAO extends CrudDAO<Staff, Integer> {

    Optional<Staff> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Staff> search();

    List<Staff> findByRole();

    List<Staff> findByActive();

    List<Staff> findByRoleAndActive();
}
