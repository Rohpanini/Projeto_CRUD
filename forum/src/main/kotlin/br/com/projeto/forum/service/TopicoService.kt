package br.com.projeto.forum.service

import br.com.projeto.forum.dto.AtualizacaoTopicoForm
import br.com.projeto.forum.dto.NovoTopicoForm
import br.com.projeto.forum.dto.TopicoPorCategoria
import br.com.projeto.forum.dto.TopicoView
import br.com.projeto.forum.exception.NotFoundException
import br.com.projeto.forum.mapper.TopicoFormMapper
import br.com.projeto.forum.mapper.TopicoViewMapper
import br.com.projeto.forum.repository.TopicoRepository
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TopicoService(
    private val repository: TopicoRepository,
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
    private val notFoundMessage: String = "Topico não encontrado",
    private val em: EntityManager
    ) {

    fun listar(nomeCurso: String?, page: Pageable): Page<TopicoView> {
        print(em)
        val topicos = if (nomeCurso == null){
            repository.findAll(page)
            }else{
                repository.findByCursoNome(nomeCurso, page)
            }
        return topicos.map { t ->
            topicoViewMapper.map(t)
        }
    }

    fun buscarPorId(id: Long): TopicoView {
        val topico = repository.findById(id).orElseThrow{NotFoundException(notFoundMessage)}
        return topicoViewMapper.map(topico)
    }

    fun cadastar(form: NovoTopicoForm): TopicoView {
        val topico = topicoFormMapper.map(form)
        repository.save(topico)
        return topicoViewMapper.map(topico)
    }

    fun atualizar(form: AtualizacaoTopicoForm): TopicoView{
        val topico = repository.findById(form.id).orElseThrow{NotFoundException(notFoundMessage)}
        topico.titulo = form.titulo
        topico.mensagem = form.mensagem
        return topicoViewMapper.map(topico)
    }

    fun deletar(id: Long) {
       repository.deleteById(id)
    }

    fun relatorio():List<TopicoPorCategoria>{
        return repository.relatorio()
    }
}