package com.mikita.furtex.repositories;

import com.mikita.furtex.TestDataUtil;
import com.mikita.furtex.domain.Material;
import com.mikita.furtex.domain.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
        Request request = TestDataUtil.createTestRequest();

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
            Request request = TestDataUtil.createTestRequest();
            requests.add(request);
        }
        underTest.saveAll(requests);
        Iterable<Request> requestListFinal = underTest.findAll();

        assertThat(requestListFinal).hasSize(4).containsAll(requests);
    }


    @Test
    public void updateRequest_validDataPassed_requestUpdated() {
        Request request = TestDataUtil.createTestRequest();
        underTest.save(request);

        request.setComment("Updated comment");

        underTest.save(request);
        Optional<Request> updatedRequest = underTest.findById(request.getId());

        assertThat(updatedRequest).isPresent();
        assertThat(updatedRequest.get()).isEqualTo(request);
    }

    @Test
    public void deleteRequest_dataExist_requestDeleted() {
        Request request = TestDataUtil.createTestRequest();

        underTest.save(request);

        Optional<Request> result = underTest.findById(request.getId());

        assertThat(result).isPresent();

        underTest.deleteById(request.getId());
        Optional<Request> resultAfterDelete = underTest.findById(request.getId());
        assertThat(resultAfterDelete).isEmpty();
    }
}
