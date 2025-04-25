package com.devtrack.notes.application.mapper.output;

import com.devtrack.notes.domain.entity.NoteEntity;
import com.devtrack.notes.infraestructure.controller.dto.output.NoteOutputFullDto;
import com.devtrack.notes.infraestructure.controller.dto.output.NoteOutputSimpleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NoteOutputMapper {

    NoteOutputMapper noteOutputMapper = Mappers.getMapper(NoteOutputMapper.class);

    NoteOutputSimpleDto entityToOutputSimpleDto(NoteEntity noteEntity);
    NoteOutputFullDto entityToOutputFullDto(NoteEntity noteEntity);
}
