<p align="center">
<img src="/Images/ifsc-logo.png" width="180" height="180">
</p>

**INSTITUTO FEDERAL DE SANTA CATARINA**  
Análise e desenvolvimento de Sistemas - 1ª fase  
ICO7862 - Introdução a Computação  
**Professor:** Diego da Silva de Medeiros  
**Discentes:**  Antonio Marcos da Silva, Bernardo Silva Bombazaro, Érik Crestani Bueno, Pablo Batista Danski

---

<h1 align="center">Robocode: EhOTrem</h1>

## Tópicos

- [Introdução](#1. Introdução)
- [Objetivos da Atividade](#2-objetivos-da-atividade)
- [Descrição da Atividade](#3-descrição-das-atividades)
- [Estrutura do Git Utilizada](#4-estrutura-git-utilizada)
- [Resultado e Aprendizados](#5-resultados-e-aprendizados)
- [Conclusão](#6. Conclusão)
- [Anexos](#7. Anexos)
## 1. Introdução

<p align="justify"> &emsp; O referido relatório tem como objetivo explicitar as etapas realizadas para a elaboração de um robô no aplicativo Robocode, com foco no gerenciamento de suas versões durante o desenvolvimento, por meio da plataforma GitHub. O robô recebeu o nome de EhOTrem, assumindo o pseudônimo de um dos membros do grupo.</p>
<p align="justify"> &emsp; O Robocode é um aplicativo no qual robôs criados pelos usuários batalham entre si, gerando pontuações que, somadas ao final de um número predeterminado de rodadas, definem o vencedor da partida. Além do número de rodadas, é possível predeterminar também o tamanho do campo de batalha, havendo variação de desempenho dos robôs em decorrência do tamanho da arena.</p>
<p align="justify"> &emsp; O GitHub é uma plataforma de desenvolvimento com foco em controle de versões, baseada em Git. Todo o projeto foi desenvolvido mantendo as versões salvas no GitHub, possibilitando a colaboração entre os membros da equipe de forma simultânea, mesmo trabalhando em locais diferentes, mantendo um histórico das versões desenvolvidas por cada membro 
do grupo, servindo também de backup e proporcionando meios de junção de versões diferentes sem perder seu histórico de alterações.</p>

### Técnologias utilizadas
<p display="inline-block">
<img width="32" src="/Images/git.png" alt="GitLogo.png">
<img width="32" src="/Images/github(1).png" alt="GitHubLogo.png">
<img width="32" src="/Images/java.png" alt="JavaLogo.png">
</p>

## 2. Objetivos da atividade




## 3. Descrição das atividades




## 4. Estrutura Git utilizada

O repositório está organizado da seguinte forma:

- **EhOTrem.java**: Código principal do robô. 
- **Images/**: Contém as imagens utilizadas no README.
- **README.md**: Documentação principal do repositório.

Dentro do repositório temos algumas branchs criadas, nas quais todas estão organizadas da seguinte forma:

### 4.1 Aprimorando-mira

Está branch foi criada com o intuíto de melhorar nossa mira, onde observamos que nossa movimentação estava 
muito boa, porém estávamos perdendo muita energia pelos erros de tiro, então essa branch foi para ajustar pontos futuros da
nossa mira e melhorar sua economia de energia.

### 4.2 Aprimorando-movimentacao

Está branch foi criada a fim de corrigir alguns problemas que encontramos na movimentação quando estávamos testando,
como por exemplo, erros de colisão, problemas com mais de um tank em batalha simultânea, fizemos alguns ajustes para que ele pudesse
reagir de uma maneira mais eficaz quando encontrasse estes problemas.

### 4.3 Colisao-tiro

Está branch foi criada quando percebemos que nosso robô tinha o problema de levar tiro e não identificar quando foi
atingido, causando o problema de não reagir quando sofresse dano, então fizemos alteração para que quando fosse atingido, reagisse da
melhor forma, tanto fugindo, quanto revidando.

### 4.4 Corrigindo-erro-de-movimentacao

Está branch foi criada quando haviamos tentado mudar o método de movimentação surf e acabamos quebrando o robô. Ela foi criada com
o intuíto de fazer o robô voltar ao normal, porém dessa vez com as mudanças que tinhamos em mente, como por exemplo, maior imprevesibilidade
e uma melhor perfomace em batalha.

### 4.5 Corzinha

Está branch foi criada com o intuíto inicial de fazer o robô piscar as cores do arco íris a todo momento, porém não conseguimos e 
ela foi criada para apenas piscar as cores somente quando o robô atinge a vitória.

### 4.6 Getting-info

Está branch foi criada com o fim de coletar informações da batalha, como posição de players, dimensão da arena e distância dos mesmos.

### 4.7 Melhorando-colisao 

Está branch foi criada para podermos corrigir o eero de quando o robô batia em algum outro robô adversário, poder identificar o mesmo,
dessa maneira reagindo a ele, não ficando somente parado tentando seguir em frente travado no robô adversário.

### 4.8 Movimentacao-pablo

Está branch foi iniciada para implementar o método de movimentação de surf, porém ela acabou tento um certo problema na hora do merge,
pois acabamos errando na hora do commit, acabou sendo feito um commit de um código enorme, e na hora de dar merge tinha diversos problemas
de mesclagem, porém com muita dedicação e dor de cabeça, no final ocorreu tudo bem.

### 4.9 Movimentacao-antonio

Está branch foi criada delegando a função de movimentação para o Antonio, porém foi decidido posteriormente que a função seria delegada
para mim, então essa branch se encontra vazia e sem informações adicionais.

### 4.10 Movimentacao-travada

Nesta branch estávamos resolvendo problemas do robô quando ele não reconhecia o tamanho da arena, quando isso acabava acontencedo, o
robô ficava travado sem conseguir fazer nada, impossibilitando de fazer algo contra os inimigos.

### 4.11 new-classes

Está branch foi criada a fim de definir alguns construtores e classes que acabaram sendo usadas posteriormente.

### 4.12 ponto-futuro
 
Está branch foi criada para fins de melhorar nossa porcentagem de acertos, nosso robô somente atirava onde o robô inimigo estava, isso
fazia errarmos diversos tiros contra robôs que se moviam bastante, deixando menos eficaz nossa batalha.

### 4.13 sensor-erik

Está branch foi criada a fim de configurar o sensor, porém acaobu sendo preciso implementar junto na movimentação do surf, pois o surf
era dependente do sensor, então esta branch acabou por ficar vazia.

### 4.12 switch

Está branch foi criada com o intuíto de fazer com que o robô tivesse dois modos de batalha, um para quando estivesse sozinho contra outro
robô e mais um modo para quando estivesse com 3 ou mais robôs na arena, porém ele acabou sendo abandonado pela dificuldade e falta de tempo 
que tinhamos restante.

### 4.13 tiro-bernardo

Está branch foi criada com o intuíto de delegar a função de tiro para o Bernardo, porém acabou sendo preciso implementar na parte
de movimentação de surf, pois o método também acabou sendo dependente do tiro.

## 5. Resultados e aprendizados

Trabalhar com o Robocode foi uma experiência desafiadora que nos trouxe ótimos resultados e aprendizados. Ao programarmos nosso robô, tivemos a chance de aplicar conceitos de programação de forma prática, como a organização do código e a reutilização de funções. Isso nos permitiu aprimorar nossa capacidade de estruturar o código de maneira mais eficiente, tornando-o mais claro e fácil de entender, especialmente quando lidamos com o comportamento complexo de um robô.

Além disso, fomos desafiados a implementar estratégias inteligentes para que o robô pudesse tomar decisões durante as batalhas, como antecipar os movimentos do oponente ou escolher a melhor hora de atacar. A experiência de ver essas estratégias em ação e ver o robô evoluir à medida que ajustamos os algoritmos foi muito enriquecedora. A cada batalha, aprendemos algo novo sobre pequenas mudanças no código, como a movimentação do robô ou a precisão dos disparos, poderiam impactar o desempenho.

O processo também nos mostrou como é importante analisar os resultados e testar diferentes abordagens para melhorar o desempenho do robô. A cada derrota ou vitória, surgiam novas ideias, e tivemos que revisar constantemente o código para otimizar a movimentação e a estratégia de combate. No fim, conseguimos perceber o quanto a prática e os testes são essenciais para que um sistema se torne mais eficiente e inteligente.



## 6. Conclusão

<p align="justify"> &emsp; A experiência foi muito enriquecedora, proporcionando a todos os membros da equipe uma vivência de trabalho em equipe que facilmente se encontra no mercado de trabalho e em projetos pessoais. Outro ponto muito importante foi a evolução do grupo como um todo em relação à tecnologia Git e à plataforma GitHub, ferramentas amplamente utilizadas nos meios acadêmicos e profissionais.</p>
<p align="justify"> &emsp; Dentre os lados positivos da atividade realizada, destacou-se o aprimoramento da linguagem Java, em que o grupo desenvolveu um código que não apenas foi satisfatório para o grupo, mas também alcançou a primeira colocação no campeonato realizado entre as equipes.</p>
<p align="justify"> &emsp; As habilidades aprimoradas durante a realização da atividade terão grande importância durante e após a conclusão do curso de Análise e Desenvolvimento de Sistemas. Trabalho em equipe, Git, Linux e Java (conhecimento que também se aplica a outras linguagens de programação) são habilidades que acompanham os programadores e analistas de sistemas durante toda a sua jornada.</p>

<p align="center">
<img src="/Images/victory.jpeg" width="600" height="350">
</p>



## 7. Anexos
