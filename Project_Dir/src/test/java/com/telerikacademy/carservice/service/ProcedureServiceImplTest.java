package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.models.Procedure;
import com.telerikacademy.carservice.repository.ProcedureRepository;
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
    ProcedureRepository procedureRepository;
    @InjectMocks
    ProcedureServiceImpl procedureServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllProcedures() throws Exception {
        when(procedureRepository.findAllByProcedureDeletedIsFalse()).thenReturn(Arrays.<Procedure>asList(new Procedure("procedureName", 0d)));

        List<Procedure> result = procedureServiceImpl.getAllProcedures();
        Assert.assertEquals(Arrays.<Procedure>asList(new Procedure("procedureName", 0d)), result);
    }

    @Test
    public void testGetProcedureByID() throws Exception {
        when(procedureRepository.findProcedureByProcedureID(anyLong())).thenReturn(new Procedure("procedureName", 0d));

        Procedure result = procedureServiceImpl.getProcedureByID(Long.valueOf(1));
        Assert.assertEquals(new Procedure("procedureName", 0d), result);
    }

    @Test
    public void testAddProcedure() throws Exception {
        procedureServiceImpl.addProcedure(new Procedure("procedureName", 0d));
    }

    @Test
    public void testDeleteProcedure() throws Exception {
        when(procedureRepository.findProcedureByProcedureID(anyLong())).thenReturn(new Procedure("procedureName", 0d));

        procedureServiceImpl.deleteProcedure(Long.valueOf(1));
    }

    @Test
    public void testEditProcedure() throws Exception {
        procedureServiceImpl.editProcedure(new Procedure("procedureName", 0d));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme