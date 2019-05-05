package com.telerikacademy.carservice;

import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.carservice.models.*;
import com.telerikacademy.carservice.repository.CarEventRepository;
import com.telerikacademy.carservice.service.CarEventServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

public class CarEventServiceImplTest {
    @Mock
    CarEventRepository carEventRepository;
    @InjectMocks
    CarEventServiceImpl carEventServiceImpl;
    private Make testMake = new Make();
    private Models testModel = new Models();
    private Customer testCustomer = new Customer();
    private CustomerCars testCustomerCar = new CustomerCars();
    private CarEvent testCarEvent = new CarEvent();
    private List<CarEvent> testCarEventList = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testMake.setMakeName("Make");
        testModel.setMake(testMake);
        testModel.setModelName("Model");
    }

    @Test
    public void getAllCarEvents_ShouldReturn_WhenValidArgsPassed() {
        testCarEventList.add(testCarEvent);
        when(carEventRepository.findAll()).thenReturn(testCarEventList);
        List<CarEvent> result = carEventServiceImpl.getAllCarEvents();
        assertEquals(testCarEvent, result.get(0));
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void getAllCarEvents_ShouldThrow_WhenInvalidArgsPassed() {
        when(carEventRepository.findAll()).thenReturn(testCarEventList);
        List<CarEvent> result = carEventServiceImpl.getAllCarEvents();
        assertEquals(0, result.size());
    }

    @Test
    public void getCarEventByID_ShouldReturn_WhenValidArgsPassed() {
        when(carEventRepository.findCarEventByCarEventIDAndCarEventDeletedFalse(anyLong())).thenReturn(testCarEvent);

        CarEvent result = carEventServiceImpl.getCarEventByID(1L);
        assertEquals(testCarEvent, result);
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void getCarEventByID_ShouldThrow_WhenInvalidArgsPassed() {
        when(carEventRepository.findCarEventByCarEventIDAndCarEventDeletedFalse(anyLong())).thenReturn(null);

        CarEvent result = carEventServiceImpl.getCarEventByID(0L);
        assertEquals(testCarEvent, result);
    }

    @Test
    public void getCarEventsByCustomerCarID_ShouldReturn_WhenValidArgsPassed() {
        testCarEventList.add(testCarEvent);
        when(carEventRepository.findAllByCustomerCar_CustomerCarID(anyLong())).thenReturn(testCarEventList);

        List<CarEvent> result = carEventServiceImpl.getCarEventsByCustomerCarID(0L);
        assertEquals(testCarEvent, result.get(0));
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void getCarEventsByCustomerCarID_ShouldThrow_WhenInvalidArgsPassed() {
        when(carEventRepository.findAllByCustomerCar_CustomerCarID(anyLong())).thenReturn(testCarEventList);

        List<CarEvent> result = carEventServiceImpl.getCarEventsByCustomerCarID(0L);
        assertEquals(testCarEvent, result.get(0));
    }

    @Test(expected = NullPointerException.class)
    public void deleteCarEvent_ShouldThrow_WhenValidArgsPassed() {
        when(carEventRepository.findCarEventByCarEventIDAndCarEventDeletedFalse(anyLong())).thenReturn(testCarEvent);
        carEventServiceImpl.deleteCarEvent(testCarEvent.getCarEventID());
        assertNull(carEventServiceImpl.getAllCarEvents());
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void deleteCarEvent_ShouldThrow_WhenInvalidArgsPassed() {
        carEventServiceImpl.deleteCarEvent(0L);
    }

        @Test
    public void editPdfGenerated_ShouldReturn_WhenValidArgsPassed() {
        carEventServiceImpl.editPdfGenerated(new CarEvent(LocalDateTime.of(2019, Month.MAY, 4, 19, 2, 11), new CustomerCars(new Customer("email", "phone", "name", true), new Models(new Make("makeName"), "modelName"), Integer.valueOf(0), "licensePlate", "vinNumber"), 0d));
    }
}
