/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.Staff;
import java.util.List;

/**
 *
 * @author letha
 */
public interface StaffDAO  extends CrudDAO<Staff, Integer> {
    List<Staff> findAll();
}
