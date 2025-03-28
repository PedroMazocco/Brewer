package br.com.adminfo.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.adminfo.model.Usuario;
import br.com.adminfo.repository.filter.UsuarioFilter;

public interface UsuariosQueries {
	
	public Optional<Usuario> porEmailEAtivo(String email);
	
	public List<String> permissoes(Usuario usuario);
	
	public Page<Usuario> filtrar(UsuarioFilter clienteFilter, Pageable pageable);
	
	public 	Usuario buscarComGrupos(Long codigo);

}
