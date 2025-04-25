package com.devtrack.notes.application.mapper.input;

import com.devtrack.notes.domain.entity.NoteEntity;
import com.devtrack.notes.infraestructure.controller.dto.input.NoteInputFullDto;
import com.devtrack.notes.infraestructure.controller.dto.input.NoteInputSimpleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteInputMapper {
    NoteEntity inputSimpleDtoToEntity(NoteInputSimpleDto noteInputSimpleDto);
    NoteEntity inputFullDtoToEntity(NoteInputFullDto noteInputFullDto);
}
