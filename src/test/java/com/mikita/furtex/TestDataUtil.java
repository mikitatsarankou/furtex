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

    public static Request createTestRequest(Optional<Material> material) {
        return createTestRequest(Optional.empty(), material, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static Request createTestRequest(Optional<Long> id, Optional<Material> material) {
        return createTestRequest(id, material, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static Request createTestRequest(Optional<Material> material, Optional<String> comment, Optional<String> firstName, Optional<String> lastName, Optional<String> phoneNumber) {
        return createTestRequest(Optional.empty(), material, comment, firstName, lastName, phoneNumber);
    }

    public static Request createTestRequest(
            Optional<Long> id,
            Optional<Material> material,
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
                .material(material.orElse(null))
                .build();
    }
}
