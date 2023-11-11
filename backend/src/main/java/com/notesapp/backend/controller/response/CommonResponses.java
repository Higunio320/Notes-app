package com.notesapp.backend.controller.response;

import com.notesapp.backend.exception.NoteExceptionResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.notesapp.backend.entity.Note;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@ApiResponse(
        responseCode = "200",
        description = "Success",
        content = {@Content(schema = @Schema(implementation = Note.class), mediaType = "application/json")})
@ApiResponse(
        responseCode = "400",
        description = "Failure",
        content = {@Content(schema = @Schema(implementation = NoteExceptionResponse.class), mediaType = "application/json")}
)
public @interface CommonResponses {
}
