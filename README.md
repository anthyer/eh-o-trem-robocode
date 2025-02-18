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

- [Introdução](#1-introdução)
- [Objetivos da Atividade](#2-objetivos-da-atividade)
- [Descrição da Atividade](#3-descrição-das-atividades)
- [Estrutura do Git Utilizada](#4-estrutura-git-utilizada)
- [Resultado e Aprendizados](#5-resultados-e-aprendizados)
- [Conclusão](#6-conclusão)
- [Anexos](#7-anexos)
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

<p align="justify"> &emsp; A atividade realizada teve como objetivo principal o exercício das habilidades de utilização dos comandos Git em Linux e sua integração com a plataforma GitHub para manter o controle das versões em nuvem. Além disso, a evolução das habilidades de codificação dos membros do grupo na composição dos robôs, utilizando a linguagem de programação Java.</p>
<p align="justify"> &emsp; Outro ponto abordado foi o trabalho em equipe e a criatividade dos membros do grupo, visto que os robôs foram postos em batalha com os demais robôs criados pelas outras equipes. Dessa forma, foi necessária a colaboração contínua e a discussão de pontos a serem acrescentados e aprimorados no robô para se sair bem na competição realizada.</p>

## 3. Descrição das atividades

<p align="justify"> &emsp; Inicialmente, distribuímos as funções do robô entre os membros do grupo, ficando organizado da seguinte forma:</p>

- Pablo: Movimentação
- Bernardo: Tiro
- Erik: Sensor
- Antonio: Tiro/Sensor
  
<p align="justify"> &emsp; Com o passar do tempo, durante as aulas e nos laboratórios, passamos a discutir e propor ideias sobre possíveis melhorias para o robô. Aos poucos, deixamos de lado a divisão inicial de tarefas específicas para cada integrante e começamos a aprimorar cada parte do robô de forma colaborativa. Isso foi necessário porque os códigos de cada funcionalidade precisavam estar integrados e harmonizados para que o conjunto final funcionasse de maneira perfeita. Por meio de testes práticos nos laboratórios e da avaliação dos resultados obtidos durante os treinamentos, conseguimos chegar ao resultado desejado.</p>
<p align="justify"> &emsp; Adotamos a estratégia de movimento chamada "Surf", combinada com o sensor, para facilitar a esquiva de obstáculos e ameaças. Quando o robô estava com pouca vida, ele se afastava dos inimigos e realizava disparos com menor potência, a fim de recuperar energia. O sensor era responsável por detectar inimigos, tiros direcionados ao robô e as paredes do campo de batalha. Além disso, tivemos uma longa discussão para decidir a cor do robô, que, após muita deliberação, foi definida como magenta. Como toque final, programamos o robô para piscar todas as cores do arco-íris sempre que vencia uma batalha.</p>



## 4. Estrutura Git utilizada

### Estrutura do Repositório

<p align="justify"> &emsp; O repositório está organizado da seguinte forma:</p>

- **EhOTrem.java**: Código principal do robô.
- **Images/**: Contém as imagens utilizadas no README.
- **README.md**: Documentação principal do repositório.

<p align="justify"> &emsp; Dentro do repositório, temos algumas branches criadas, todas organizadas da seguinte forma:</p>

### 4.1 Aprimorando-mira

<p align="justify"> &emsp; Esta branch foi criada com o intuito de melhorar nossa mira. Observamos que nossa movimentação estava muito boa, porém estávamos perdendo muita energia pelos erros de tiro. Então, essa branch foi para ajustar pontos futuros da nossa mira e melhorar sua economia de energia.</p>

### 4.2 Aprimorando-movimentacao

<p align="justify"> &emsp; Esta branch foi criada a fim de corrigir alguns problemas que encontramos na movimentação durante os testes, como erros de colisão e problemas com mais de um tanque em batalha simultânea. Fizemos alguns ajustes para que o robô pudesse reagir de maneira mais eficaz a esses problemas.</p>

### 4.3 Colisao-tiro

<p align="justify"> &emsp; Esta branch foi criada quando percebemos que nosso robô tinha o problema de levar tiro e não identificar quando foi atingido, causando o problema de não reagir ao sofrer dano. Fizemos alterações para que, ao ser atingido, o robô reagisse da melhor forma, seja fugindo ou revidando.</p>

### 4.4 Corrigindo-erro-de-movimentacao

<p align="justify"> &emsp; Esta branch foi criada quando tentamos mudar o método de movimentação surf e acabamos quebrando o robô. Ela foi criada com o intuito de fazer o robô voltar ao normal, porém, desta vez, com as mudanças que tínhamos em mente, como maior imprevisibilidade e uma melhor performance em batalha.</p>

### 4.5 Corzinha

<p align="justify"> &emsp; Esta branch foi criada com o intuito inicial de fazer o robô piscar as cores do arco-íris a todo momento, mas não conseguimos. Então, ela foi criada para apenas piscar as cores quando o robô atingisse a vitória.</p>

### 4.6 Getting-info

<p align="justify"> &emsp; Esta branch foi criada com o objetivo de coletar informações da batalha, como posição dos jogadores, dimensão da arena e distância entre eles.</p>

### 4.7 Melhorando-colisao

<p align="justify"> &emsp; Esta branch foi criada para podermos corrigir o erro quando o robô batia em outro robô adversário ou paredce, passando a identificar o mesmo e reagindo a ele, não ficando parado tentando seguir em frente travado no robô adversário.</p>


### 4.8 Movimentacao-pablo

<p align="justify"> &emsp; Esta branch foi iniciada para implementar o método de movimentação surf. No entanto, acabou tendo problemas durante o merge, pois fizemos um commit de um código enorme, o que gerou diversos problemas de mesclagem. Com muita dedicação e dor de cabeça, no final, ocorreu tudo bem.</p>

### 4.9 Movimentacao-antonio

<p align="justify"> &emsp; Esta branch foi criada delegando a função de movimentação para o Antonio, mas depois foi decidido que a função seria delegada para mim. Por isso, esta branch está vazia e sem informações adicionais.</p>

### 4.10 Movimentacao-travada

<p align="justify"> &emsp; Nesta branch, estávamos resolvendo problemas do robô quando ele não reconhecia o tamanho da arena. Quando isso acontecia, o robô ficava travado, impossibilitando qualquer ação contra os inimigos.</p>

### 4.11 New-classes

<p align="justify"> &emsp; Esta branch foi criada para definir alguns construtores e classes que acabaram sendo usados posteriormente.</p>

### 4.12 Ponto-futuro

<p align="justify"> &emsp; Esta branch foi criada com o intuito de melhorar nossa porcentagem de acertos. Nosso robô atirava apenas onde o robô inimigo estava, o que fazia errar diversos tiros contra robôs que se moviam bastante, deixando nossa batalha menos eficaz.</p>

### 4.13 Sensor-erik

<p align="justify"> &emsp; Esta branch foi criada para configurar o sensor, mas acabou sendo necessário implementá-lo junto com a movimentação surf, pois o surf dependia do sensor. Portanto, esta branch acabou ficando vazia.</p>

### 4.14 Switch

<p align="justify"> &emsp; Esta branch foi criada com o intuito de fazer com que o robô tivesse dois modos de batalha: um para quando estivesse sozinho contra outro robô e outro para quando estivesse com três ou mais robôs na arena. No entanto, foi abandonada pela dificuldade e falta de tempo que tínhamos.</p>

### 4.15 Tiro-bernardo

<p align="justify"> &emsp; Esta branch foi criada para delegar a função de tiro para o Bernardo, mas acabou sendo necessário implementar na parte de movimentação surf, pois o método também dependia do tiro.</p>

## 5. Resultados e aprendizados

<p align="justify"> &emsp; Trabalhar com o Robocode foi uma experiência desafiadora que nos trouxe ótimos resultados e aprendizados. Ao programarmos nosso robô, tivemos a chance de aplicar conceitos de programação de forma prática, como a organização do código e a reutilização de funções. Isso nos permitiu aprimorar nossa capacidade de estruturar o código de maneira mais eficiente, tornando-o mais claro e fácil de entender, especialmente quando lidamos com o comportamento complexo de um robô.</p>
<p align="justify"> &emsp; Além disso, fomos desafiados a implementar estratégias inteligentes para que o robô pudesse tomar decisões durante as batalhas, como antecipar os movimentos do oponente ou escolher a melhor hora de atacar. A experiência de ver essas estratégias em ação e ver o robô evoluir à medida que ajustamos os algoritmos foi muito enriquecedora. A cada batalha, aprendemos algo novo sobre pequenas mudanças no código, como a movimentação do robô ou a precisão dos disparos, poderiam impactar o desempenho.</p>
<p align="justify"> &emsp; O processo também nos mostrou como é importante analisar os resultados e testar diferentes abordagens para melhorar o desempenho do robô. A cada derrota ou vitória, surgiam novas ideias, e tivemos que revisar constantemente o código para otimizar a movimentação e a estratégia de combate. No fim, conseguimos perceber o quanto a prática e os testes são essenciais para que um sistema se torne mais eficiente e inteligente.</p>

## 6. Conclusão

<p align="justify"> &emsp; A experiência foi muito enriquecedora, proporcionando a todos os membros da equipe uma vivência de trabalho em equipe que facilmente se encontra no mercado de trabalho e em projetos pessoais. Outro ponto muito importante foi a evolução do grupo como um todo em relação à tecnologia Git e à plataforma GitHub, ferramentas amplamente utilizadas nos meios acadêmicos e profissionais.</p>
<p align="justify"> &emsp; Dentre os lados positivos da atividade realizada, destacou-se o aprimoramento da linguagem Java, em que o grupo desenvolveu um código que não apenas foi satisfatório para o grupo, mas também alcançou a primeira colocação no campeonato realizado entre as equipes.</p>
<p align="justify"> &emsp; As habilidades aprimoradas durante a realização da atividade terão grande importância durante e após a conclusão do curso de Análise e Desenvolvimento de Sistemas. Trabalho em equipe, Git, Linux e Java (conhecimento que também se aplica a outras linguagens de programação) são habilidades que acompanham os programadores e analistas de sistemas durante toda a sua jornada.</p>

<p align="center">
<img src="/Images/victory.jpeg" width="600" height="350">
</p>



## 7. Anexos

### 7.1 Wiki do RoboCode e códigos importantes

- <a href="https://robowiki.net/wiki/Main_Page" target="_blank">Wiki RoboCode</a>
<p display="inline-block">
<details>
<summary>Inicialização do Robô</summary>

```java
public class EhOTrem extends AdvancedRobot {
    @Override
    public void run() {
        setColors(Color.red, Color.blue, Color.green); // Configura as cores do robô
        while (true) {
            setAhead(100); // Move para frente
            turnGunRight(360); // Gira o canhão
            back(100); // Move para trás
            turnGunRight(360); // Gira o canhão novamente
        }
    }
}
```
</details>

<details>
<summary>Lógica de Mira</summary>

```java
public void onScannedRobot(ScannedRobotEvent e) {
    double absoluteBearing = getHeading() + e.getBearing();
    double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
    if (Math.abs(bearingFromGun) <= 3) {
        turnGunRight(bearingFromGun);
        if (getGunHeat() == 0) {
            fire(Math.min(3 - Math.abs(bearingFromGun), getEnergy() - 0.1));
        }
    } else {
        turnGunRight(bearingFromGun);
    }
}
```
</details>

<details>
<summary>Reação ao Ser Atingido</summary>

```java
public void onHitByBullet(HitByBulletEvent e) {
    setBack(50); // Move para trás ao ser atingido
    turnRight(90 - e.getBearing()); // Gira 90 graus em relação ao tiro recebido
}
```
</details>

<details>
<summary>Movimentação Evasiva</summary>

```java
public void onHitWall(HitWallEvent e) {
    double bearing = e.getBearing();
    if (bearing > -90 && bearing <= 90) {
        back(100); // Move para trás se bater na parede
    } else {
        ahead(100); // Move para frente se bater na parede
    }
    turnRight(90 - bearing); // Gira 90 graus em relação à parede
}
```
</details>

<details>
<summary>Corzinhas</summary>

```java
 public void onWin(WinEvent event) {
		
        for(int i = 0; i <8; i++){
        setBodyColor(coresArcoIris[indiceCor]);
        indiceCor = (indiceCor + 1) % coresArcoIris.length;
        doNothing();
        if(i == 7) i = 0;
        }
    }
```

</details>
</p>

<details>
<summary>Estratégia campeã<summary>

<img src="/Images/canetaAzul.jpg" width="350" height="600">

</details>

- [Movimentação Surf](https://www.youtube.com/watch?v=dQw4w9WgXcQ)
