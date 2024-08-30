package br.com.projeto.forum.repository

import br.com.projeto.forum.dto.TopicoPorCategoria
import br.com.projeto.forum.model.Topico
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TopicoRepository: JpaRepository<Topico,Long> {

    fun findByCursoNome(nomeCurso: String, page: Pageable): Page<Topico>

    @Query("SELECT new br.com.projeto.forum.dto.TopicoPorCategoria(curso.categoria, count(t)) " +
            "FROM Topico t JOIN t.curso curso GROUP BY curso.categoria")
    fun relatorio(): List<TopicoPorCategoria>
}