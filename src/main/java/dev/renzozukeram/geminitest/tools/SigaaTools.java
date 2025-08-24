package dev.renzozukeram.geminitest.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SigaaTools {
    @Tool(name = "verificarSeTemVagaEmTurma", description = "Retorna a quantidade de vagas disponíveis em uma turma, quando o código da mesma é fornecido na forma de String")
    int verificarSeTemVagaEmTurma(@ToolParam(description = "é uma string na forma de: 3 caracteres e 4 números") String codigoTurma) {
        System.out.printf("Verificando se tem vaga na turma %s \n", codigoTurma);
        if (codigoTurma.equals("DIM0614")) {
            return 4;
        } else if (codigoTurma.equals("IMD0033")) {
            return 5;
        } else {
            return 10;
        }
    }

    @Tool(name = "realizarMatriculaDeAlunoEmTurma", description = "Realiza a matrícula do(a) aluno(a) em uma turma que tem vaga, dado que seja fornecido o número de matrícula do(a) aluno(a) e o código da turma como String")
    String realizarMatriculaDeAlunoEmTurma(@ToolParam(description = "É uma string no formato de 11 caracteres numéricos, onde os 4 primeiros representam o ano que o aluno ingressou na instituição") String matriculaAluno, String codigoTurma) {
        System.out.println("Matriculando aluno " + matriculaAluno + " na turma " + codigoTurma);
        return "O aluno " + matriculaAluno + " foi matriculado na turma " + codigoTurma + " com sucesso!" +
                "A data da matrícula foi " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ".";
    }
}
