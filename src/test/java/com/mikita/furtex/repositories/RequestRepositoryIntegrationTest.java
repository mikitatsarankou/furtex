package com.mikita.furtex.repositories;

import com.mikita.furtex.TestDataUtil;
import com.mikita.furtex.domain.Material;
import com.mikita.furtex.domain.Request;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class RequestRepositoryIntegrationTest {
    private RequestRepository underTest;
    private MaterialRepository materialRepository;

    @Autowired
    public RequestRepositoryIntegrationTest(RequestRepository underTest, MaterialRepository materialRepository) {
        this.underTest = underTest;
        this.materialRepository = materialRepository;
    }

    @Test
    public void createAndGetRequest_validDataPassed_requestReturned() {
        Material material = TestDataUtil.createTestMaterial();
        Request request = TestDataUtil.createTestRequest(Optional.of(material));

        underTest.save(request);

        Optional<Request> result = underTest.findById(request.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(request);
    }

    @Test
    public void createAndGetMultipleRequests_dataExists_requestsReturned() {
        List<Request> requests = new ArrayList<>();
        int numberOfNewRecords = 4;
        for (int counter = 0; counter < numberOfNewRecords; counter++) {
            Material material = TestDataUtil.createTestMaterial();
            materialRepository.save(material);
        }
        List<Material> materialList = (List<Material>)materialRepository.findAll();
        for (Material material : materialList) {
            Request request = TestDataUtil.createTestRequest(Optional.of(material));
            requests.add(request);
        }
        underTest.saveAll(requests);
        Iterable<Request> requestListFinal = underTest.findAll();
        assertThat(requestListFinal).hasSize(4).containsAll(requests);
    }

//
//    @Test
//    public void updateRequest_validDataPassed_requestUpdated() {
//        Material material = TestDataUtil.createTestMaterial();
//        Long materialId = materialDao.create(material);
//
//        Request request = TestDataUtil.createTestRequest(Optional.ofNullable(materialId));
//        Long requestId = underTest.create(request);
//
//        request.setId(requestId);
//        request.setComment("Updated comment");
//
//        underTest.update(requestId, request);
//        Optional<Request> updatedRequest = underTest.findOne(requestId);
//
//        assertThat(updatedRequest).isPresent();
//        assertThat(updatedRequest.get()).isEqualTo(request);
//    }
//
//    @Test
//    public void deleteRequest_dataExist_requestDeleted() {
//        Material material = TestDataUtil.createTestMaterial();
//        Long materialId = materialDao.create(material);
//
//        Request request = TestDataUtil.createTestRequest(Optional.of(materialId));
//        Long requestId = underTest.create(request);
//        request.setId(requestId);
//
//        underTest.delete(requestId);
//        Optional<Request> updatedRequest = underTest.findOne(requestId);
//
//        assertThat(updatedRequest).isEmpty();
//    }
}
