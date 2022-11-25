package br.com.unitins.censohgp.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

@Data
@Getter
@Setter
public class CredenciaisDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String matricula;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;
    public CredenciaisDTO() {
    }

    public CredenciaisDTO(String matricula, String senha) {
        this.matricula = matricula;
        this.senha = senha;
    }
}
