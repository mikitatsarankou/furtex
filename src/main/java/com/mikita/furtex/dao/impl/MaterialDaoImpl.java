package com.mikita.furtex.dao.impl;

import com.mikita.furtex.dao.MaterialDao;
import com.mikita.furtex.domain.Material;
import jakarta.persistence.Id;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class MaterialDaoImpl implements MaterialDao {

    private final JdbcTemplate jdbcTemplate;

    public MaterialDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long create(Material material) {
        if (material.getId() == null) {
            String sql = "INSERT INTO materials (name, description) VALUES (?,?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, material.getName());
                ps.setString(2, material.getDescription());
                return ps;
            }, keyHolder);

            return keyHolder.getKey().longValue();
        }

        jdbcTemplate.update(
                "INSERT INTO materials (id, name, description) VALUES (?,?,?)",
                material.getId(),
                material.getName(),
                material.getDescription()
        );

        return material.getId();
    }

    @Override
    public Optional<Material> findOne(Long materialId) {
        List<Material> results = jdbcTemplate.query(
                "SELECT id, name, description FROM materials WHERE id = ? LIMIT 1",
                new MaterialRowMapper(), materialId
        );

        return results.stream().findFirst();
    }

    @Override
    public List<Material> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, description FROM materials",
                new MaterialRowMapper()
        );
    }

    @Override
    public Material update(Long id, Material material) {
        jdbcTemplate.update(
                "UPDATE materials SET id = ?, name = ?, description = ? WHERE id = ?",
                id, material.getName(), material.getDescription(),  id
        );

        return material;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(
                "DELETE FROM materials where id = ?",
                id
        );
    }

    public static class MaterialRowMapper implements RowMapper<Material> {

        @Override
        public Material mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Material.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .build();
        }
    }
}
