package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioCancelamento implements ValidadorCancelamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        var horarioConsulta = repository.getReferenceById(dados.id()).getData();
        var horarioSolicitacao = LocalDateTime.now();
        var antecedencia = Duration.between(horarioSolicitacao, horarioConsulta).toHours();
        if (antecedencia < 24) {
            throw new ValidacaoException("Consulta somente poderá ser cancelada com antecedência mínima de 24 horas.");
        }
    }
}
