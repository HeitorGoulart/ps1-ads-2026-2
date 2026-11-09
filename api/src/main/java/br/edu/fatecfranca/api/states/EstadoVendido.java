package br.edu.fatecfranca.api.states;

public class EstadoVendido implements EstadoVeiculo {
    @Override
    public void processarVenda() {
        System.out.println("[STATE] ERRO: Tentativa de vender um carro que já está vendido!");
        throw new IllegalStateException("Carro já vendido.");
    }
}