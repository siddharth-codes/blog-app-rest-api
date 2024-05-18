package com.example.blogapprestapi.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    String resourceName;
    String fieldName;
    long fieldValue;
    public ResourceNotFoundException(String resourceName,String fieldName,long fieldValue){
        super(resourceName+" not found with "+fieldName+" : "+fieldValue );
        this.resourceName= resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
