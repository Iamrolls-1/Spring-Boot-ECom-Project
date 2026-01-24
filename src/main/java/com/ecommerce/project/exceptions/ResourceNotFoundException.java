package com.ecommerce.project.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String field;
    String fieldName;
    Long fieldId;

    public ResourceNotFoundException(String fieldName, String field, String resourceName) {
        super("Resource: " + resourceName + " with field name: " + fieldName + " and field: " + field + " not found");
        this.fieldName = fieldName;
        this.field = field;
        this.resourceName = resourceName;
    }

    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super("Resource: " + resourceName + " with field : " + field + " and field ID: " + fieldId + " not found");
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }
}
