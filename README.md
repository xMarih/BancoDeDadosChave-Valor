
# Banco de Dados Chave-Valor
**Trabalho Final desenvolvido para a disciplina de AED3**

**Prof.** Pedro Henrique Penna

Graduação em **Engenharia de Computação**

Pontifícia Universidade Católica de Minas Gerais (PUC Minas)

# Desenvolvedores

Abraão Melo Fagundes

Cristian Fernandes Sena

Guilherme Marcos Pereira Goncalves

Mariana Eliza Alves Costa

# Resumo

Nesse trabalho foi desenvolvido um banco de dados de chave-valor com os seguintes
requisitos funcionais:

• Inserir / Remover / Buscar / Atualizar

• Listar / Filtrar 

• Compactar / Descompactar 

Mais detalhes em "Proposta.txt"


# Distribuição de Pontos
Esse trabalho será avaliado da seguinte forma:
|                  Critério                     |  Porcentagem da Pontuação  |
|:---------------------------------------------:|:--------------------------:|
| Corretude da Solução                          | 60%                        |
| Implementação de Testes                       | 20%                        |
| Qualidade e Documentação de Código            | 10%                        |
| Participação de Todos os Integrantes do Grupo | 10%                        |

# Distribuição de Pontos Extras

• Esboçar um diagrama de classes do projeto usando uma ferramenta de UML (1 ponto).

• Integrar a compilação do projeto com um ambiente de integração contínuo, como Github Actions, Jenkins ou TravisCI (1 ponto).

• Automatizar a compilação do projeto usando o sistema make ou cmake (2 pontos).

• Realizar a avaliação de desempenho da sua solução. (3 pontos)

# Avaliação de Desempenho dos Algoritmos

Este projeto foi divido em 3 partes importantes que iremos analisar o desempenho, cada uma destas possui implementações de algoritmos conhecidos e ensinados na disciplina de Algoritmo e Estrutura de Dados III. 

•	Inserir / Remover / Buscar / Atualizar: Neste algoritmo o principal efeito de otimização foi utilizar um arquivo indexado para armazenar os dados criados. O algoritmo para busca utilizado 

Inserir: O seu tempo de execução foi em média de 
Remover: O seu tempo de execução foi em média de
Buscar: O seu tempo de execução foi em média de


•	Listar / Filtrar: Para a filtragem e listagem foi utilizado a classe “readByFilter” pelos testes feitos o algoritmo teve a complexidade de o(n) e possuiu em média o tempo de

•	Compactar / Descompactar: Utilizamos dois algoritmos para desenvolver a compactação e descompactação:

Código de Huffman: Este algoritmo possui duas chamadas na main “HuffmanCodificador.Codificar” e “HuffmanDecoder.Decodificador” pelos testes feitos apresentou a complexidade o(n) em um tempo médio de execução de aproximadamente 

Algoritmo LZW: Este algoritmo possui duas chamadas na main “lzw.compressFile” e “lzw.decompressFile” pelos testes feitos apresentou a complexidade o(n) em um tempo médio de execução de aproximadamente 

# Diagrama de Classes

![ClassDiagram-alt-tag](https://github.com/xMarih/BancoDeDadosChave-Valor/blob/master/img/Class%20Diagram.png)
