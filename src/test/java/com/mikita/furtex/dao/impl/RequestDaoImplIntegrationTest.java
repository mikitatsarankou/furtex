package com.mikita.furtex.dao.impl;

import com.mikita.furtex.TestDataUtil;
import com.mikita.furtex.dao.MaterialDao;
import com.mikita.furtex.domain.Material;
import com.mikita.furtex.domain.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RequestDaoImplIntegrationTest {

    private RequestDaoImpl underTest;
    private MaterialDao materialDao;

    @Autowired
    public RequestDaoImplIntegrationTest(RequestDaoImpl underTest, MaterialDao materialDao) {
        this.underTest = underTest;
        this.materialDao = materialDao;
    }

    @Test
    public void createAndGetRequest_validDataPassed_requestReturned() {
        Material material = TestDataUtil.createTestMaterial();
        Long materialId = materialDao.create(material);

        Request request = TestDataUtil.createTestRequest();
        request.setMaterialId(materialId);

        Long requestId = underTest.create(request);
        request.setId(requestId);

        Optional<Request> result = underTest.findOne(requestId);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(request);
    }

    @Test
    public void createAndGetMultipleRequests_dataExists_requestsReturned() {
        Material material = TestDataUtil.createTestMaterial();
        Long materialId = materialDao.create(material);

        List<Request> requestListInit = underTest.findAll();

        int numberOfNewRecords = 4;
        for (int counter = 0; counter < numberOfNewRecords; counter++) {
            Request request = TestDataUtil.createTestRequest(
                    Optional.of(materialId),
                    Optional.of("Test comment " + counter),
                    Optional.of("Test first name " + counter),
                    Optional.of("Test last name " + counter),
                    Optional.of("+23456765455" + counter)
            );
            underTest.create(request);
        }
        List<Request> requestListFinal = underTest.findAll();
        assertThat(requestListInit.size() + numberOfNewRecords).isEqualTo(requestListFinal.size());
    }

    @Test
    public void updateRequest_validDataPassed_requestUpdated() {
        Material material = TestDataUtil.createTestMaterial();
        Long materialId = materialDao.create(material);

        Request request = TestDataUtil.createTestRequest(Optional.ofNullable(materialId));
        Long requestId = underTest.create(request);

        request.setId(requestId);
        request.setComment("Updated comment");

        underTest.update(requestId, request);
        Optional<Request> updatedRequest = underTest.findOne(requestId);

        assertThat(updatedRequest).isPresent();
        assertThat(updatedRequest.get()).isEqualTo(request);
    }

    @Test
    public void deleteRequest_dataExist_requestDeleted() {
        Material material = TestDataUtil.createTestMaterial();
        Long materialId = materialDao.create(material);

        Request request = TestDataUtil.createTestRequest(Optional.of(materialId));
        Long requestId = underTest.create(request);
        request.setId(requestId);

        underTest.delete(requestId);
        Optional<Request> updatedRequest = underTest.findOne(requestId);

        assertThat(updatedRequest).isEmpty();
    }
}
