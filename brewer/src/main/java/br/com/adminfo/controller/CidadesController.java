package br.com.adminfo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.adminfo.controller.page.PageWrapper;
import br.com.adminfo.model.Cidade;
import br.com.adminfo.repository.Cidades;
import br.com.adminfo.repository.Estados;
import br.com.adminfo.repository.filter.CidadeFilter;
import br.com.adminfo.service.CadastroCidadeService;
import br.com.adminfo.service.exception.ImpossivelExcluirEntidadeException;
import br.com.adminfo.service.exception.NomeCidadeJaCadastradaException;

@Controller
@RequestMapping("/cidades")
public class CidadesController {
	
	@Autowired
	private Estados estados;
	
	@Autowired
	private Cidades cidades;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@RequestMapping("/nova")
	public ModelAndView nova(Cidade cidade) {
		ModelAndView mv = new ModelAndView("cidade/CadastroCidade");
		mv.addObject("estados", estados.findAll());
		return mv;
	}
	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisarPorEstadoCodigo(
			@RequestParam(name = "estado", defaultValue = "-1") Long codigoEstado){
		
		try{
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		
		return cidades.findByEstadoCodigo(codigoEstado);
	}
	
	@GetMapping
	public ModelAndView pesquisar(CidadeFilter cidadeFilter, BindingResult result
			, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("cidade/PesquisaCidades");
		mv.addObject("estados", estados.findAll());
		
		PageWrapper<Cidade> paginaWrapper = new PageWrapper<>(cidades.filtrar(cidadeFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@RequestMapping(value = { "/nova", "{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Cidade cidade, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return nova(cidade);
		}
		
		try {
			cadastroCidadeService.salvar(cidade);
		} catch (NomeCidadeJaCadastradaException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return nova(cidade);
		}
		
		attributes.addFlashAttribute("mensagem", "Cidade salva com sucesso!");
		return new ModelAndView("redirect:/cidades");
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Cidade cidade){
		try {
			cadastroCidadeService.excluir(cidade);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Cidade cidade) {
		ModelAndView mv = nova(cidade);
		mv.addObject(cidade);
		return mv;
	}

}
