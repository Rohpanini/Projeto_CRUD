package br.com.projeto.forum.service


import br.com.projeto.forum.model.Usuario
import br.com.projeto.forum.repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service
data class UsuarioService(private val repository: UsuarioRepository) {

    fun buscarPorId(id: Long):Usuario{
        return repository.getOne(id)
    }
}