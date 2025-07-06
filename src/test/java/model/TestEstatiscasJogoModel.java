package model;

import org.cefet.models.EstatiscasJogoModel;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

public class TestEstatiscasJogoModel {

    @Test
    public void testCalcularLucroMedioPorAposta_ValorValido() {
        // Arrange
        EstatiscasJogoModel model = new EstatiscasJogoModel();
        model.setLucroCasa(1000);
        model.setTotalApostas(100);

        // Act
        double result = model.calcularLucroMedioPorAposta();

        // Assert
        assertEquals(10.0, result);
    }
}
