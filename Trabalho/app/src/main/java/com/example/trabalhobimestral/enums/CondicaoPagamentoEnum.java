package com.example.trabalhobimestral.enums;

public enum CondicaoPagamentoEnum {
    ZERO("á vista"),
    UM("á prazo");
    private String descricao;
    CondicaoPagamentoEnum(String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return descricao;
    }
}
