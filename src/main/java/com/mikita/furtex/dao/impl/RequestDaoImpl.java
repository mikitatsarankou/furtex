package com.mikita.furtex.dao.impl;

import com.mikita.furtex.dao.RequestDao;
import com.mikita.furtex.domain.Request;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Component
public class RequestDaoImpl implements RequestDao {

    private final JdbcTemplate jdbcTemplate;

    public RequestDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long create(Request request) {
        if (request.getId() == null) {
            String sql = "INSERT INTO requests (material_id, comment, first_name, last_name, phone_number) VALUES (?,?,?,?,?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        ps.setLong(1, request.getMaterialId());
                        ps.setString(2, request.getComment());
                        ps.setString(3, request.getFirstName());
                        ps.setString(4, request.getLastName());
                        ps.setString(5, request.getPhoneNumber());
                        return ps;
                    },
                    keyHolder
            );

            return keyHolder.getKey().longValue();
        }

        jdbcTemplate.update(
                "INSERT INTO requests (id, material_id, comment, first_name, last_name, phone_number) VALUES (?,?,?,?,?,?)",
                request.getId(),
                request.getMaterialId(),
                request.getComment(),
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber()
        );

        return request.getId();
    }

    @Override
    public Optional<Request> findOne(Long requestId) {
        List<Request> results = jdbcTemplate.query(
                "SELECT id, material_id, comment, first_name, last_name, phone_number FROM requests WHERE id = ? LIMIT 1",
                new RequestRowMapper(), requestId
        );

        return results.stream().findFirst();
    }

    @Override
    public List<Request> findAll() {
        return jdbcTemplate.query(
                "SELECT id, material_id, comment, first_name, last_name, phone_number FROM requests",
                new RequestRowMapper()
        );
    }

    @Override
    public Request update(Long id, Request request) {
        jdbcTemplate.update(
                "UPDATE requests SET id = ?, comment = ?, first_name = ?, last_name = ?, phone_number = ?, material_id = ? WHERE id = ?",
                id, request.getComment(), request.getFirstName(), request.getLastName(), request.getPhoneNumber(), request.getMaterialId(), id
        );

        return request;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(
                "DELETE FROM requests WHERE id = ?",
                id
        );
    }

    public static class RequestRowMapper implements RowMapper<Request> {

        @Override
        public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Request.builder()
                    .id(rs.getLong("id"))
                    .materialId(rs.getLong("material_id"))
                    .comment(rs.getString("comment"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .phoneNumber(rs.getString("phone_number"))
                    .build();
        }
    }
}
