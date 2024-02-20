package com.mikita.furtex.dao.impl;

import com.mikita.furtex.TestDataUtil;
import com.mikita.furtex.domain.Material;
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
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MaterialDaoImplIntegrationTest {

    private MaterialDaoImpl underTest;

    @Autowired
    public MaterialDaoImplIntegrationTest(MaterialDaoImpl underTest) {
        this.underTest = underTest;
    }

    @Test
    public void createAndGetMaterial_validDataPassed_materialReturned() {
        Material material = TestDataUtil.createTestMaterial();
        Long materialId = underTest.create(material);
        material.setId(materialId);

        Optional<Material> result = underTest.findOne(materialId);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(material);
    }

    @Test
    public void createAndGetMultipleMaterials_dataExists_materialsReturned() {
        List<Material> materialListInit = underTest.findAll();

        int numberOfNewRecords = 4;
        for (int counter = 0; counter < numberOfNewRecords; counter++) {
            Material material = TestDataUtil.createTestMaterial(
                    Optional.empty(),
                    Optional.of("Test name " + counter),
                    Optional.of("Test description" + counter)
            );
            underTest.create(material);
        }
        List<Material> materialListFinal = underTest.findAll();
        assertThat(materialListInit.size() + numberOfNewRecords).isEqualTo(materialListFinal.size());
    }

    @Test
    public void updateMaterial_validDataPassed_materialUpdated() {
        Material material = TestDataUtil.createTestMaterial();
        Long materialId = underTest.create(material);

        material.setId(materialId);
        material.setName("Updated name");

        underTest.update(materialId, material);
        Optional<Material> updatedMaterial = underTest.findOne(materialId);

        assertThat(updatedMaterial).isPresent();
        assertThat(updatedMaterial.get()).isEqualTo(material);
    }

    @Test
    public void deleteMaterial_dataExist_materialDeleted() {
        Material material = TestDataUtil.createTestMaterial();
        Long materialId = underTest.create(material);
        material.setId(materialId);

        underTest.delete(materialId);
        Optional<Material> updatedMaterial = underTest.findOne(materialId);

        assertThat(updatedMaterial).isEmpty();
    }
}
