## Multiprogramação
As tarefas são escalonadas para dividirem o processador, onde cada tarefa (processo) executa por um certo tempo (determinado pelo algoritmo escalonador). Esse tempo é rápido o suficiente para que se tenha a impressão de que os processos estão executando em paralelo, mas isso não necessariamente é verdade.  

## Processo
Processos podem ser entendidos como uma "abstração" de um programa em execução, organizando informãções referente à execução como um todo, por exemplo: código, dados, pilha, heap, etc.
Durante sua "vida", um processo pode admitir diferentes estados, que representam seu ponto atual de execução, por exemplo: executando, pronto para executar, bloqueado, aguardando, etc.
Um ciclo de vida pode ser, por exemplo, o seguinte
criado -> apto -> em execução -> bloqueado (aguardando I/O) -> apto (I/O finalizada) -> em execução

## Modo usuário e supervisor:
usuário: não pode fazer tudo
supervisor: pode fazer tudo
a transição é feita assim:
usuário -> supervisor: interrupção
supervisor -> usuário: instrução 

## PCB Program Control Block:
Estrutura de dados que guarda as informações necessárias sobre um determinado processo. Essa estrutura deve ser mantida e armazenada pelo sistema opercional, para que, dentre outras coisas, consiga escalonar e manter corretamente os dados de cada processo durante trocas de contexto. O PCB pode armazenar dados como, por exemplo, o ID do processo, espaço de endereçamento, status, etc.

## Race condition:
Quando mais de um processo concorre pelos mesmos recursos, sendo que o resultado final depende da ordem em que os processos foram executados.

## seção crítica:
Seção do código que manipula algum recurso compartilhado


## exec:
substitui o código atual por outro (informado por argumento), ou seja, pode-se entender como um processo totalmente novo, que não mais divide recursos com sei pai

## fork:
divide recursos com o pai, começa a executar a partir da linha imediatamente seguinte ao fork(), sendo uma "cópia" do pai, mas que se encontra numa região diferente de memória. 

## IPC:
técnicas para trocar informação entre diferentes processos. tem o PIPE, fila de mensagens, memória compartilhada, etc. para usar o PIPE em um programa chamado por EXEC, a solução é enviar o file descriptor como parâmetro para o novo programa.


## WAIT:
processo pai "congela" sua execução, para aguardar que seus filhos terminem. quando eles terminarem, a execução do pai é retomada.

## ZOMBIE:
processo filho que termina após o seu pai, ou seja, fica "órfão". Nesse contexto, é adotado pelo processo Init, que passa a ser seu "novo pai".

## Modelo de threads
###	N:1
N threads de usuário para 1 thread de kernel. Nesse contexto, todo a gerência das threads se dá a nível de usuário, no qual a biblioteca sendo utilizada é responsável por escalonar as threads. O problema disso é que se por alguma razão alguma thread for bloqueada, tipo pra fazer I/O, o processo inteiro fica "on hold" sem poder avançar


###	1:1 
uma thread de usuário para 1 thread de kernel. Aqui, as threads de usuário podem efetivamente acontecer em paralelo (caso hajam múltiplos processadores), porém requer sempre uma de kernel para cada de usuário, e isso pode sobrecarregar o sistema.

###	N:N
N threads de usuário para N threads de Kernel. Aqui também consegue obter-se o paralelismo (caso hajam múltiplos processadores), porém fica bem difícil de conseguir parametrizar quantas threads quantas threads de kernel para cada thread de usuário.


## Threads:
São mais rápidas em termos de criação e destruição do que um processo "normal". Além disso, a troca de contexto entre threads também é mais rápida do que a entre processos "normais". Além disso, por dividirem o mesmo espaço de memória, podem utilizar recursos de memória compartilhada para se comunicarem.

## Threads de usuário vs kernel:
### usuário
são manejadas (criar, escalonar, destruir...) pelo próprio programador, normalmente através de bibliotecas. uma vantagem é que pode ocorrer de ser mais rápido, devido ao fato de que a troca de contextos entre threads é bem mais rápida a nível de usuário do que a nível de kernel, pois não precisa fazer "nada" a nível de núcleo de processamento, reduzing o overhead. uma desvantagem é que não permite o paralelismo real em sistemas multiprocessados, pois na visão do OS, essas múltiplas threads são como se fossem um único processo. além disso, caso uma das threads faça uma chamada bloqueadora (I/O, por exemplo), todo mundo é bloqueado e a execução não avança.

### kernel
são manejadas diretamente pelo OS, fazendo uma troca de contexto completa a cada thread nova que o próprio OS escalona. Uma vantagem é que permite o paralelismo real em sistemas multiprocessados, já que cada thread nesse caso se comporta como um processo diferente. entretando, por precisar de uma troca de contexto a nível de núcleo, ocasiona um overhead significativo em comparação às threads de usuário.

## Seção crítica
regra 1 -> exclusão mútua: dois ou mais processos não podem acessar simultaneamente a seção crítica
regra 2 -> progressão: nenhum processo fora da seção crítica pode bloquear outro processo
regra 3 -> espera limitada: nenhum processo deve esperar inifinitamente até poder acessar a seção crítica
regra 4 -> processamento: não se deve fazer consideraçoes sobre o número de processadores ou suas velocidades

## Semáforo
variáveis compartilhadas entre processos para controle de acceso a seção crítica. São basicamente um contador que permite N processos entrarem simultaneamente na seção crítica. cada processo que entra, decresce 1 do contador, cada um que sai, acrescenta um ao contador. caso algum processo tente acessar, porém abaixe o contador para -1, ele é automaticamente suspenso e fica aguardando o contador voltar para 1, para que possa acessar a seção crítica. 

## Mutex
aqui, apenas um processo pode "tomar a ownsership" da mutex e acessar a região crítica. dessa forma, um hipotético segundo processo precisa ficar aguardando a mutex ser liberada para poder tomá-la. Esse "esperando" é conhecido como busy waiting, pois o processo está usando processador para nada. Nesses casos, a recomendação é por o processo para dormir e acordá-lo assim que a mutex for liberada.

## Deadlock
4 condições
	-  exclusão mútua
	- segura/espera
	- recurso não preemptivo
	- espera circular
pra resolver os deadlocks, uma das 4 condições acima precisa ser falsa. na vida real, a maioria dos OS simplesmente ignora esses casos, torcendo pra que nunca ocorram. de maneira ideal, poderiamos resolver isso permitindo que os processos só aloquem recursos quando todos que forem preciso estiverem liberados, sem manter nada que não está usando ainda. por exemplo no jantar dos filósofos: ninguém pegaria um garfo e ficaria pra sempre aguardando o segundo, ou pega os dois ou não pega nenhum

## Escalonadores
Algoritmos para gerenciar os processos que vão ou não pra CPU.

- time-sharing -> cada processo executa por um tempo quantum. quando ele termina, o processo é preemptado e um novo entra em execução
- real-time -> o processo já define quanto tempo vai ter de processador e esse tempo precisa ser meticulosamente seguido, sem ser modificado. isso garante o funcionamento adequado de sistemas de altissima precisão, como de equipamentos médicos, por exemplo.
- Throughput -> processos/tempo
- Turnaround ->  final execução - chegada na fila
- Starvation -> um processo que nunca é executado. uma causa pode ser ele ter a prioridade baixa demais e nunca ser escalonado para execução.
- longo prazo -> seleciona processos aptos da memória secundária e trás pra principal
- médio prazo -> seleciona processos da memória principal e faz swap com a memória secundária
- curto prazo -> seleciona os aptos da memória principal e dá CPU pra eles

## modo kernel 
mais seguro porque diversas funções da CPU só podem ser utilizadas caso se esteja no modo kernel, limitando bastante no que instruções no modo usuário podem ou não fazer.

