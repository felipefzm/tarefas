package br.com.felipeTarefas.enums;

public enum PrioridadeEnum {
UM("1"), 
DOIS("2"), 
TRÊS("3"),;

private final String name;

private PrioridadeEnum(String name){
    this.name = name;
}

public String getName(){
    return name;
}
}
