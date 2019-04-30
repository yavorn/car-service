package com.telerikacademy.carservice;

import com.telerikacademy.carservice.exceptions.DatabaseItemAlreadyExists;
import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
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
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarModelServiceTest {


    @Mock
    MakeRepository mockMakeRepository;

    @Mock
    ModelsRepository mockModelsRepository;

    @InjectMocks
    CarServiceImpl carService;

    private Make carMakeAudi = new Make("Audi");
    private Make carMakeSeat = new Make("Seat");
    private Make carMakeOpel = new Make("Opel");
    private Models carModelA6 = new Models(carMakeAudi, "A6");
    private Models carModelRS6 = new Models(carMakeAudi, "RS6");
    private Models carModelLeon = new Models(carMakeSeat, "Leon");
    private Models carModelAstra = new Models(carMakeOpel, "Astra");

    private List<Make> carMakes = new ArrayList<>();
    private List<Models> carModels = new ArrayList<>();


    @Before
    public void setDefaultTestCarModels() {

        carMakeAudi.setMakeID((long) 1);
        carMakeSeat.setMakeID((long) 2);
        carMakeOpel.setMakeID((long) 3);
        carMakeOpel.setMakeDeleted();
        carModelA6.setModelID((long) 1);
        carModelRS6.setModelID((long) 2);
        carModelLeon.setModelID((long) 3);
        carModelAstra.setModelID((long) 4);
        carModelAstra.setModelDeleted();


        carMakes.add(carMakeAudi);
        carMakes.add(carMakeSeat);

        carModels.add(carModelA6);
        carModels.add(carModelRS6);
        //carModels.add(carModelLeon);


    }

    @Test
    public void getAllMakes_shouldReturnTwoCarMakes_whenTwoCarMakesAreAvailable() {
        // Arrange
        when(mockMakeRepository.getAllByMakeDeletedFalseOrderByMakeNameAsc())
                .thenReturn(carMakes);
        // Act
        List<Make> result = carService.getAllMakes();
        // Assert
        assertEquals(2, result.size());
        assertEquals("Audi", result.get(0).getMakeName());
        assertEquals("Seat", result.get(1).getMakeName());
        verify(mockMakeRepository, times(1)).getAllByMakeDeletedFalseOrderByMakeNameAsc();
    }

    @Test(expected = NullPointerException.class)
    public void getAllMakes_shouldThrowNullPointerException() {
        // Arrange
        when(mockMakeRepository.getAllByMakeDeletedFalseOrderByMakeNameAsc())
                .thenReturn(null);
        // Act
        List<Make> result = carService.getAllMakes();
        // Assert
        assertEquals(0, result.size());
        verify(mockMakeRepository, times(1)).getAllByMakeDeletedFalseOrderByMakeNameAsc();
    }


    @Test
    public void getAllModels_shouldReturnTwoCarModels_whenTwoCarModelsAreAvailable() {
        // Arrange
        when(mockModelsRepository.getAllByModelDeletedFalse())
                .thenReturn(carModels);

        // Act
        List<Models> result = carService.getAllModels();

        // Assert
        assertEquals(2, result.size());
        assertEquals("A6", result.get(0).getModelName());
        assertEquals("RS6", result.get(1).getModelName());
        verify(mockModelsRepository, times(1)).getAllByModelDeletedFalse();
    }

    @Test(expected = NullPointerException.class)
    public void getAllModels_shouldThrowNullPointerException() throws ResponseStatusException {
        // Arrange
        when(mockModelsRepository.getAllByModelDeletedFalse())
                .thenReturn(null);
        // Act
        List<Models> result = carService.getAllModels();
        // Assert
        assertEquals(0, result.size());
        verify(mockModelsRepository, times(1)).getAllByModelDeletedFalse();
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void getMakeById_shouldThrowDatabaseItemNotFoundException_whenInvalidIdIsPassed() throws DatabaseItemNotFoundException {
        // Act
        Make carMake = carService.getMakeById((long)1);
    }

    @Test
    public void getMakeById_shouldReturnMakeWithIdOne_whenArgumentIsSetToOne() {
        //Arrange
        Mockito.when(mockMakeRepository.findMakeByMakeID((long) 1)).thenReturn(carMakeAudi);
        // Act
        Make carMake = carService.getMakeById((long) 1);
        // Assert
        assertEquals(carMakes.get(0), carMake);
        assertEquals("Audi", carMake.getMakeName());
        verify(mockMakeRepository, times(2)).findMakeByMakeID((long) 1);
    }




    @Test
    public void getModelById_shouldReturnModelWithIdOne_whenArgumentIsSetToOne() {
        //Arrange
        Mockito.when(mockModelsRepository.findModelsByModelID((long) 1)).thenReturn(carModelA6);

        // Act
        Models carModel = carService.getModelById((long) 1);

        // Assert
        assertEquals(carModels.get(0), carModelA6);
        assertEquals("A6", carModel.getModelName());
        verify(mockModelsRepository, times(2)).findModelsByModelID((long) 1);
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void getModelById_shouldThrowDatabaseItemNotFoundException_whenInvalidIdIsPassed() throws DatabaseItemNotFoundException {

        // Act
        Models carModel = carService.getModelById((long) 1);

    }

    @Test
    public void findModelsByMakeId_shouldReturnModelWithMakeIdOne_whenArgumentIsSetToOne() {

        // Arrange
        when(mockModelsRepository.findModelsByMake_MakeID((long) 1))
                .thenReturn(carModels);

        // Act
        List<Models> result = carService.findModelsByMakeID((long) 1);

        // Assert
        assertEquals(2, result.size());
        assertEquals("A6", result.get(0).getModelName());
        assertEquals("RS6", result.get(1).getModelName());
        verify(mockModelsRepository, times(2)).findModelsByMake_MakeID((long) 1);
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void findModelsByMakeId_shouldThrowDatabaseItemNotFoundException_whenInvalidIdIsPassed() throws DatabaseItemNotFoundException{

        List<Models> result = carService.findModelsByMakeID((long) 1);

    }





    @Test
    public void addMake_shouldInvokeSaveInMakeRepository_whenAddedSuccessfully() {

        // Act
        carService.addMake(carMakeAudi);

        // Assert
        verify(mockMakeRepository, times(1)).save(Mockito.any(Make.class));
    }


     @Test(expected = DatabaseItemAlreadyExists.class)
    public void addMake_shouldThrowDatabaseItemAlreadyExistsException_whenCarMakeExists() throws DatabaseItemAlreadyExists  {
        //Arrange
        when(mockMakeRepository.findMakeByMakeName(carMakeAudi.getMakeName()))
                .thenReturn(carMakeAudi);

        // Act
         carService.addMake(carMakeAudi);
    }

    @Test
    public void addModel_shouldInvokeSaveInModelsRepository_whenAddedSuccessfully() {

        // Act
        carService.addModel(carModelA6);

        // Assert
        verify(mockModelsRepository, times(1)).save(Mockito.any(Models.class));
    }


    @Test(expected = DatabaseItemAlreadyExists.class)
    public void addModel_shouldThrowDatabaseItemAlreadyExistsException_whenCarModelExists() throws DatabaseItemAlreadyExists  {
        //Arrange
        when(mockModelsRepository.findModelsByModelName(carModelA6.getModelName()))
                .thenReturn(carModelA6);

        // Act
        carService.addModel(carModelA6);
    }

    @Test
    public void editMake_shouldInvokeSaveInMakeRepository_whenEditSuccessfully() {

        when(mockMakeRepository.findMakeByMakeID((long)1))
                .thenReturn(carMakeAudi);
        // Act
        carService.editMake((long)1, carMakeAudi);

        // Assert
        verify(mockMakeRepository, times(1)).save(Mockito.any(Make.class));
    }


    @Test(expected = NullPointerException.class)
    public void editMake_shouldDatabaseItemNotFoundException_whenCarMakeDoNotExists() throws NullPointerException {

        when(mockMakeRepository.findMakeByMakeID((long)1))
                .thenReturn(null);
        // Act
        carService.editMake((long)1, carMakeAudi);


    }
    @Test
    public void editModel_shouldInvokeSaveInModelRepository_whenEditSuccessfully() {

        when(mockModelsRepository.findModelsByModelID((long)1))
                .thenReturn(carModelA6);
        // Act
        carService.editModel((long)1, carModelA6);

        // Assert
        verify(mockModelsRepository, times(1)).save(Mockito.any(Models.class));
    }
    @Test(expected = NullPointerException.class)
    public void editModel_shouldDatabaseItemNotFoundException_whenCarModelDoNotExists() throws NullPointerException {

        when(mockModelsRepository.findModelsByModelID((long)1))
                .thenReturn(null);
        // Act
        carService.editModel((long)1, carModelA6);
    }

    @Test
    public void deleteCarMakeByID_shouldInvokeSaveInMakeRepository_whenDeleteSuccessfully() {

        when(mockMakeRepository.findMakeByMakeID((long)1))
                .thenReturn(carMakeAudi);
        // Act
        carService.deleteCarMakeByID((long)1);

        // Assert
        verify(mockMakeRepository, times(1)).save(Mockito.any(Make.class));
    }


    @Test(expected = DatabaseItemNotFoundException.class)
    public void deleteCarMakeByID_shouldThrowDatabaseItemNotFoundException_whenInvalidIdIsPassed() throws DatabaseItemNotFoundException {

        when(mockMakeRepository.findMakeByMakeID((long)1))
                .thenReturn(null);
        // Act
        carService.deleteCarMakeByID((long)1);


    }

    @Test
    public void deleteCarModelByID_shouldInvokeSaveInModelRepository_whenDeleteSuccessfully() {

        when(mockModelsRepository.findModelsByModelID((long)1))
                .thenReturn(carModelA6);
        // Act
        carService.deleteCarModelByID((long)1);

        // Assert
        verify(mockModelsRepository, times(1)).save(Mockito.any(Models.class));
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void deleteCarModelByID_shouldThrowDatabaseItemNotFoundException_whenInvalidIdIsPassed() throws DatabaseItemNotFoundException {

        when(mockModelsRepository.findModelsByModelID((long)1))
                .thenReturn(null);
        // Act
        carService.deleteCarModelByID((long)1);


    }


    @Test
    public void undeleteCarMakeByID_shouldInvokeSaveInMakeRepository_whenUndeleteSuccessfully() {

        when(mockMakeRepository.findMakeByMakeID((long)3))
                .thenReturn(carMakeOpel);
        // Act
        carService.undeleteCarMakeByID((long)3);

        // Assert
        verify(mockMakeRepository, times(1)).save(Mockito.any(Make.class));
    }


    @Test(expected = DatabaseItemNotFoundException.class)
    public void undeleteCarMakeByID_shouldThrowDatabaseItemNotFoundException_whenInvalidIdIsPassed() throws DatabaseItemNotFoundException {

        when(mockMakeRepository.findMakeByMakeID((long)3))
                .thenReturn(null);
        // Act
        carService.undeleteCarMakeByID((long)3);


    }

    @Test
    public void undeleteCarModelByID_shouldInvokeSaveInModelRepository_whenUndeleteSuccessfully() {

        when(mockModelsRepository.findModelsByModelID((long)4))
                .thenReturn(carModelAstra);
        // Act
        carService.undeleteCarModelByID((long)4);

        // Assert
        verify(mockModelsRepository, times(1)).save(Mockito.any(Models.class));
    }


    @Test(expected = DatabaseItemNotFoundException.class)
    public void undeleteCarModelByID_shouldThrowDatabaseItemNotFoundException_whenInvalidIdIsPassed() throws DatabaseItemNotFoundException {

        when(mockMakeRepository.findMakeByMakeID((long)3))
                .thenReturn(null);
        // Act
        carService.undeleteCarMakeByID((long)3);


    }

}
