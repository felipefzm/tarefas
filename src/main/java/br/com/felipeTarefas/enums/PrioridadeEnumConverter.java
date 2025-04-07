package br.com.felipeTarefas.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PrioridadeEnumConverter implements AttributeConverter<PrioridadeEnum, String> {

    @Override
    public String convertToDatabaseColumn(PrioridadeEnum prioridadeEnum) {
        return prioridadeEnum != null? prioridadeEnum.getName(): "UM";
    }

    @Override
    public PrioridadeEnum convertToEntityAttribute(String prioridadeEnum) {
        for (PrioridadeEnum prioridadeEnum2 : PrioridadeEnum.values()) {
            if (prioridadeEnum2.getName().equals(prioridadeEnum)) {
                return prioridadeEnum2;
            }
        } return PrioridadeEnum.UM;
       
    }

}
