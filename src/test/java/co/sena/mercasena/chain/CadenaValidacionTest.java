
package co.sena.mercasena.chain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CadenaValidacionTest {

    private static Resultado validar(
            int cantidad,
            int stock,
            long total,
            boolean nuevo) {

        SolicitudPedido solicitud = new SolicitudPedido(
                cantidad,
                stock,
                BigDecimal.valueOf(total),
                nuevo
        );

        return CadenaValidacion.crear().validar(solicitud);
    }

    @Test
    void pedidoNormalEsAprobado() {
        assertEquals(
                Resultado.APROBADO,
                validar(2, 10, 50_000, false)
        );
    }

    @Test
    void sinStock() {
        assertEquals(
                Resultado.SIN_STOCK,
                validar(20, 10, 50_000, false)
        );
    }

    @Test
    void montoMenorAlMinimo() {
        assertEquals(
                Resultado.MONTO_MINIMO,
                validar(1, 10, 15_000, false)
        );
    }

    @Test
    void clienteNuevoConPedidoMuyAltoVaARevision() {
        assertEquals(
                Resultado.REVISION_FRAUDE,
                validar(1, 10, 6_000_000, true)
        );
    }

    // Error 1: el stock debe validarse antes que el monto mínimo.
    @Test
    void stockSeValidaAntesQueMontoMinimo() {
        assertEquals(
                Resultado.SIN_STOCK,
                validar(20, 10, 15_000, false)
        );
    }

    // Error 2: el validador de monto debe continuar la cadena.
    @Test
    void montoValidoDebeContinuarAlSiguienteValidador() {

        ValidadorMontoMinimo validadorMonto =
                new ValidadorMontoMinimo();

        validadorMonto.enlazar(new Validador() {
            @Override
            public Resultado validar(SolicitudPedido solicitud) {
                return Resultado.REVISION_FRAUDE;
            }
        });

        SolicitudPedido solicitud = new SolicitudPedido(
                1,
                10,
                BigDecimal.valueOf(50_000),
                false
        );

        assertEquals(
                Resultado.REVISION_FRAUDE,
                validadorMonto.validar(solicitud)
        );
    }

    // Error 3: exactamente cinco millones no debe activar fraude.
    @Test
    void clienteNuevoConPedidoExactamenteDeCincoMillonesNoVaARevision() {
        assertEquals(
                Resultado.APROBADO,
                validar(1, 10, 5_000_000, true)
        );
    }
}
