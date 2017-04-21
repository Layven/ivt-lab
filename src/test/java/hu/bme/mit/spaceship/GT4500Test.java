package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.sun.xml.internal.bind.v2.TODO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GT4500Test {

    private GT4500 ship;

    @Mock
    private TorpedoStore primary, secondary;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.ship = new GT4500(primary, secondary);
    }

    @Test
    public void fireTorpedos_Single_Success() {
        // Arrange
        when(primary.isEmpty()).thenReturn(false);
        when(primary.fire(1)).thenReturn(true);

        when(secondary.isEmpty()).thenReturn(false);
        when(secondary.fire(1)).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedos(FiringMode.SINGLE);

        // Assert
        assertEquals(true, result);
        verify(primary, times(1)).fire(1);
    }

    @Test
    public void fireTorpedos_Single_Success_2times() {
        // Arrange
        when(primary.isEmpty()).thenReturn(false);
        when(primary.fire(1)).thenReturn(true);

        when(secondary.isEmpty()).thenReturn(false);
        when(secondary.fire(1)).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedos(FiringMode.SINGLE);
        boolean result2 = ship.fireTorpedos(FiringMode.SINGLE);

        // Assert
        assertEquals(true, result);
        assertEquals(true, result2);
        verify(primary, times(1)).fire(1);
        verify(secondary, times(1)).fire(1);
    }

    @Test
    public void fireTorpedos_All_Success() {
        // Arrange
        when(primary.isEmpty()).thenReturn(false);
        when(primary.fire(1)).thenReturn(true);

        when(secondary.isEmpty()).thenReturn(false);
        when(secondary.fire(1)).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedos(FiringMode.ALL);

        // Assert
        assertEquals(true, result);
        verify(primary, times(1)).fire(1);
        verify(secondary, times(1)).fire(1);
    }

    @Test
    public void fireTorpedos_Single_Failure() {
        // Arrange
        when(primary.isEmpty()).thenReturn(true);
        when(primary.fire(1)).thenReturn(false);

        when(secondary.isEmpty()).thenReturn(true);
        when(secondary.fire(1)).thenReturn(false);

        // Act
        boolean result = ship.fireTorpedos(FiringMode.SINGLE);

        // Assert
        assertEquals(false, result);
        verify(primary, times(0)).fire(1);
    }

    @Test
    public void fireTorpedos_Single_Success_Then_Failure() {
        // Arrange
        when(primary.isEmpty()).thenReturn(false);
        when(primary.fire(1)).thenReturn(true);

        when(secondary.isEmpty()).thenReturn(true);
        when(secondary.fire(1)).thenReturn(false);

        // Act
        boolean result = ship.fireTorpedos(FiringMode.SINGLE);
        boolean result2 = ship.fireTorpedos(FiringMode.SINGLE);

        // Assert
        assertEquals(true, result);
        assertEquals(true, result2);
        verify(primary, times(2)).fire(1);
        verify(secondary, times(0)).fire(1);
    }


}
