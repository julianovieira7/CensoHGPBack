package br.com.unitins.censohgp.model.enums;

public enum Genero {

    Feminino(1, "FEMININO"),
    Masculino(2, "MASCULINO");

    private int cod;
    private String descricao;

    private Genero(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao () {
        return descricao;
    }

    public static Genero toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (Genero x : Genero.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
