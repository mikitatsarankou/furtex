package com.mikita.furtex.repositories;

import com.mikita.furtex.TestDataUtil;
import com.mikita.furtex.domain.Material;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
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
public class MaterialRepositoryIntegrationTest {

    private MaterialRepository underTest;

    @Autowired
    public MaterialRepositoryIntegrationTest(@Qualifier("materialRepository")MaterialRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void createAndGetMaterial_validDataPassed_materialReturned() {
        Material material = TestDataUtil.createTestMaterial();
        underTest.save(material);

        Optional<Material> result = underTest.findById(material.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(material);
    }

    @Test
    public void createAndGetMultipleMaterials_dataExists_materialsReturned() {
        List<Material> materials = new ArrayList<>();

        int numberOfNewRecords = 4;
        for (int counter = 0; counter < numberOfNewRecords; counter++) {
            Material material = TestDataUtil.createTestMaterial();
            materials.add(material);
        }
        underTest.saveAll(materials);
        Iterable<Material> materialListFinal = underTest.findAll();

        assertThat(materialListFinal).hasSize(4);
    }

    @Test
    public void updateMaterial_validDataPassed_materialUpdated() {
        Material material = TestDataUtil.createTestMaterial();
        underTest.save(material);

        material.setName("Updated name");

        underTest.save(material);
        Optional<Material> updatedMaterial = underTest.findById(material.getId());

        assertThat(updatedMaterial).isPresent();
        assertThat(updatedMaterial.get()).isEqualTo(material);
    }

    @Test
    public void deleteMaterial_dataExist_materialDeleted() {
        Material material = TestDataUtil.createTestMaterial();
        underTest.save(material);

        Optional<Material> result = underTest.findById(material.getId());

        assertThat(result).isPresent();

        underTest.deleteById(material.getId());
        Optional<Material> resultAfterDelete = underTest.findById(material.getId());
        assertThat(resultAfterDelete).isEmpty();
    }
}
