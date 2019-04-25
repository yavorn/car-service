package com.telerikacademy.carservice;

import com.telerikacademy.carservice.models.Make;
import com.telerikacademy.carservice.models.Models;
import com.telerikacademy.carservice.repository.MakeRepository;
import com.telerikacademy.carservice.repository.ModelsRepository;
import com.telerikacademy.carservice.service.CarServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarModelServiceTests {


    @Mock
    MakeRepository mockMakeRepository;

    @Mock
    ModelsRepository mockModelsRepository;

    @InjectMocks
    CarServiceImpl carService;

    private Make carMakeAudi = new Make("Audi");
    private Make carMakeSeat = new Make("Seat");
    private Models carModelA6 = new Models(carMakeAudi, "A6");
    private Models carModelRS6 = new Models(carMakeAudi, "RS6");
    private Models carModelLeon = new Models(carMakeSeat, "Leon");

    private List<Make> carMakes = new ArrayList<>();
    private List<Models> carModels = new ArrayList<>();


    @Before
    public void setDefaultTestCarModels() {

        carMakeAudi.setMakeID((long) 1);
        carMakeSeat.setMakeID((long) 2);
        carModelA6.setModelID((long) 1);
        carModelRS6.setModelID((long)2);
        carModelLeon.setModelID((long) 3);

        carMakes.add(carMakeAudi);
        carMakes.add(carMakeSeat);

        carModels.add(carModelA6);
        carModels.add(carModelRS6);
        carModels.add(carModelLeon);


    }

    @Test
    public void getAllMakes_shouldReturnTwoCarMakes_whenTwoCarMakesAreAvailable() {
        // Arrange
        when(mockMakeRepository.findAllByOrderByMakeNameAsc())
                .thenReturn(carMakes);

        // Act
        List<Make> result = carService.getAllMakes();

        // Assert
        Assert.assertEquals(2, result.size());
        Assert.assertEquals("Audi", result.get(0).getMakeName());
        Assert.assertEquals("Seat", result.get(1).getMakeName());
        verify(mockMakeRepository, Mockito.times(1)).findAllByOrderByMakeNameAsc();
    }


    @Test
    public void getAllModels_shouldReturnTwoCarModels_whenTwoCarModelsAreAvailable() {
        // Arrange
        when(mockModelsRepository.findAll())
                .thenReturn(carModels);

        // Act
        List<Models> result = carService.getAllModels();

        // Assert
        Assert.assertEquals(3, result.size());
        Assert.assertEquals("A6", result.get(0).getModelName());
        Assert.assertEquals("RS6", result.get(1).getModelName());
        verify(mockModelsRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getMakeById_shouldReturnMakeWithIdOne_whenArgumentIsSetToOne() {
        //Arrange
       Mockito.when(mockMakeRepository.findMakeByMakeID((long)1)).thenReturn(carMakeAudi);
        // Act
        Make carMake = carService.getMakeById((long)1);

        // Assert
        Assert.assertEquals(carMakes.get(0), carMake);
        Assert.assertEquals("Audi", carMake.getMakeName());
        verify(mockMakeRepository, Mockito.times(1)).findMakeByMakeID((long)1);
    }


    @Test
    public void getModelById_shouldReturnModelWithIdOne_whenArgumentIsSetToOne() {
        //Arrange
        Mockito.when(mockModelsRepository.findModelsByModelID((long)1)).thenReturn(carModelA6);

        // Act
        Models carModel = carService.getModelById((long)1);

        // Assert
        Assert.assertEquals(carModels.get(0), carModel);
        Assert.assertEquals("A6", carModel.getMake().getMakeName());
        verify(mockModelsRepository, Mockito.times(1)).findModelsByModelID((long)1);
    }

    @Test
    public void findModelsByMakeId_shouldReturnModelWithMakeIdOne_whenArgumentIsSetToOne() {

        // Arrange
        when(mockModelsRepository.findModelsByMake_MakeID((long)1))
                .thenReturn(carModels);

        // Act
        List<Models> result = carService.findModelsByMakeID((long)1);

        System.out.println();
        // Assert
        Assert.assertEquals(2, result.size());
        Assert.assertEquals("A6", result.get(0).getModelName());
        Assert.assertEquals("RS6", result.get(1).getModelName());
        verify(mockModelsRepository, Mockito.times(1)).findModelsByMake_MakeID((long)1);
    }


}
