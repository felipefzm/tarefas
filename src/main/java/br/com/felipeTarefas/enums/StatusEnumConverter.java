package br.com.felipeTarefas.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusEnumConverter implements AttributeConverter<StatusEnum, String>{

    @Override
    public String convertToDatabaseColumn(StatusEnum statusEnum) {
       return statusEnum != null? statusEnum.getNameStatus(): "1";
    }

    @Override
    public StatusEnum convertToEntityAttribute(String dbData) {
        for (StatusEnum statusEnum : StatusEnum.values()) {
            if (statusEnum.getNameStatus().equals(dbData)) {
                return statusEnum;               
            }
        } return StatusEnum.PENDENTE;
    }

    
    

}
