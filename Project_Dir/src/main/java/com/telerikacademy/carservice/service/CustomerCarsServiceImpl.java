package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.exceptions.DatabaseItemAlreadyDeletedException;
import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.carservice.models.Customer;
import com.telerikacademy.carservice.models.CustomerCars;
import com.telerikacademy.carservice.repository.CustomerCarsRepository;
import com.telerikacademy.carservice.service.contracts.CustomerCarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerCarsServiceImpl implements CustomerCarsService {

    private static final String CUSTOMER_CAR_NOT_FOUND_EXCEPTION_MSG = "Customer Car with ID: %s not found!";
    private static final String CAR_ALREADY_DELETED_MSG = "Car with ID %d is already deleted.";


    private CustomerCarsRepository customerCarsRepository;

    @Autowired
    public CustomerCarsServiceImpl(CustomerCarsRepository customerCarsRepository) {
        this.customerCarsRepository = customerCarsRepository;
    }

    @Override
    public List<CustomerCars> getAllCustomerCars() {
        List<CustomerCars> result = customerCarsRepository.findAllByCustomerCarDeletedFalse();
        if (result.size() == 0) {
            throw new DatabaseItemNotFoundException("No customer cars found.");
        }
        return result;
    }

    @Override
    public List<CustomerCars> getAllCustomerCarsByCustomerId(long id) {
        List<CustomerCars> result = customerCarsRepository.findCustomerCarsByCustomer_CustomerIdAndCustomerCarDeletedFalse(id);
        if (result.size() == 0) {
            throw new DatabaseItemNotFoundException(String.format("No customer cars found for customer with id %d", id));
        }
        return result;
    }

    @Override
    public CustomerCars getCustomerCarById(long id) {
        CustomerCars customerCar = customerCarsRepository.findCustomerCarsByCustomerCarIDAndCustomerCarDeletedFalse(id);
        if (customerCar == null) {
            throw new DatabaseItemNotFoundException(String.format(CUSTOMER_CAR_NOT_FOUND_EXCEPTION_MSG, id));
        }
        return customerCarsRepository.findCustomerCarsByCustomerCarIDAndCustomerCarDeletedFalse(id);
    }

    @Override
    public List<CustomerCars> getAllCustomerCarsByCustomerEmail(String email){
        List<CustomerCars> result = customerCarsRepository.findCustomerCarsByCustomerEmailAndCustomerCarDeletedFalse(email);
        if (result.size() == 0) {
            throw new DatabaseItemNotFoundException(String.format("No customer cars found for customer with username %s", email));
        }
        return result;
    }

    @Override
    public void deleteCustomerCar(long id){
        CustomerCars carToDelete = customerCarsRepository.findCustomerCarsByCustomerCarIDAndCustomerCarDeletedFalse(id);
        if (carToDelete == null) {
            throw new DatabaseItemNotFoundException(String.format(CUSTOMER_CAR_NOT_FOUND_EXCEPTION_MSG, id));
        }
        if (carToDelete.isCustomerCarDeleted()){
            throw new DatabaseItemAlreadyDeletedException(String.format(CAR_ALREADY_DELETED_MSG, id));
        }

        carToDelete.setCustomerCarDeleted(true);
        customerCarsRepository.saveAndFlush(carToDelete);
    }
}
