package com.telerikacademy.carservice;

import com.telerikacademy.carservice.models.Procedure;
import com.telerikacademy.carservice.repository.ProcedureRepository;
import com.telerikacademy.carservice.service.ProcedureServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class ProcedureServiceImplTest {
    @Mock
    ProcedureRepository mockProcedureRepository;
    @InjectMocks
    ProcedureServiceImpl procedureServiceImpl;
    private Procedure testProcedure = new Procedure();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testProcedure.setProcedureName("procedureName");
        testProcedure.setProcedurePrice(0);
    }

    @Test
    public void getAllProcedures_shouldReturn_When_ValidArgsPassed() throws Exception {
        when(mockProcedureRepository.findAllByProcedureDeletedIsFalse()).thenReturn(Arrays.<Procedure>asList(testProcedure));

        List<Procedure> result = procedureServiceImpl.getAllProcedures();
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void getAllProcedures_shouldReturn_When_InvalidArgsPassed() throws Exception{
        when(mockProcedureRepository.findAllByProcedureDeletedIsFalse()).thenReturn(null);
        List<Procedure> result = procedureServiceImpl.getAllProcedures();
        Assert.assertNull(result.get(0));
    }

    @Test
    public void testGetProcedureByID() throws Exception {
        when(mockProcedureRepository.findProcedureByProcedureID(anyLong())).thenReturn(new Procedure("procedureName", 0d));

        Procedure result = procedureServiceImpl.getProcedureByID(Long.valueOf(1));
        Assert.assertEquals(new Procedure("procedureName", 0d), result);
    }

    @Test
    public void testAddProcedure() throws Exception {
        procedureServiceImpl.addProcedure(new Procedure("procedureName", 0d));
    }

    @Test
    public void testDeleteProcedure() throws Exception {
        when(mockProcedureRepository.findProcedureByProcedureID(anyLong())).thenReturn(new Procedure("procedureName", 0d));

        procedureServiceImpl.deleteProcedure(Long.valueOf(1));
    }
}
