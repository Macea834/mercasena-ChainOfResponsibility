
@Test
void stockSeValidaAntesQueMontoMinimo() {
    // El pedido incumple el stock y también el monto mínimo.
    // Debe prevalecer SIN_STOCK porque Stock se valida primero.
    assertEquals(
        Resultado.SIN_STOCK,
        validar(20, 10, 15_000, false)
    );
}

@Test
void montoValidoDebeContinuarAlSiguienteValidador() {
    ValidadorMontoMinimo validador = new ValidadorMontoMinimo();

    Validador siguiente = new Validador() {
        @Override
        public Resultado validar(SolicitudPedido solicitud) {
            return Resultado.REVISION_FRAUDE;
        }
    };

    validador.enlazar(siguiente);

    SolicitudPedido solicitud = new SolicitudPedido(
        1, 10, BigDecimal.valueOf(50_000), false
    );

    assertEquals(
        Resultado.REVISION_FRAUDE,
        validador.validar(solicitud)
    );
}

@Test
void clienteNuevoConPedidoExactamenteDeCincoMillonesEsAprobado() {
    assertEquals(
        Resultado.APROBADO,
        validar(1, 10, 5_000_000, true)
    );
}
