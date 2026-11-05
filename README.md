# Un Proxy para migrar sin salir de main

Taller individual: cambiar cómo se calcula el envío de una tienda usando **Trunk-Based Development**.
No hay ramas largas: cada paso es un commit pequeño que deja `main` en verde, y el código nuevo
entra apagado detrás de un Proxy.

## Cómo correrlo

```bash
mvn test
```

Requiere Java 17 y Maven.

## Historial (de abajo hacia arriba en `git log`)

| Paso | Commit | Qué hace |
|---|---|---|
| 0 | `chore: initial shop with legacy shipping` | Tienda base: `Checkout` crea directamente `LegacyShippingCalculator` (10.0 fijo) |
| 1 | `refactor: extract ShippingCalculator interface` | Interfaz `ShippingCalculator`; Legacy la implementa, sin cambio de comportamiento |
| 2 | `refactor: inject ShippingCalculator into Checkout` | `Checkout` recibe el calculador por constructor; `ShippingConfig` lo ensambla |
| 3 | `feat: add ShippingCalculatorProxy (flag off)` | Proxy con la misma interfaz; con el flag apagado todo se comporta igual (Smart es un stub) |
| 4 | `feat: smart shipping by weight` | Regla A: 2,5 por kilo (Smart reemplaza al stub, flag aún apagado) |
| 4 | `feat: smart shipping surcharge outside Bogotá` | Regla B: +5 fuera de Bogotá |
| 5 | `feat: proxy logging` | El Proxy registra qué implementación respondió y cuántos ms tardó |
| 6 | `chore: enable smart-shipping` | Se enciende el flag en producción |
| 6 | `chore: remove legacy shipping and flag` | Se borran Legacy, el flag y el Proxy |

## Diseño

- `ShippingCalculator` es la única interfaz que conoce `Checkout`; el cliente no se entera del Proxy.
- `ShippingCalculatorProxy` implementa esa misma interfaz y delega: Smart con el flag
  `smart-shipping` encendido, Legacy en cualquier otro caso.
- Extensión del paso 5 (**Logging**): el Proxy acepta un `Consumer<String>` y escribe
  `shipping order=<id> impl=<clase> time=<n>ms`. El constructor original de tres argumentos se
  conserva, así que las pruebas del enunciado no cambian.
- Un flag que nunca se borra se vuelve deuda técnica: por eso el último commit elimina el flag,
  el código viejo y el Proxy, que sin flag ya no tiene nada que decidir.
