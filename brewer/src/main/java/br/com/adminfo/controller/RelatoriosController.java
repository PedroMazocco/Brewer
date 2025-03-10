package br.com.adminfo.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.adminfo.dto.CodigoProduto;
import br.com.adminfo.dto.PeriodoRelatorio;

@Controller
@RequestMapping("/relatorios")
public class RelatoriosController {
	
	@GetMapping("/vendasEmitidas")
	public ModelAndView relatorioVendasEmitidas() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioVendasEmitidas");
		mv.addObject(new PeriodoRelatorio());
		return mv;
	}
	
	@PostMapping("/vendasEmitidas")
	public ModelAndView gerarRelatorioVendasEmitidas(PeriodoRelatorio periodoRelatorio) {
		Map<String, Object> parametros = new HashMap<>();
		
		Date dataInicio = Date.from(LocalDateTime.of(periodoRelatorio.getDataInicio(), LocalTime.of(0, 0, 0))
				.atZone(ZoneId.systemDefault()).toInstant());
		Date dataTermino = Date.from(LocalDateTime.of(periodoRelatorio.getDataTermino(), LocalTime.of(23, 59, 59))
				.atZone(ZoneId.systemDefault()).toInstant());
		
		parametros.put("format", "pdf");
		parametros.put("dataInicio", dataInicio);
		parametros.put("dataTermino", dataTermino);
		
		return new ModelAndView("relatorio_vendas_emitidas", parametros);
	}
	
	@GetMapping("/comissaoProdutos")
	public ModelAndView relatorioComissaoProdutos() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioComissaoProduto");
		mv.addObject(new CodigoProduto());
		return mv;
	}
	
	@PostMapping("/comissaoProdutos")
	public ModelAndView gerarRelatorioComissao(CodigoProduto codigoProduto){
		Map<String, Object> parametros = new HashMap<>();
		
		parametros.put("format", "pdf");
		parametros.put("codigoInicial", codigoProduto.getCodigoInicial());
		parametros.put("codigoFinal", codigoProduto.getCodigoFinal());
		
		return new ModelAndView("relatorio_comissao_produto", parametros);
		
		}

}
