# Problema de Concorrência em Java

Este repositório contém um exemplo simples de código em Java que ilustra um problema comum em programação concorrente, que é a situação em que duas ou mais threads compartilham uma variável e, ao atualizá-la simultaneamente, acabam sobrepondo os valores uns dos outros.

O código deste exemplo apresenta um objeto ContadorCentral que possui uma variável numPessoas que é compartilhada por duas threads, representadas pelas classes Roleta. O objetivo é contar o número de pessoas que entram em um parque através de duas entradas diferentes, cada uma representada por uma thread.

Porém, como as threads compartilham a variável numPessoas, há o risco de que uma thread leia o valor atualizado por outra thread de forma incorreta, o que pode causar uma inconsistência nos resultados finais.

Para solucionar este problema, o código utiliza um bloco synchronized para garantir que apenas uma thread possa atualizar a variável numPessoas por vez, evitando assim a "leitura suja"/"atualização perdida".

Cabe ressaltar que, embora o uso de bloqueios de sincronização possa resolver o problema de concorrência, eles podem afetar negativamente o desempenho do programa, especialmente em sistemas com muitas threads ou em loops com muitas iterações. Portanto, é importante considerar outras estratégias de sincronização, como o uso de semáforos ou monitores, dependendo do contexto do problema.

Para executar o código, basta compilar o arquivo Main.java e executá-lo a partir do terminal com o comando java Main. O programa exibirá a quantidade total de pessoas que entraram em cada uma das duas entradas do parque, bem como o total de pessoas contabilizadas pelo objeto ContadorCentral.
