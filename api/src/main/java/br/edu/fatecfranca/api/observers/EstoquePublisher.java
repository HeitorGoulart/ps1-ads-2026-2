package br.edu.fatecfranca.api.observers;
import br.edu.fatecfranca.api.entities.Car;
import java.util.ArrayList;
import java.util.List;

public class EstoquePublisher {
    private static EstoquePublisher instancia; // Misturando com Singleton para facilitar acesso
    private List<String> emailsInscritos = new ArrayList<>();

    private EstoquePublisher() {
        emailsInscritos.add("cliente.vip@karangos.com"); // Simulando um cliente inscrito
    }

    public static EstoquePublisher getInstance() {
        if (instancia == null) instancia = new EstoquePublisher();
        return instancia;
    }

    public void notificarNovoCarro(Car carro) {
        System.out.println("[OBSERVER] Disparando notificações...");
        for (String email : emailsInscritos) {
            System.out.println(" -> Enviando email para " + email + ": 'Novo carro disponível!'");
        }
    }
}