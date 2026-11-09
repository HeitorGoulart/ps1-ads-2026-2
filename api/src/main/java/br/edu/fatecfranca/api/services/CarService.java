package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.fatecfranca.api.entities.Car;
import br.edu.fatecfranca.api.repositories.CarRepository;

// --- IMPORTAÇÕES DOS PADRÕES DE PROJETO ---
import br.edu.fatecfranca.api.utils.AuditoriaLogger;
import br.edu.fatecfranca.api.factories.CarFactory;
import br.edu.fatecfranca.api.observers.EstoquePublisher;

@Service
public class CarService {

   private final CarRepository repository;

   public CarService(CarRepository repository) {
       this.repository = repository;
   }

   public Car create(Car car) {
       // 1. FACTORY METHOD: A fábrica aplica regras de negócio (ex: selo Premium) antes de salvar
       car = CarFactory.prepararNovoCarro(car);
       
       // Código original que salva no banco de dados
       Car carroSalvo = repository.save(car);
       
       // 2. SINGLETON: Regista a criação do veículo no ficheiro de auditoria global
       AuditoriaLogger.getInstance().registrarLog("Veículo CRIADO", "CarService");
       
       // 3. OBSERVER: Dispara notificações para todos os clientes inscritos sobre o novo veículo
       EstoquePublisher.getInstance().notificarNovoCarro(carroSalvo);
       
       return carroSalvo;
   }

   public List<Car> findAll() {
       return repository.findAll();
   }

   public Optional<Car> findById(Long id) {
       return repository.findById(id);
   }

   public Car update(Car car) {
       // SINGLETON: Regista a atualização no sistema de auditoria
       AuditoriaLogger.getInstance().registrarLog("Veículo ATUALIZADO (ID: " + car.getId() + ")", "CarService");
       
       return repository.save(car);
   }

   public boolean existsById(Long id) {
       return repository.existsById(id);
   }

   public void deleteById(Long id) {
       // SINGLETON: Regista a eliminação antes de o veículo desaparecer do banco
       AuditoriaLogger.getInstance().registrarLog("Veículo ELIMINADO (ID: " + id + ")", "CarService");
       
       repository.deleteById(id);
   }
}