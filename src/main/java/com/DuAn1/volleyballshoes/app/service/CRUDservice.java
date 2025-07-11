/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DuAn1.volleyballshoes.app.service;

import java.util.List;

/**
 *
 * @author nickh
 */
public interface CRUDservice<T, ID> {

    T create(T entity);

    void update(T entity);

    void deleteById(ID id);

    List<T> findAll();

    T findById(ID id);
}
