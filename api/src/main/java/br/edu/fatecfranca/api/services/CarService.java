package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fatecfranca.api.dtos.CarRequest;
import br.edu.fatecfranca.api.entities.Car;
import br.edu.fatecfranca.api.factories.CarFactory;
import br.edu.fatecfranca.api.observers.EstoquePublisher;
import br.edu.fatecfranca.api.repositories.CarRepository;
import br.edu.fatecfranca.api.repositories.CustomerRepository;
import br.edu.fatecfranca.api.utils.AuditoriaLogger;
import br.edu.fatecfranca.api.validators.ManufactureYearValidator;
import br.edu.fatecfranca.api.validators.RequestValidator;
import br.edu.fatecfranca.api.validators.SaleDataValidator;
import br.edu.fatecfranca.api.validators.UniquePlatesValidator;

@Service
public class CarService {

    private final CarRepository repository;
    private final CustomerRepository customerRepository;
    private final RequestValidator<CarRequest> validator;

    public CarService(
            CarRepository repository,
            CustomerRepository customerRepository) {
        this.repository = repository;
        this.customerRepository = customerRepository;

        var platesValidator = new UniquePlatesValidator(repository, null);
        var saleValidator = new SaleDataValidator(platesValidator);
        this.validator = new ManufactureYearValidator(saleValidator);
    }

    public Car create(CarRequest request) {
        validator.validate(request, null);

        Car car = toEntity(request);
        car = CarFactory.prepararNovoCarro(car);
        Car savedCar = repository.save(car);

        AuditoriaLogger.getInstance()
                .registrarLog("Veículo CRIADO", "CarService");

        EstoquePublisher.getInstance().notificarNovoCarro(savedCar);

        return savedCar;
    }

    public Car update(Long id, CarRequest request) {
        validator.validate(request, id);

        Car car = toEntity(request);
        car.setId(id);

        AuditoriaLogger.getInstance()
                .registrarLog("Veículo ATUALIZADO (ID: " + id + ")", "CarService");

        return repository.save(car);
    }

    public List<Car> findAll() {
        return repository.findAll();
    }

    public Optional<Car> findById(Long id) {
        return repository.findById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public void deleteById(Long id) {
        AuditoriaLogger.getInstance()
                .registrarLog("Veículo ELIMINADO (ID: " + id + ")", "CarService");

        repository.deleteById(id);
    }

    private Car toEntity(CarRequest request) {
        Car car = new Car();

        car.setBrand(request.brand());
        car.setModel(request.model());
        car.setColor(request.color());
        car.setYearManufacture(request.yearManufacture().intValue());
        car.setImported(request.imported());
        car.setPlates(request.plates());
        car.setSellingDate(request.sellingDate());
        car.setSellingPrice(request.sellingPrice());

        if (request.customerId() != null) {
            car.setCustomer(customerRepository.findById(request.customerId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Cliente não encontrado."
                    )));
        }

        return car;
    }
}