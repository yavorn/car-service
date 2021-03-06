package com.telerikacademy.carservice;

import com.telerikacademy.carservice.exceptions.DatabaseItemAlreadyDeletedException;
import com.telerikacademy.carservice.exceptions.DatabaseItemAlreadyExistsException;
import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.carservice.models.Procedure;
import com.telerikacademy.carservice.repository.ProcedureRepository;
import com.telerikacademy.carservice.service.ProcedureServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class ProcedureServiceImplTest {
    @Mock
    ProcedureRepository mockProcedureRepository;

    @InjectMocks
    ProcedureServiceImpl procedureServiceImpl;

    private Procedure testProcedure = new Procedure();
    private Procedure testProcedureTwo = new Procedure();
    private Procedure testProcedureThree = new Procedure();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testProcedure.setProcedureName("procedureName");
        testProcedureTwo.setProcedureName("procedureNameTwo");
        testProcedure.setProcedurePrice(0);
        testProcedureTwo.setProcedurePrice(1);
        testProcedure.setProcedureID(0L);
        testProcedureTwo.setProcedureID(1L);
        testProcedureThree.setProcedureDeleted();
    }

    @Test
    public void getAllProcedures_shouldReturn_WhenValidArgsPassed() {
        when(mockProcedureRepository.findAllByProcedureDeletedIsFalseOrderByProcedureNameAsc())
                .thenReturn(Arrays.asList(testProcedure, testProcedureTwo));

        List<Procedure> result = procedureServiceImpl.getAllProcedures();
        Assert.assertEquals(2, result.size());
    }

    @Test
    public void getAllProcedures_shouldReturn_WhenInvalidArgsPassed() {
        when(mockProcedureRepository.findAllByProcedureDeletedIsFalseOrderByProcedureNameAsc())
                .thenReturn(Arrays.asList(testProcedure, testProcedureTwo));
        List<Procedure> result = procedureServiceImpl.getAllProcedures();
        Assert.assertNotEquals(3, result.size());
    }

    @Test(expected = NullPointerException.class)
    public void getAllProcedures_ShouldThrow_WhenNullPassed() {
        when(mockProcedureRepository.findAllByProcedureDeletedIsFalseOrderByProcedureNameAsc())
                .thenReturn(null);
        List<Procedure> result = procedureServiceImpl.getAllProcedures();
        Assert.assertEquals(1, result.size());
    }


    @Test
    public void getProcedureByID_shouldReturn_WhenValidArgsPassed() {
        when(mockProcedureRepository.findProcedureByProcedureIDAndProcedureDeletedFalse(anyLong()))
                .thenReturn(testProcedure);

        Procedure result = procedureServiceImpl.getProcedureByID(1L);
        Assert.assertEquals(testProcedure, result);
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void getProcedureByID_shouldReturn_WhenInvalidArgsPassed() {
        when(mockProcedureRepository.findProcedureByProcedureIDAndProcedureDeletedFalse(anyLong()))
                .thenReturn(null);

        Procedure result = procedureServiceImpl.getProcedureByID(3L);
        Assert.assertEquals(testProcedure, result);
    }

    @Test
    public void addProcedure_shouldReturn_WhenValidArgsPassed() {
        procedureServiceImpl.addProcedure(testProcedure);
        procedureServiceImpl.addProcedure(testProcedureTwo);

        verify(mockProcedureRepository, times(2))
                .save(Mockito.any(Procedure.class));
    }


    @Test(expected = DatabaseItemAlreadyExistsException.class)
    public void addProcedure_shouldReturn_WhenDuplicateNamePassed() {
        when(mockProcedureRepository.findProcedureByProcedureNameAndProcedureDeletedFalse(testProcedure.getProcedureName()))
                .thenReturn(testProcedure);
        procedureServiceImpl.addProcedure(testProcedure);
    }

    @Test
    public void deleteProcedure_shouldReturn_WhenValidArgsPassed()  {
        when(mockProcedureRepository.findProcedureByProcedureIDAndProcedureDeletedFalse(anyLong()))
                .thenReturn(testProcedure);

        procedureServiceImpl.deleteProcedure(1L);
    }

    @Test (expected = DatabaseItemNotFoundException.class)
    public void deleteProcedure_shouldReturn_WhenInvalidArgsPassed()  {
        when(mockProcedureRepository.findProcedureByProcedureIDAndProcedureDeletedFalse(anyLong()))
                .thenReturn(null);

        procedureServiceImpl.deleteProcedure(1L);
    }

    @Test (expected = DatabaseItemAlreadyDeletedException.class)
    public void deleteProcedure_shouldReturn_WhenItemAlreadyDeleted()  {
        when(mockProcedureRepository.findProcedureByProcedureIDAndProcedureDeletedFalse(anyLong()))
                .thenReturn(testProcedureThree);

        procedureServiceImpl.deleteProcedure(1L);
    }

    @Test
    public void editProcedure_shouldReturn_WhenValidArgsPassed() {
        when(mockProcedureRepository.findProcedureByProcedureIDAndProcedureDeletedFalse(anyLong())).thenReturn(testProcedure);

        procedureServiceImpl.editProcedure(new Procedure("procedureNameFour", 0d), 1L);
    }


    @Test (expected = DatabaseItemNotFoundException.class)
    public void editProcedure_shouldThrow_WhenInvalidArgsPassed(){
        when(mockProcedureRepository.findProcedureByProcedureIDAndProcedureDeletedFalse(anyLong())).thenReturn(null);

        procedureServiceImpl.editProcedure(testProcedure, testProcedure.getProcedureID());
    }


}
