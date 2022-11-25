package br.com.unitins.censohgp.service;
import br.com.unitins.censohgp.security.UsuarioSS;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    public static UsuarioSS authenticated() {
        try {
            return (UsuarioSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }

        catch (Exception e) {
            return null;
        }
    }
}
