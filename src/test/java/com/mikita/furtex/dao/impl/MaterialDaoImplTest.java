package com.mikita.furtex.dao.impl;

import com.mikita.furtex.TestDataUtil;
import com.mikita.furtex.domain.Material;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class MaterialDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private MaterialDaoImpl underTest;

    @Test
    public void createMaterial_validDataPassed_materialCreated() {
        Material material = TestDataUtil.createTestMaterial(Optional.empty(), Optional.empty(), Optional.empty());
        material.setId(1L);

        underTest.create(material);

        verify(jdbcTemplate).update(
                eq("INSERT INTO materials (id, name, description) VALUES (?,?,?)"),
                eq(1L), eq("Test name"), eq("Test description")
        );
    }

    @Test
    public void getMaterial_dataExist_materialReturned() {
        underTest.findOne(1L);
        verify(jdbcTemplate).query(
                eq("SELECT id, name, description FROM materials WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<MaterialDaoImpl.MaterialRowMapper>any(),
                eq(1L)
        );
    }

    @Test
    public void getMaterials_dataExists_materialsReturned() {
        underTest.findAll();
        verify(jdbcTemplate).query(
                eq("SELECT id, name, description FROM materials"),
                ArgumentMatchers.<MaterialDaoImpl.MaterialRowMapper>any()
        );
    }

    @Test
    public void updateMaterial_dateExist_materialUpdated() {
        Material material = TestDataUtil.createTestMaterial(Optional.of(1L));
        underTest.update(material.getId(), material);

        verify(jdbcTemplate).update(
                "UPDATE materials SET id = ?, name = ?, description = ? WHERE id = ?",
                1L, "Test name", "Test description", 1L
        );
    }

    @Test
    public void deleteMaterial_dataExist_materialDeleted() {
        underTest.delete(1L);

        verify(jdbcTemplate).update(
                "DELETE FROM materials where id = ?",
                1L
        );
    }
}
