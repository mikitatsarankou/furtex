package com.mikita.furtex.dao;

import com.mikita.furtex.domain.Material;
import com.mikita.furtex.domain.Request;

import java.util.List;
import java.util.Optional;

public interface RequestDao {
    Long create(Request request);

    Optional<Request> findOne(Long id);

    List<Request> findAll();

    Request update(Long id, Request request);

    void delete(Long id);
}
