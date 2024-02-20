package com.mikita.furtex;

import com.mikita.furtex.domain.Material;
import com.mikita.furtex.domain.Request;

import java.util.Optional;

public final class TestDataUtil {

    private TestDataUtil() {

    }

    public static Material createTestMaterial() {
        return createTestMaterial(Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static Material createTestMaterial(Optional<Long> id) {
        return createTestMaterial(id, Optional.empty(), Optional.empty());
    }

    public static Material createTestMaterial(Optional<Long> id, Optional<String> name, Optional<String> description) {
        return Material.builder()
                .id(id.orElse(null))
                .name(name.orElse("Test name"))
                .description(description.orElse("Test description"))
                .build();
    }

    public static Request createTestRequest() {
        return createTestRequest(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static Request createTestRequest(Optional<Long> materialId) {
        return createTestRequest(Optional.empty(), materialId, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static Request createTestRequest(Optional<Long> id, Optional<Long> materialId) {
        return createTestRequest(id, materialId, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static Request createTestRequest(Optional<Long> materialId, Optional<String> comment, Optional<String> firstName, Optional<String> lastName, Optional<String> phoneNumber) {
        return createTestRequest(Optional.empty(), materialId, comment, firstName, lastName, phoneNumber);
    }

    public static Request createTestRequest(
            Optional<Long> id,
            Optional<Long> materialId,
            Optional<String> comment,
            Optional<String> firstName,
            Optional<String> lastName,
            Optional<String> phoneNumber
    ) {
        return Request.builder()
                .id(id.orElse(null))
                .comment(comment.orElse("Test comment"))
                .firstName(firstName.orElse("Test first name"))
                .lastName(lastName.orElse("Test last name"))
                .phoneNumber(phoneNumber.orElse("+123456789543"))
                .materialId(materialId.orElse(null))
                .build();
    }
}
