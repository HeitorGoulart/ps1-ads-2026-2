package br.edu.fatecfranca.api.utils;

public class AuditoriaLogger {
    private static AuditoriaLogger instanciaUnica;

    private AuditoriaLogger() {}

    public static AuditoriaLogger getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new AuditoriaLogger();
        }
        return instanciaUnica;
    }

    public void registrarLog(String acao, String entidade) {
        System.out.println("=========================================");
        System.out.println("[AUDITORIA SINGLETON] Ação: " + acao + " | Alvo: " + entidade);
        System.out.println("=========================================");
    }
}