package br.edu.fatecfranca.api.factories;
import br.edu.fatecfranca.api.entities.Car;

public class CarFactory {
    
    // Método de fábrica que monta o objeto Carro com regras de negócio
    public static Car prepararNovoCarro(Car carroRecebido) {
        // Se o modelo contiver "SUV", aplica uma regra VIP (exemplo de regra)
        if (carroRecebido.getModel() != null && carroRecebido.getModel().toUpperCase().contains("SUV")) {
            System.out.println("[FACTORY METHOD] SUV detetado. Aplicando selo Premium.");
            // Aqui você poderia alterar um campo de preço ou categoria, caso existam na sua classe Car
        } else {
            System.out.println("[FACTORY METHOD] Carro padrão processado pela fábrica.");
        }
        return carroRecebido;
    }
}