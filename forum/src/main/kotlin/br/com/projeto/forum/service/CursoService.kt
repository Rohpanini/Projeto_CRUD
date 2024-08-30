package br.com.projeto.forum.service

import br.com.projeto.forum.model.Curso
import br.com.projeto.forum.repository.CursoRepository
import org.springframework.stereotype.Service

@Service
class CursoService(private val repository: CursoRepository) {

    fun buscarPorId(id: Long): Curso {
        return repository.getOne(id)
    }
}
