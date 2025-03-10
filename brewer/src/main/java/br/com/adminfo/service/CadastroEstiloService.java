package br.com.adminfo.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.adminfo.model.Estilo;
import br.com.adminfo.repository.Estilos;
import br.com.adminfo.service.exception.ImpossivelExcluirEntidadeException;
import br.com.adminfo.service.exception.NomeEstiloJaCadastradoException;

@Service
public class CadastroEstiloService {
	
	@Autowired
	private Estilos estilos;
	
	@Transactional
	public Estilo salvar(Estilo estilo) {	
		Optional<Estilo> estiloOptional = estilos.findByNomeIgnoreCase(estilo.getNome());
		if(estiloOptional.isPresent()) {
			throw new NomeEstiloJaCadastradoException("Nome do estilo já está cadastrado");
		}
		
		return estilos.saveAndFlush(estilo);
	}

	@Transactional
	public void excluir(Estilo estilo) {
		try {
			estilos.delete(estilo);
			estilos.flush();
		} catch (PersistenceException e){
			throw new ImpossivelExcluirEntidadeException
			("Impossivel apagar estilo. Já foi usado em alguma venda");
		}
		
	}

}