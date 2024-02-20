package com.mikita.furtex.dao;

import com.mikita.furtex.domain.Material;

import java.util.List;
import java.util.Optional;

public interface MaterialDao {
    Long create(Material material);

    Optional<Material> findOne(Long id);

    List<Material> findAll();

    Material update(Long id, Material material);

    void delete(Long id);
}
