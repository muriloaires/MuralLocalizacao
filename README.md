# MuralLocalizacao
## Projeto de contexto acadêmico ##
## Todos os direitos reservados a Universidade Federal de Goiás ##

1 - Do objetivo da execução do projeto:
• A execução do projeto tem como principal objetivo o aprendizado do discente no que se refere à computação ubíqua e suas particularidades.
• Aprendizado aprofundado na plataforma Android
• Incorporação do módulo de localização para o projeto MuralUFG da fábrica de software da UFG
• Promover integração entre os discentes e/ou grupos responsáveis por outros módulos.

2 - Da distribuição do trabalho
• O escopo do módulo de localização foi definido através da primeira necessidade definida nos requisitos conhecidos, presente no documento de abetura do MuralUFG
• A parte de documentação do projeto (que inclui este documento) foi divida entre os integrantes Murilo Aires e Weslley Martins
• A parte de definiçaõ de casos de uso foi desenvolvida pelo integrande Murilo Aires
• O documento de diagrama de classes  foi criado pelo integrante Weslley Martins
• A criação do arquivo JSON, que é fundamental para o projeto, foi feita pelo integrante Higor Quintão
• A implementação do aplicativo foi feita pelos integrantes Murilo Aires e Higor Quintão

3 - Da arquitetura do sistema
* O diagrma de classes se encontra no link Wiki *
• Foi definido que para a aplicação se tornar funcional era necessário a existência de um arquivo JSON, cuja definição também se econtra no Wiki do projeto.
• O arquivo JSON pode ser encontrado no link: https://cdn.fbsbx.com/hphotos-xpt1/v/t59.2708-21/11689338_10206317697528876_581035048_n.json/institutis-1-1-9.json?oh=326aec8ce0b8213dca47d9d09ed844db&oe=55973466&dl=1
• A aplicação se resume em três contextos: Vizualização de todos os Campus, Vizualização do mapa de um determinado campus, e criação de rota a um determinado instituto.
• Foi definido uma classe chamada “Globals” que contem objetos estáticos que serão utilizados durante a execução da aplicação. Essa classe contem Uma lista de Cidades (que corresponde ao arquivo JSON interpretado), um objeto campus selecioado (que é alterado sempre que o usuário clica em um campus na primeira tela da aplicação) e a URL para a localização do arquivo JSON.
• Para vizualização do mapa foi utilizada a API Maps v2 oferecida gratuitamente pela google.

4 - Das dificuldades de implementação
• Dificuldade em adequação ao material design, visto que nosso contexto, com a exceção do mapa, se restringe a simples listas contendo nomes.
• Dificuldade de inserir Toolbar na acitivity de mapas. - Nao realizado
• Não foram criados layouts para adequação para outras resoluções
• O mapa só aparecia carregando em um android studio específico (maquina do Murilo Aires), o que acarretou em muita perda de tempo investigando o que poderia estar acontecendo de errado. Não foi encontrada solução em nenhum fórum de programadores.


