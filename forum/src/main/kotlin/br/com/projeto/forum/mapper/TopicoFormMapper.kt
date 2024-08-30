package br.com.projeto.forum.mapper

import br.com.projeto.forum.dto.NovoTopicoForm
import br.com.projeto.forum.model.Topico
import br.com.projeto.forum.service.CursoService
import br.com.projeto.forum.service.UsuarioService
import org.springframework.stereotype.Component

@Component
class TopicoFormMapper(
    private val cursoService: CursoService,
    private val usuarioService: UsuarioService
): Mapper<NovoTopicoForm, Topico> {

    override fun map(t: NovoTopicoForm): Topico {
        return (Topico(
            titulo = t.titulo,
            mensagem = t.mensagem,
            curso = cursoService.buscarPorId(t.idCurso),
            autor = usuarioService.buscarPorId(t.idAutor)
        ))
    }
}
