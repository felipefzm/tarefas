package br.com.felipeTarefas.enums;

public enum StatusEnum {
    PENDENTE("PENDENTE"),
    EM_ANDAMENTO("ANDAMENTO"),
    CONCLUÍDA ("CONCLUÍDA");

    private final String nameStatus;

    private StatusEnum(String nameStatus){
        this.nameStatus = nameStatus;
    }

    public String getNameStatus(){
        return nameStatus;
    }
}
