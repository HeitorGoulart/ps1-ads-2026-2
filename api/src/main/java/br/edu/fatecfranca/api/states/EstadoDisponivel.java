package br.edu.fatecfranca.api.states;

public class EstadoDisponivel implements EstadoVeiculo {
    @Override
    public void processarVenda() {
        System.out.println("[STATE] O carro estava disponível e a venda foi autorizada.");
    }
}