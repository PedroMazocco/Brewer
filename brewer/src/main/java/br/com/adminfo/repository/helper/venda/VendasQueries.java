package br.com.adminfo.repository.helper.venda;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.adminfo.dto.VendasMes;
import br.com.adminfo.dto.VendasOrigem;
import br.com.adminfo.model.Venda;
import br.com.adminfo.repository.filter.VendaFilter;

public interface VendasQueries {
	
	public Page<Venda> filtrar(VendaFilter vendaFilter, Pageable pageable);
	
	public BigDecimal valorTotalNoAno();
	
	public BigDecimal valorTotalNoMes();
	
	public BigDecimal valorTicketMedioNoAno();
	
	public List<VendasMes> totalPorMes();
	
	public List<VendasOrigem> totalPorOrigem();
	
	public Venda buscarComItens(Long codigo);

}
