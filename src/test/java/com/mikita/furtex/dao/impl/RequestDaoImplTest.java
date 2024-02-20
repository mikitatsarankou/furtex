package com.mikita.furtex.dao.impl;

import com.mikita.furtex.TestDataUtil;
import com.mikita.furtex.dao.MaterialDao;
import com.mikita.furtex.domain.Material;
import com.mikita.furtex.domain.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class RequestDaoImplTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private RequestDaoImpl underTest;

    @Test
    public void createRequest_validDataPassed_requestCreated() {
        Request request = TestDataUtil.createTestRequest(Optional.of(15L), Optional.of(10L));
        underTest.create(request);

        verify(jdbcTemplate).update(
                eq("INSERT INTO requests (id, material_id, comment, first_name, last_name, phone_number) VALUES (?,?,?,?,?,?)"),
                eq(15L),
                eq(10L),
                eq("Test comment"),
                eq("Test first name"),
                eq("Test last name"),
                eq("+123456789543")
        );
    }

    @Test
    public void getRequest_dataExist_requestReturned() {
        underTest.findOne(15L);
        verify(jdbcTemplate).query(
                eq("SELECT id, material_id, comment, first_name, last_name, phone_number FROM requests WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<RequestDaoImpl.RequestRowMapper>any(),
                eq(15L)
        );
    }

    @Test
    public void getRequests_dataExists_requestsReturned() {
        underTest.findAll();

        verify(jdbcTemplate).query(
                eq("SELECT id, material_id, comment, first_name, last_name, phone_number FROM requests"),
                ArgumentMatchers.<RequestDaoImpl.RequestRowMapper>any()
        );
    }

    @Test
    public void updateRequest_dateExist_requestUpdated() {
        Request request = TestDataUtil.createTestRequest(Optional.of(1L), Optional.of(2L));
        underTest.update(request.getId(), request);

        verify(jdbcTemplate).update(
                "UPDATE requests SET id = ?, comment = ?, first_name = ?, last_name = ?, phone_number = ?, material_id = ? WHERE id = ?",
                1L, "Test comment", "Test first name", "Test last name", "+123456789543", 2L, 1L
        );
    }

    @Test
    public void deleteRequest_dataExist_requestDeleted() {
        underTest.delete(1L);

        verify(jdbcTemplate).update(
                "DELETE FROM requests WHERE id = ?",
                1L
        );
    }
}
