> Cómo usar este documento: subilo al inicio de cada conversación nueva conmigo para que retome el proyecto exactamente donde quedó. Actualizalo (o pedime que lo actualice) cada vez que decidamos algo nuevo.

> **Preferencia de idioma del usuario**: hablar siempre en español neutro, sin formas de voseo argentino ("vos", "tenés", "sos", etc.). El usuario no es argentino y lo pidió explícitamente más de una vez.

## Visión general

- Modpack para Minecraft Java 1.21.1 / Forge 52.1.0, compatible con mundos vanilla nuevos (NO mapa fijo, NO funciona en mundos ya generados antes de instalar).
- Sensación buscada: **terror y perturbación durante el día**, **calma relativa durante la noche**. Inversión del ciclo clásico de supervivencia.
- El día debe sentirse peligroso, opresivo, de alerta constante.
- La noche es el momento "seguro" para explorar y conseguir recursos.
- Habrá historia y lore — **pausado por ahora**, se retoma cuando el contenido jugable esté más avanzado.
- Objetivo de distribución: publicar en CurseForge/Modrinth.

## Identidad del proyecto

- **Nombre del modpack**: DARK
- **Autor**: JayemuaXD
- **Mod núcleo**:
  - Nombre: `DARK Core`
  - mod_id: `darkcore`
  - package base: `com.jayemuaxd.dark.core`

## Inspiración / referencias

- **Backrooms**: estética de espacios liminales para el "día opresivo" — iluminación fluorescente, silencio antinatural, sensación de que el mundo se corrompe temporalmente con la luz solar. Posible mecánica: desorientación espacial / terreno que se siente "mal" de día.
- **Attack on Titan**: sensación de ser presa y no cazador — amenazas enormes, más rápidas/fuertes que el jugador, que no se enfrentan de frente sino que se evitan o de las que se huye.
- **Project Zomboid**: tensión de supervivencia por sistemas (hambre, cansancio, ruido, permadeath) más que por sustos puntuales. Inspira el sistema de tensión/sanidad: cuanto más expuesto de día, más se degrada el estado del jugador.
- **El Titan (Deltarune, Cap. 4)**: jefe imparable/implacable, la tensión viene de la persecución más que del combate directo. Posible base para un "mob emblemático" de día — algo de lo que se sobrevive, no que se mata.
- **Canciones de referencia para atmósfera sonora**: "Looping the Rooms", "I Monster – Who Is She?", "Caravan Palace – Aftermath". Sugieren un tono inquietante pero con groove, más surreal que gore puro.

**Síntesis de diseño**: el terror de día se orienta a "algo enorme e imparable obliga a moverte/esconderte, mientras el mundo se siente mal y los recursos se degradan" (Backrooms + Zomboid + AoT/Deltarune), en vez de jumpscares aislados. La noche es el respiro real.

## Sistema del mundo (lore y reglas)

> **Cambio de prioridad (sesión 7)**: antes de seguir con entidades/mobs, se define primero la estructura de reglas del mundo — de dónde sale todo, qué ramas existen, cómo se relacionan entre sí. Las entidades (como El Titán) se van a enganchar después a estas ramas (por ejemplo: ¿el Titán es producto de la Magia? ¿de la Alquimia? — se define más adelante).

### El Núcleo del Vacío — herramienta de Magia, explotada industrialmente por Tecnología

**Nombre confirmado: Núcleo del Vacío.** Es la herramienta con la que la Magia canaliza la Filtración de forma personal/directa (ver "Maná del jugador"). Lo que hace especial a **Tecnología** es que, en su etapa avanzada, no inventa un método propio desde cero — **explota/industrializa los métodos de acceso que ya usan las otras ramas**, llevándolos a escala:

- **Del Núcleo del Vacío de la Magia**: una versión industrializada/sobre-explotada del mismo conducto que usa un Mago, pero a escala de maquinaria en vez de uso personal.
- **De las plantas de Botánica**: Tecnología podría cultivar/procesar en masa las mismas plantas tocadas por el Vacío que Botánica usa de forma orgánica y a pequeña escala.
- **Del proceso elemental de Alquimia**: de igual forma, industrializando el uso del Vacío como 5to elemento en vez del proceso manual/artesanal de un alquimista.
- Esto le da a Tecnología una identidad clara dentro de Ciencia: no es "otra forma más" de acceder a la Filtración, es la que **toma prestados y escala** los métodos de todas las demás — coherente con que Ciencia "va físicamente a buscar" la energía de forma más agresiva que las otras ramas.
- _(pendiente: cómo se consigue/craftea el Núcleo del Vacío, si hay un solo tipo o varios, mecánica concreta de cómo Tecnología "escala" cada método)_

### Sobre la dimensión del Vacío

- **Sí va a ser una dimensión jugable** (en términos técnicos de Minecraft, como el Nether o el End) — el jugador va a poder visitarla en algún momento. **Cómo se llega ahí queda para mucho más adelante**, no es prioridad ahora.
- **En términos de lore, no cambia la metáfora ya establecida**: aunque mecánicamente el juego las trate como dos dimensiones separadas (como cualquier mod maneja el Nether), narrativamente el Vacío y el Overworld siguen ocupando "el mismo lugar" — las dos tazas superpuestas. La dimensión jugable sería, en el fondo, una forma de representar mecánicamente ese mismo espacio superpuesto, no un lugar aparte en la ficción.

### Regla fundamental: la energía del Vacío

**Todas las ramas (Ciencia y Magia, y sus 6 sub-ramas) usan la misma fuente: la energía del Vacío.** Esto es lo que las conecta a todas entre sí, y es la base de todo el sistema:

- No son sistemas aislados — Alquimia, Botánica, Tecnología, Encantamientos, Hechizos y Rituales son distintas **formas de canalizar/manipular** la misma energía de origen, no seis mecánicas independientes sin relación.
- **Se pueden combinar entre sí.** Ciencia y Magia no son mutuamente excluyentes para el jugador. Por ejemplo: un objeto podría requerir un proceso de Alquimia (Ciencia) y un Encantamiento (Magia) para terminar de funcionar, ya que en el fondo ambos procesos mueven la misma energía.
- Esto abre la puerta a mecánicas de progresión/crafteo cruzado entre ramas más adelante (a definir).

### Regla de diseño: progresión de mundano a Vacío

Patrón repetido en las ramas ya definidas — ya no es casualidad, es una regla consistente de diseño:

| Rama | Etapa mundana (base) | Etapa avanzada (Vacío) |
|---|---|---|
| Alquimia | Elementos clásicos (Fuego, Agua, Tierra, Aire) | Vacío como 5to elemento, riesgoso |
| Tecnología | Fuentes normales (agua, viento) | "Exprimir" la Filtración |
| Botánica | Plantas elementales mundanas | Plantas nacidas/tocadas por el Vacío |

Cada rama empieza segura y accesible, y el Vacío aparece siempre como el salto de poder de la etapa avanzada — con más riesgo, coherente con que forzar al Vacío tiene consecuencias (el mismo patrón que causó el colapso original). Al definir ramas pendientes (Hechizos, Rituales, Encantamientos, Herrería), conviene chequear si este mismo patrón aplica, para mantener el sistema consistente.

### Regla de diseño: los 6 elementos son el lenguaje de todo el mundo, no solo de Alquimia

El árbol de 6 elementos primarios y sus 45 compuestos (ver "Alquimia — detalle mecánico" para el árbol completo) **no es un sistema exclusivo de Alquimia** — es cómo funciona el mundo al fin y al cabo. Alquimia es simplemente la rama que lo manipula de forma más directa/pura, pero el mismo lenguaje elemental aplica al resto del sistema:

- **Hechizos**: ya reutiliza los 4 elementos clásicos para atacar — con este árbol, también podría tener hechizos de compuestos (un hechizo de Oscuridad, uno de Vida/Florecimiento, etc.), no solo de los primarios.
- **Imbuir**: los efectos (Veneno, Fuego, y los que se agreguen) podrían mapearse directo a elementos/compuestos existentes en vez de inventarse aparte — por ejemplo, Veneno podría ser directamente el compuesto Miasma.
- **Encantamientos**: los encantamientos nuevos podrían tener una afinidad elemental propia en vez de ser genéricos.
- **Botánica**: cada planta podría estar ligada a un elemento o compuesto específico, no solo a los 4 clásicos mundanos de su etapa base.
- **Rituales**: qué entidad sale de un ritual podría depender de qué elemento domina ese ritual (una criatura de Corrosión vs. una de Estabilidad, por ejemplo).
- **Tecnología**: distintos tipos de Fuerza Motriz o maquinaria podrían estar ligados a un elemento (una máquina "de Vapor" ya casi lo pide sola, dado que Vapor es un compuesto ya definido de Fuego+Agua).
- _(pendiente: aplicar esto en detalle rama por rama — por ahora queda como regla general confirmada, las conexiones concretas se completan cuando se trabaje cada rama)_

### El Rugir — consecuencia máxima compartida por sobre-explotar el Vacío

Resuelve la pregunta pendiente de "qué pasa cuando te pasás con el Vacío": **cada rama tiene su propia escalada de síntomas particulares, pero todas convergen en el mismo techo catastrófico — el Rugir.**

- **Qué es**: el Rugir es tanto un evento como un lugar — es una recreación **local y a pequeña escala** del colapso original del Vacío, causada por sobre-exprimir la Filtración en un punto. Es literalmente **el sitio de donde salen las entidades del Vacío hacia el Overworld** (igual que el colapso global original generó entidades como El Titán, un Rugir local hace lo mismo pero a escala de esa zona).
- **Escalada progresiva antes de llegar al Rugir** (específica por rama, con su propio sabor):
  - **Tecnología**: 1) las máquinas simplemente se apagan (advertencia leve), 2) algo explota (daño real), 3) en el extremo, sobre-exprimir de verdad **invoca un Rugir**.
  - **Botánica**: sobre-explotar una zona puede dejarla **infértil** (ya no se puede volver a cultivar/generar ahí), y en el extremo también puede desencadenar un Rugir.
  - **Alquimia**: ya se había planteado Disonancia (corrupción del jugador) y Filtración (corrupción del terreno) como posibles castigos, sin decidir cuál — probablemente ambas son síntomas de la escalada temprana, con el Rugir como su techo también, siguiendo el mismo patrón.
  - **Rituales**: ya se definió que un ritual fallido puede generar criaturas peligrosas — un Rugir sería la versión extrema de ese mismo fallo.
- **Por qué funciona bien**: le da consistencia a todo el sistema de riesgo sin forzar que cada rama tenga el mismo castigo — cada una se siente distinta en el camino (apagones vs. infertilidad vs. corrupción vs. criaturas), pero todas comparten el mismo "fin del mundo en miniatura" si el jugador realmente se excede.
- _(pendiente: nombre alternativo si "Rugir" no convence del todo, qué tan fácil/difícil es llegar a ese extremo, qué tan grave es un Rugir en términos de gameplay — ¿se puede cerrar? ¿dura para siempre? ¿escala en intensidad con el tiempo?)_

### Qué es El Vacío

- **No es un lugar aparte — ocupa el mismo espacio que el Overworld, al mismo tiempo.** Analogía de referencia: como si en una caja donde solo cabe una taza, hubiera dos tazas ocupando el mismo lugar al mismo tiempo. El Vacío y el Overworld se superponen; normalmente solo se puede percibir/ver uno de los dos a la vez.
- **Es concepto, caos y energía a la vez** — no es una sola cosa definible, es la fuente indiferenciada de la que salen todas las ramas (Ciencia y Magia).
- **Crea y destruye.** Las entidades (como El Titán) son creaciones del Vacío — no son criaturas "diseñadas" por Magia o Ciencia directamente, sino consecuencias directas del colapso del Vacío por el abuso/presión de ambas ramas sobre él.
- **Es fundamentalmente inentendible.** No se puede comprender algo que no quiere ser comprendido y que ni siquiera se entiende a sí mismo. Esto es central: cualquier intento de "explicarlo" del todo choca con su propia naturaleza caótica.

### Cómo cada rama accede a su energía

- **Magia**: acepta al Vacío tal cual es, **sin intentar entenderlo**. Canaliza su energía a través de cristales (nombre específico pendiente de definir) que actúan como intermediario/conducto. **La fuente ambiental de esa energía es la Filtración por zona**, pero el jugador está limitado por su propio **Maná** (ver secciones "Filtración por zona" y "Maná del jugador" más abajo) — son dos recursos distintos, no uno solo.
- **Ciencia**: va **físicamente** a buscar la energía — un acercamiento activo, de exploración material, y además **intenta comprender y entender** al Vacío (a diferencia de la Magia, que no lo intenta).

### El colapso: origen de las entidades

El Vacío es caos e inentendible por naturaleza. Al seguir siendo forzado — la Magia exprimiendo cada vez más su poder, y la Ciencia insistiendo en comprenderlo — **el Vacío colapsó**, y ese colapso empezó a enviar entidades al Overworld. **Así es como nacen entidades como El Titán**: no son criaturas "diseñadas" por Magia o Ciencia, sino consecuencias directas del colapso del Vacío por el abuso/presión de ambas ramas sobre él.

**El colapso es un evento cerrado, ya ocurrió.** El mundo que explora el jugador es la "cicatriz" que dejó: las entidades ya están sueltas en el Overworld como resultado directo de ese colapso pasado, no de un proceso que siga empeorando en tiempo real. Esto da un punto de partida narrativo fijo y claro para cuando se retome la historia/lore más adelante.

### Tecnología / Mecánica — inspirado en Create (no copiado)

Lo que hace querido a Create no es "tener máquinas", sino la **energía cinética física y visible**: ejes y engranajes que giran de verdad y transmiten fuerza a través de la construcción, contraptions (estructuras que se mueven), y la satisfacción de ver ingeniería funcionando en tiempo real en vez de una barra de progreso abstracta.

- **Mecánica central: fuerza física/cinética, no energía abstracta.** Nombre propio: **Fuerza Motriz**. En vez de "FE" o "RF" invisibles, algo transmitido visiblemente (ejes, engranajes, poleas) que el jugador arma y ve funcionar. El nombre se mantiene igual sin importar la fuente (agua/viento en la etapa mundana, Filtración exprimida en la avanzada).
- **Progresión de fuentes de energía, igual que Alquimia**: empieza con fuentes mundanas normales (agua, viento — como los molinos de agua/viento reales, sin nada del Vacío de por medio) para la maquinaria básica. Recién en una etapa avanzada se empieza a **"exprimir" la Filtración** para obtener Fuerza Motriz mucho más potente — el mismo verbo que describe por qué colapsó el Vacío originalmente (la Magia exprimiendo su poder). Escalada de riesgo definida: máquinas apagándose → explosiones → un Rugir en el extremo (ver "El Rugir").
- **Conexión con el lore**: la Ciencia ya está definida como "va físicamente a buscar la energía del Vacío" — la etapa avanzada de Tecnología es la culminación literal de eso: máquinas que procesan/refinan Filtración extraída directamente del chunk.
- **Contraptions (estructuras móviles)**: se mantiene la idea de construcciones que se mueven físicamente — podría ser la base técnica para los constructos/golems que ya se mencionaron como creaciones de Ciencia.
- _(pendiente: mecánicas concretas de extracción/refinado de Filtración, qué máquinas existen)_

### Botánica — inspirado en Botania (no copiado)

Lo más querido de Botania es que **no usa GUIs ni tuberías/cables** — todo pasa en el mundo (partículas, rayos de energía fluyendo entre flores en tiempo real), es lento pero no se siente como grindear, y ya separa flores por día/noche (unas generan de día, otras de noche) — algo que encaja de forma casi perfecta con la mecánica central del modpack.

- **Mecánica central: interacción en el mundo, sin menús.** Plantas/flores especiales que generan y usan un recurso, con partículas/efectos visibles en vez de una interfaz.
- **El recurso es directamente la Filtración — resuelto, sin nombre nuevo**: las plantas de Botánica no generan ni usan una sustancia propia, absorben y canalizan la misma Filtración que ya comparten todas las ramas (ver "Filtración por zona"). Simplifica el sistema: una sola energía, múltiples formas de tocarla — Botánica es la forma orgánica/visible (plantas), a diferencia del Núcleo del Vacío (Magia) o la maquinaria (Tecnología).
- **Progresión de plantas, mismo patrón que Alquimia y Tecnología**: empieza con **plantas elementales mundanas** — cada una ligada a uno de los 4 elementos clásicos (Fuego, Agua, Tierra, Aire), sin necesidad de energía del Vacío. En una etapa avanzada aparecen **plantas nacidas o tocadas por el Vacío** — creadas directamente por él, o que absorbieron Filtración directamente — más raras y poderosas que las elementales.
- **Conexión con el lore**: las plantas avanzadas crecen literalmente donde el Vacío se filtró hacia la superficie — mismo concepto que ya usa Tecnología para su etapa avanzada, aplicado a algo vivo/orgánico en vez de maquinaria.
- **Gancho directo con el ciclo día/noche**: al igual que Botania separa flores de día/noche, esta rama podría tener plantas que solo generan/florecen de noche (coherente con "noche = calma, momento seguro para cuidar el jardín") y otras que solo funcionan de día pero son riesgosas de cosechar (coherente con "día = peligro"). Esto le daría a Botánica una identidad de rama "tranquila", en contraste con el riesgo de Alquimia y la industria de Tecnología.
- _(pendiente: qué tipos de flores existen para cada elemento, cómo son las plantas nacidas del Vacío, mecánicas concretas)_

### Sistema de clases / árbol de habilidades

**Idea central**: un solo pool de puntos de habilidad, con **múltiples árboles que coexisten** (no se excluyen entre sí) — coherente con la regla general del sistema ("todo se puede combinar, nada es excluyente"). El jugador elige libremente dónde invertir sus puntos, permitiendo desde builds puras hasta híbridas.

- **Árboles "mundanos" disponibles desde el inicio** (sin necesidad del Núcleo del Vacío):
  - **Guerrero / Tanque / Pícaro**: combate cuerpo a cuerpo, ligado a la Herrería (arma/armadura como base física).
  - **Cazador**: combate a distancia sin magia — arcos, trampas, y una **mecánica de domesticación de criaturas como seña de identidad de esta clase**. Inspirado en el Hunter de World of Warcraft (no copiado): la mascota no es solo decorativa, cumple un rol táctico real en combate (tanque, daño, o utilidad), y el vínculo se profundiza con el tiempo (lealtad/entrenamiento). Posible cruce con Herrería (armas del Cazador) y/o Botánica (trampas, venenos).
- **Árbol de Mago**: se desbloquea al conseguir el Núcleo del Vacío y completar lo necesario para acceder al "mundo de la magia". **Se agrega en paralelo a los árboles mundanos, no los reemplaza ni los bloquea** — un jugador puede seguir invirtiendo en Guerrero/Cazador después de desbloquear Mago, o combinar ambos (ej: un "Spellblade" mitad guerrero, mitad mago).
- **Por qué no bloquear contenido**: bloquear un árbol al elegir otro le pondría un candado a la exploración del jugador y rompería la filosofía de combinación libre que ya define el resto del sistema (Ciencia+Magia, Herrería+todo). El cristal sigue siendo un hito importante (abre un árbol entero nuevo), solo que sin castigar lo que el jugador ya eligió antes.
- _(pendiente: mecánicas concretas de domesticación del Cazador, contenido detallado de cada árbol, cómo se obtienen los puntos de habilidad)_

### Magia — propósito general

A diferencia de Ciencia (que se sintetiza igual en las 3 sub-ramas: física, exploratoria, busca comprender), **la sensación de usar Magia varía mucho según la sub-rama** — no hay una regla única de "así se siente la Magia". Se define sub-rama por sub-rama, empezando por Encantamientos.

### Encantamientos

- **Base vanilla + expansión**: se mantienen los encantamientos normales de Minecraft como punto de partida, pero se agregan muchos más (a definir cuáles).
- **La mecánica de encantar cambia**: no es la mesa de encantamiento + niveles de experiencia + estantería tal cual vanilla — el proceso en sí se rediseña (forma exacta aún sin definir).
- **Permanentes**: un encantamiento, una vez aplicado, no se pierde ni se degrada con el uso.
- _(pendiente: cuáles son los encantamientos nuevos, cómo es la nueva mecánica de encantar, si usa el cristal/energía del Vacío como parte del proceso)_

### Imbuir (efectos temporales — distinto de Encantamientos)

Sistema paralelo a Encantamientos, con una lógica opuesta en el punto clave:

- **No es permanente** (a diferencia de Encantamientos, que sí lo son) — un arma/armadura/herramienta imbuida con un efecto (ej: veneno) lo pierde con el tiempo o el uso.
- **La fuerza varía según el efecto y la fuente**: algunos efectos imbuidos funcionan *mejor* que su encantamiento equivalente, otros funcionan *peor* — depende del encantamiento específico y de con qué rama se imbuyó. No hay una regla fija de "imbuir siempre es más fuerte/débil que encantar".
- **Es un sistema cruzado entre tres ramas**: se puede imbuir usando **Magia**, **Botánica**, o **Alquimia** — cada una probablemente aportando efectos distintos (ej: veneno con toque de Botánica, algo distinto desde Magia o Alquimia).
- **Ejemplos concretos de tipo de daño/efecto** (inspirado en las afinidades de armas de Elden Ring, no copiado): imbuir un arma con **Veneno** para que haga daño de veneno, o con **Fuego** para que haga daño de fuego, en vez de (o sumado a) su daño físico normal. El arma base es la misma; el efecto imbuido cambia qué tipo de daño hace.
- Refuerza la regla general del sistema: nada está aislado, esto es otro punto de cruce entre ramas.
- **Aporte concreto de Alquimia — confirmado: la Resina Imbuidora.** Un ítem líquido/viscoso, craftable, que se aplica sobre un arma o herramienta (right-click, se consume) para imbuirla con un elemento del árbol de 45. Se crea en la Mesa de Mezclas combinando un **Frasco del elemento/compuesto deseado + una base viscosa**. Dos ejes independientes determinan el resultado final:
  - **Eje 1 — material base (duración)**: Bola de Slime → duración corta, fácil de conseguir, para pruebas tempranas. Resina vanilla (del Pale Garden/Creaking) → duración larga, más rara y tardía de conseguir.
  - **Eje 2 — nivel del elemento usado (potencia del efecto)**: Nivel 0 (primario puro: Fuego, Agua, etc.) → efecto básico, el más débil. Nivel 1 (compuesto de una rama: Vapor, Corrosión, Templanza, etc.) → efecto medio. Nivel 2 (fusión entre ramas: Oscuridad, Muerte, Claridad, etc.) → efecto fuerte, el más raro y poderoso.
  - Ambos ejes se combinan libremente (ej: una Resina de Fuego con Slime es la más débil y corta de todas; una Resina de Oscuridad con Resina vanilla es de las más fuertes y duraderas del sistema) — le da progresión real a Imbuir, desde lo básico y accesible hasta compuestos raros del árbol completo.
  - Ejemplos: Frasco de Fuego + Slime → Resina Imbuidora de Fuego (quema al golpear). Frasco de Muerte + Resina vanilla → Resina Imbuidora de Veneno de alta potencia (mismo elemento que ya usa la Poción de Veneno).
- **Aporte concreto de Botánica — confirmado: resina cultivada, no ensamblada.** A diferencia de Alquimia (imbuye *ensamblando* Frasco + base viscosa en la Mesa), Botánica imbuye *cultivando* — coherente con que ya es una rama de "recurso pasivo generado con el tiempo", no de proceso activo de laboratorio.
  - Ciertas plantas (todavía sin definir cuáles) pueden "sangrar" resina si se las cultiva bajo ciertas condiciones — ej. regadas con un Frasco de un elemento específico como si fuera fertilizante, o plantadas en tierra con cierto nivel de Filtración.
  - Qué combinación de planta + condición da qué tipo de resina se descubre **experimentando**, mismo principio de descubrimiento que el resto del sistema — nadie le dice al jugador de entrada qué planta con qué condición da qué elemento.
  - La resina resultante ya sale con el elemento incorporado — se cosecha lista para usar directo sobre el arma, sin pasar por una Mesa de Mezclas como en Alquimia.
  - **La Resina vanilla (del Roble Pálido/Pale Garden) queda reasignada a Botánica**, no exclusiva de Alquimia — tiene más sentido narrativo al ser un producto vegetal real. Alquimia sigue pudiendo usarla igual como base de larga duración para su Resina Imbuidora (no es excluyente, ambas ramas la aprovechan cada una a su manera).
- _(pendiente: lista completa de efectos imbuibles más allá de Veneno/Fuego, reglas de cuándo un efecto imbuido supera o no a un encantamiento equivalente, qué plantas concretas de Botánica sangran resina y bajo qué condiciones, aporte concreto de Magia a Imbuir — todavía sin definir)_

### Hechizos

- **Propósito principal: combate.** Es la forma en la que los Magos (ver "Sistema de clases") atacan — el equivalente mágico al arma de un Guerrero o al arco de un Cazador. Otros usos posibles aún sin definir.
- **Sub-rama elemental**: Hechizos de Agua, Tierra, Fuego y Aire — reutiliza los mismos 4 elementos clásicos ya definidos en Alquimia (ver sección Alquimia), en vez de inventar una paleta distinta. Da consistencia visual/conceptual a todo el sistema: el mismo Fuego que se usa para transmutar en Alquimia es el que lanza un Mago en combate.
- _(pendiente: si hay hechizos no-elementales o de utilidad, si el Vacío también tiene su propia sub-rama de hechizo (como pasa en Alquimia con el 5to elemento), mecánica concreta de lanzar/apuntar hechizos, costo de uso — ¿maná propio, o algo distinto?)_

### Rituales

- **Mecánica central: crear entidades mediante rituales.** No transmutación de objetos ni efectos consumibles (eso es Alquimia/Hechizos) — Rituales invoca/crea criaturas.
- **Entidades de utilidad**: el objetivo principal es crear ayudantes con roles concretos — cuidar un huerto, proteger al jugador, organizar/ordenar cofres, o servir de montura.
- **Riesgo real de que salga mal**: por error (fallar el ritual) o incluso a propósito (buscando algo más poderoso), pueden salir **criaturas peligrosas** en vez de la entidad de utilidad esperada.
- **Conexión directa con el lore del Vacío**: esto es, a escala del jugador, una versión pequeña y controlable del mismo proceso que generó entidades como El Titán en el colapso global del Vacío — invocar mediante Rituales es "forzar" al Vacío a crear algo, con el mismo riesgo de que salga mal que tuvo el colapso original.
- **Contraste con los golems de Ciencia**: Ciencia *construye/ensambla* de forma predecible y controlada (ingeniería, ver Tecnología/Herrería); Magia *invoca* con incertidumbre real — coherente con que el Vacío es caos por naturaleza. Dos caminos distintos para llegar a "tener un ayudante", con distinta sensación de riesgo.
- **Contraste Ritual fallido vs. Constructo fallido — confirmado (sesión 70).** Ambos pueden fallar, pero de formas distintas que reflejan la naturaleza de su rama — no es "uno puede fallar y el otro no", es que cada uno falla distinto:
  - **Ritual fallido (Magia)**: falla de origen — el proceso nunca estuvo del todo bajo control desde el principio. Cuando sale mal, sale directamente una criatura hostil no planeada, algo completamente distinto a lo que se buscaba.
  - **Constructo fallido (Ciencia)**: falla de ejecución — el proceso es predecible y controlado en su diseño, pero si se fuerza demasiado el Vacío para energizarlo (etapa avanzada, "exprimiendo" Filtración), el constructo puede terminar defectuoso o corrompido: sigue siendo el golem que se quiso construir, pero con fallas de comportamiento (agresivo sin motivo, errático, se rompe antes de tiempo) — no una criatura random distinta como en Rituales.
  - Coherente con la regla general de que cada rama tiene su propia escalada hacia El Rugir (Tecnología: apagones→explosiones; Botánica: infertilidad; Rituales: criatura random; Constructos de Ciencia: corrupción de comportamiento).
- _(pendiente: mecánica exacta del ritual, qué determina si sale bien o mal, tipos concretos de entidades de utilidad, qué tan peligrosas pueden ser las criaturas fallidas)_

### Progresión general del jugador

Dos sistemas de progresión distintos, no una sola barra:

**1. Progresión de conocimiento (todas las ramas)**
- **Se aprende haciendo**: tanto Ciencia (y sus sub-ramas) como Magia (y las suyas) se aprenden practicando — mismo principio que ya se definió para Alquimia (descubrimiento por experimentación), extendido como regla general a todo el sistema, no solo a esa rama.
- **Ligado al Diario**: cada descubrimiento se registra en el apartado correspondiente del Diario (ver sección "El Diario") — es la versión propia del Thaumonomicon, pero para todas las ramas, no solo Alquimia.
- **También se aprende explorando**: encontrar notas, ruinas y lugares similares revela conceptos sin necesidad de descubrirlos por experimentación propia — el detalle concreto de esto (qué ruinas, qué notas, dónde) se define mucho más adelante.

**2. Progresión de combate (árbol de habilidades / clases)**
- **Se sube de nivel matando monstruos y entidades**: ganar experiencia por combate es lo que permite invertir más puntos en los árboles de clase (Mago, Guerrero, Tanque, Cazador, etc. — ver "Sistema de clases").
- Es un sistema separado del conocimiento — un jugador podría saber mucho de Alquimia sin ser buen combatiente, o ser un Guerrero de nivel alto sin haber tocado casi nada de Ciencia/Magia.

### Filtración por zona (mecánica transversal — afecta a toda rama que use energía del Vacío)

Inspirado en el sistema de Vis por chunk de Thaumcraft (energía mágica con concentración distinta en cada chunk, variable según bioma, con nodos raros de alta concentración) — adaptado con nombre propio y conectado al lore existente:

- **No es energía pareja en todo el mundo**: cada chunk tiene su propio nivel de Filtración — coherente con la metáfora de las dos tazas superpuestas (Vacío y Overworld no se superponen con la misma intensidad en todos lados).
- **Varía según el bioma, alineado al sistema de 5 elementos**: por ejemplo, zonas cerca del Nether o volcánicas tendrían más Filtración de Fuego, océanos más de Agua, etc. — reutiliza el mismo sistema elemental de Alquimia/Hechizos en vez de inventar un sistema paralelo.
- **Puntos raros de alta concentración**: lugares específicos donde el Vacío está anormalmente cerca de la superficie — valiosos para asentarse y extraer energía. Podrían correlacionar con dónde aparecen entidades como El Titán (no es casualidad, es justo donde el Vacío se filtra más fuerte).
- **Se agota y regenera con el tiempo**: le da al jugador una razón real para explorar y moverse, en vez de asentarse en un solo punto para siempre.
- **Es la fuente ambiental de la que se extrae la magia**: la Filtración es el "pozo" del mundo, no el límite del jugador — ver "Maná del jugador" abajo para la diferencia clave.
- _(pendiente: nombre propio para los "puntos de alta concentración", cómo se detectan/miden, tasa de agotamiento/regeneración, relación exacta bioma↔elemento)_
- **Confirmado**: el modpack soporta un jugador y varios (multijugador). **Confirmado también**: la Filtración de un chunk es un **recurso compartido y competitivo** entre jugadores en multijugador — un jugador con Tecnología industrial puede agotarla para los demás en esa zona. Esto le da peso estratégico real a la ubicación en partidas multijugador (competencia por zonas de alta Filtración).

### Maná del jugador (distinto de la Filtración del mundo)

Dos recursos separados, no uno solo:

- **Filtración** = cuánta energía del Vacío hay disponible en el chunk/zona (recurso del mundo, ej: 300 de energía en un chunk).
- **Maná** = el límite físico/mental **propio del jugador** para canalizar esa energía (ej: empieza en 50, sin importar cuánta Filtración haya alrededor). No es "cuánta energía hay", es "cuánta puede manejar esta persona a la vez".
- **Sensación de cansancio, no solo un número**: con poco Maná, el jugador debería sentirse "cansado" de usar magia — no es solo una barra que llega a cero, es una limitación física/mental real del personaje.
- **El Maná crece con progresión**: a medida que el jugador aprende a controlar mejor la magia (sube de nivel, invierte en el árbol de Mago — ver "Sistema de clases"), ese límite personal aumenta. Empezar con Maná bajo y expandirlo es parte de la progresión del Mago.
- **Aplica solo a la magia del jugador** (atacar con Hechizos, movilidad mágica, etc.) — **no** a bloques, mecanismos o mecánicas del mundo (esos tendrán su propio límite propio, o ninguno, a definir caso por caso — no comparten esta regla del Maná del jugador).
- _(pendiente: valores concretos de progresión, cómo se sube el límite exactamente, si hay formas de recuperar Maná más rápido)_

### Herrería — capa neutral, fuera del árbol del Vacío

A diferencia de las 6 sub-ramas de Ciencia y Magia, la **Herrería no usa energía del Vacío en absoluto** — es el oficio físico/mundano de forjar metal en armas, armaduras y herramientas, tal como existía antes (o al margen) de todo el sistema del Vacío.

- **No es una sub-rama de Ciencia ni de Magia, ni un tercer camino paralelo** — es una capa aparte, la base física común del mundo.
- **Se combina con TODAS las demás ramas**, potenciando lo que produce:
  - Con **Alquimia**: templar/tratar un arma o herramienta forjada con elementos alquímicos, haciéndola más fuerte.
  - Con **Magia** (Encantamientos): un arma forjada puede encantarse después, combinando ambos procesos.
  - Con **Tecnología**: mecanismos que aceleran o automatizan el proceso de forjado en sí (ej: un martillo hidráulico movido por fuerza cinética).
  - En general: la Herrería produce la "base" (el arma/herramienta/armadura cruda), y las demás ramas la mejoran o la especializan.

**Sistema de refinado de minerales — confirmado, cambia la progresión vanilla de raíz.**

- **Metales que se funden** (Hierro, Oro, Cobre, y similares) tienen **3 etapas**:
  1. **[Metal] Sucio**: lo que da el horno vanilla al fundir el mineral en bruto — no es un lingote limpio, todavía tiene residuos de tierra. Reemplaza la textura y receta del lingote de hierro vanilla.
  2. **[Metal] Refinado**: requiere Herrería — sí o sí, no hay atajo. Con esto se craftea la primera armadura "de verdad" de ese metal.
  3. **[Metal] Puro**: el metal en su estado más puro, siguiente escalón de Herrería, con su propia armadura mejorada.
  - **Armadura de Hierro Sucio** (con textura y receta nuevas: Hierro Sucio + Cuero) es la única excepción **jugable antes de tener acceso a Herrería** — el punto de entrada mundano al juego, coherente con que el Hierro es la primera etapa de progresión en vanilla.
- **Gemas que no se funden** (Diamante, Esmeralda, y similares) **no tienen etapa Sucia** — se minan ya limpias como gema. Van directo a necesitar Herrería para volverse "Refinado" y ser utilizables como armadura/herramienta — **sin excepción ni atajo mundano**, a diferencia del Hierro.
- **Aplica a todos los minerales del juego**, no solo a Hierro y Diamante — mismo patrón de 2 o 3 etapas según si el mineral se funde o no.
- **Camino alternativo confirmado: Alquimia también puede llegar a un [Metal] Refinado, sin depender de la estación de Herrería.** Ejemplo confirmado: **Oro Sucio + frasco de Maestría (compuesto de Orden) → Oro Refinado**, hecho en la Mesa de Mezclas de Alquimia. No es un ítem distinto ni un sistema paralelo — es el mismo Oro Refinado, alcanzado por otro camino. Le da al jugador una elección real: invertir en Herrería, o invertir en Alquimia, para llegar al mismo resultado. Cada metal podría tener su propio compuesto 