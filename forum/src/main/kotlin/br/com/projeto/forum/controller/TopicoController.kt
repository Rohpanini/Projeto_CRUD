package br.com.projeto.forum.controller

import br.com.projeto.forum.dto.AtualizacaoTopicoForm
import br.com.projeto.forum.dto.NovoTopicoForm
import br.com.projeto.forum.dto.TopicoPorCategoria
import br.com.projeto.forum.dto.TopicoView
import br.com.projeto.forum.service.TopicoService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/topicos")
class TopicoController(
    private val serviceTopico: TopicoService
) {

    //LISTA TODOS OS TOPICOS
    @GetMapping
    @Cacheable("topicos")
    fun listar(@RequestParam(required = false) nomeCurso: String?,
               @PageableDefault(size=3, sort = ["dataCriacao"], direction = Sort.Direction.DESC) page: Pageable
    ): Page<TopicoView> {
        return serviceTopico.listar(nomeCurso, page)
    }

    //BUSCA COM GET PARA UM ÚNICO ID
    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id:Long): TopicoView{
        return serviceTopico.buscarPorId(id)
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun cadastrar(
        @RequestBody @Valid form: NovoTopicoForm,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<TopicoView>{
        val topicoView = serviceTopico.cadastar(form)
        val uri = uriBuilder.path("/topicos/${topicoView.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicoView)
    }

    @PutMapping
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun atualizar(@RequestBody @Valid form: AtualizacaoTopicoForm) :ResponseEntity<TopicoView>{
        val topicoView = serviceTopico.atualizar(form)
        return ResponseEntity.ok().body(topicoView)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun deletar(@PathVariable id:Long){
        serviceTopico.deletar(id)
    }

    @GetMapping("/relatorio")
    fun relatorio():List<TopicoPorCategoria>{
        return serviceTopico.relatorio()
    }
}