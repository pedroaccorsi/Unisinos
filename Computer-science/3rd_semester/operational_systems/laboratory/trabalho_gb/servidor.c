#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <string.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <mqueue.h>
#include <time.h>
#include <unistd.h>
#include <sys/types.h>
#include <stdbool.h>

//FEITO POR NICOLAS E JOAO

//nome dos jogadores
char player1[40];
char player2[40];

//pid dos jogadores
int pidPlayer1;
int pidPlayer2;

//valida qual jogador tem que estar jogando
int playerTurn = 1;

//guarda o numero de posicoes ocupadas
int plays = 0;

//Nome da fila
const char* NOME_FILA = "/filaJogoDaVelha";

//status
const char* EMPATE = "2";
const char* JOGADA_VENCEDORA = "1";
const char* JOGADA_VALIDA = "0";
const char* JOGADA_INVALIDA = "-1";
bool primeira_jogada = true;

//matriz das jogadas
char matriz[3][3] = {{' ', ' ', ' '}, { ' ', ' ', ' ' }, { ' ', ' ', ' ' }};

//Estrutura de dados para a mensagem
typedef struct Jogada {
	char nome[40];
	int pid;
	int x;
	int y;
	int posicao;
	int startConection;
} TJogada;

//numero total de players que estao contectados
int totalDePlayersConectados = 0;
int fd;	
int pid;	

//Declarações das funções
ssize_t get_msg_buffer_size(mqd_t queue);
void print_msg(TJogada* m);
void validaJogada(TJogada* m);
void executaJogada(TJogada* m, char jogada);
void print_matriz();
void startPlayerConnection(TJogada* m);
void requestAnotherName(TJogada* m);
void deal_with_signals();
void createLog(TJogada* m, const char* status);
void signal_handler();
void encerra_jogo(const char* jogada, TJogada* m);
void checa_jogada_vencedora(char jogada, TJogada* m);
void set_collor(char* collor);

int main(void) {

	pid = getpid();
	printf("Servidor, PID: %d \n\n", pid);

	deal_with_signals();
	fd = open("log.txt", O_CREAT | O_RDWR, 0770);
	truncate("log.txt", 0);
	//Declaração da fila
	mqd_t queue;

	//Declaração do buffer
	char* buffer = NULL;

	//Declaração do tamanho do buffer
	ssize_t tam_buffer;
	ssize_t nbytes;

	//Obter descritor (mq_open+O_RDONLY)
	queue = mq_open(NOME_FILA, O_RDONLY | O_CREAT);
	if (queue == (mqd_t) -1) {
		perror("mq_open");
		exit(2);
	}

	//Alocar buffer para receber msg
	tam_buffer = get_msg_buffer_size(queue);
	buffer = calloc(tam_buffer, 1);
	for(;;){
        
		//Receber (mq_recv)
		nbytes = mq_receive(queue, buffer, tam_buffer, NULL);
		if (nbytes == -1) {
			perror("receive");
			exit(4);
		}

		if(((TJogada*) buffer)->startConection == 10) {
			printf("Jogador(a) %s saiu da partida, encerrando...\n", ((TJogada*) buffer)->nome);
			kill(pidPlayer1, SIGKILL);
			kill(pidPlayer2, SIGKILL);
			exit(10);
		}

		//Print da mensagem recebida
		if(totalDePlayersConectados == 2){
			validaJogada((TJogada*) buffer);
		}
			
		if(((TJogada*) buffer)->startConection == 1) 
			startPlayerConnection((TJogada*) buffer);

	}

}


void validaJogada(TJogada* m) {
 
	char nome[40];

	strcpy(nome, m->nome);

	if (playerTurn == 1 && strcmp(nome, player1) == 0) {
		executaJogada(m, 'X');
		return;
	}
	if(playerTurn == 2 && strcmp(nome, player2) == 0) {
		executaJogada(m, 'O');
		return;	
	}
	createLog(m, JOGADA_INVALIDA);
	set_collor("yellow");
	printf("WARNING! Jogador %s jogou, sendo que não era sua vez. \nJogada ignorada\n", nome);
	set_collor("white");
}

void executaJogada(TJogada* m, char jogada) {

	char nome[40];
	int x, y;

	strcpy(nome, m->nome);

	switch(m->posicao){
		case 1: x=0; y=0; break;
		case 2: x=0; y=1; break;
		case 3: x=0; y=2; break;
		case 4: x=1; y=0; break;
		case 5: x=1; y=1; break;
		case 6: x=1; y=2; break;
		case 7: x=2; y=0; break;
		case 8: x=2; y=1; break;
		case 9: x=2; y=2; break;
		default: x=y=-1;
	}

	if((x >= 0 && x <= 2) && (y >= 0 && y <= 2)) {
		if(matriz[x][y] == ' ') {
			matriz[x][y] = jogada;
			print_matriz();
			playerTurn = playerTurn == 1 ? 2 : 1;
			plays++;
			checa_jogada_vencedora(jogada, m);
			createLog(m, JOGADA_VALIDA);
			return;
		}
		createLog(m, JOGADA_INVALIDA);
		set_collor("yellow");
		printf("WARNING! Posição já ocupada, tente outra \n");
		set_collor("white");
		return;
	} else {
		createLog(m, JOGADA_INVALIDA);
		set_collor("yellow");
		printf("WARNING! Posição inválida, jogue novamente\n");
		set_collor("white");
		return;
	}	
}

void encerra_jogo(const char* jogada, TJogada* m) {
	if (jogada == JOGADA_VENCEDORA) {
		createLog(m, JOGADA_VENCEDORA);
		set_collor("green");
		printf("SUCCESS! Jogador(a) %s ganhou!\n", m->nome);
		set_collor("white");
	} else if (jogada == EMPATE) {
		createLog(m, EMPATE);
		set_collor("green");
		printf("SUCCESS! A partida foi empatada!\n");
	} else {
		printf("Acabou o jogo \n");
	}
	sleep(4);
	close(fd);
	kill(pidPlayer1, SIGKILL);
	kill(pidPlayer2, SIGKILL);
	printf("O servidor está sendo encerrado \n");

	kill(pid, SIGKILL);
}

void checa_jogada_vencedora(char jogada, TJogada* m) {
	if(matriz[0][0] == jogada && matriz[1][1] == jogada && matriz[2][2] == jogada){
		encerra_jogo(JOGADA_VENCEDORA, m);
	} else if (matriz[0][2] == jogada && matriz[1][1] == jogada && matriz[2][0] == jogada) {
		encerra_jogo(JOGADA_VENCEDORA, m);
	} else if (matriz[0][0] == jogada && matriz[0][1] == jogada && matriz[0][2] == jogada) {
		encerra_jogo(JOGADA_VENCEDORA, m);
	} else if (matriz[0][0] == jogada && matriz[1][0] == jogada && matriz[2][0] == jogada) {
		encerra_jogo(JOGADA_VENCEDORA, m);
	} else if (matriz[2][0] == jogada && matriz[2][1] == jogada && matriz[2][2] == jogada) {
		encerra_jogo(JOGADA_VENCEDORA, m);
	} else if (matriz[0][2] == jogada && matriz[1][2] == jogada && matriz[2][2] == jogada) {
		encerra_jogo(JOGADA_VENCEDORA, m);
	} else if (matriz[0][1] == jogada && matriz[1][1] == jogada && matriz[2][1] == jogada) {
		encerra_jogo(JOGADA_VENCEDORA, m);
	} else if (matriz[1][0] == jogada && matriz[1][1] == jogada && matriz[1][2] == jogada) {
		encerra_jogo(JOGADA_VENCEDORA, m);
	} else if (plays == 9) {
		encerra_jogo(EMPATE, m);
	}
}

void print_msg(TJogada* m) {
	printf("name=%s, posX=%d, posY=%d, pid=%d \n", m->nome, m->x, m->y, m->pid);
}

void startPlayerConnection(TJogada* m) {
	if (totalDePlayersConectados == 1) {
		if (strcmp(m->nome, player1) == 0) {
			requestAnotherName(m);
			return;
		}
	}
	if(totalDePlayersConectados == 2) {
		set_collor("yellow");
		printf("WARNING! Terceiro jogador (%s) expulso do servidor \n", m->nome);
		set_collor("white");
		kill(m->pid, SIGKILL);
		return;
	}
	if (totalDePlayersConectados == 0) {
		strcpy(player1, m->nome);
		pidPlayer1 = m->pid;
	} else {
		strcpy(player2, m->nome);
		pidPlayer2 = m->pid;
	}
	printf("-------------------------------------------\n");
	printf("Player: %s, se conectou \n", m->nome);
	totalDePlayersConectados++;
	printf("Total de player conectados: %d \n", totalDePlayersConectados);
	printf("-------------------------------------------\n");
	if (totalDePlayersConectados == 2) {
		system("clear");
		print_matriz();
	}
}

void requestAnotherName(TJogada* m) {
	set_collor("red");
	printf("ERRO! Username já utilizado \n");
	set_collor("white");
	kill(m->pid, SIGKILL);
}

ssize_t get_msg_buffer_size(mqd_t queue) {
	struct mq_attr attr;

	/*Determina max. msg size; allocate buffer to receive msg */
	if (mq_getattr(queue, &attr) != -1) {
		printf("max msg size: %ld\n", attr.mq_msgsize);
		return attr.mq_msgsize;
	}

	perror("aloca_msg_buffer");
	for(;;){}
}

void print_matriz(){
	system("clear");
	if(primeira_jogada){
		primeira_jogada = false;
		printf("Vez do jogador: %s", playerTurn == 1 ? player1 : player2);
	}else{
		printf("Vez do jogador: %s", playerTurn == 1 ? player2 : player1);
	}
	printf("\n\n Tabuleiro\n\n");
	printf("     |     |\n");
	printf("  %c  |  %c  |  %c\n", matriz[0][0], matriz[0][1], matriz[0][2]);
	set_collor("gray"); printf("1"); set_collor("white"); printf("    |"); set_collor("gray"); printf("2"); set_collor("white"); printf("    |"); set_collor("gray"); printf("3\n");
	set_collor("white");
	printf("-----+-----+-----\n");
	printf("     |     |\n");
	printf("  %c  |  %c  |  %c\n", matriz[1][0], matriz[1][1], matriz[1][2]);
	set_collor("gray"); printf("4"); set_collor("white"); printf("    |"); set_collor("gray"); printf("5"); set_collor("white"); printf("    |"); set_collor("gray"); printf("6\n");
	set_collor("white");
	printf("-----+-----+-----\n");
	printf("     |     |\n");
	printf("  %c  |  %c  |  %c\n", matriz[2][0], matriz[2][1], matriz[2][2]);
	set_collor("gray"); printf("7"); set_collor("white"); printf("    |"); set_collor("gray"); printf("8"); set_collor("white"); printf("    |"); set_collor("gray"); printf("9\n");
	printf("\n\n");
	set_collor("white");
}

void set_collor(char* collor){
	if      (strcmp(collor, "gray")  ==0){
		printf("\033[0;90m");
	}else if(strcmp(collor,"white")  ==0){
		printf("\033[0;0m");
	}else if(strcmp(collor,"red")    ==0){
		printf("\033[0;31m");
	}else if(strcmp(collor,"green")  ==0){
		printf("\033[0;32m");
	}else if(strcmp(collor,"yellow") ==0){
		printf("\033[0;33m");
	}
}

void sigint_handler() {
	TJogada* dummy;
	set_collor("red");
	printf("ERROR! CTRL + C\n");
	encerra_jogo("",dummy);
}

void deal_with_signals() {
	signal(SIGKILL, signal_handler);
	signal(SIGQUIT, signal_handler);
	signal(SIGTERM, signal_handler);
	signal(SIGINT, sigint_handler);
}

void signal_handler() 
{ 
    printf("Recebeu o sinal \n"); 
}

void createLog(TJogada* m, const char* status) {
	char log[500];
	int x, y;

	time_t currentTime;
	struct tm ts;
	char stringTime[150];

	switch(m->posicao){
		case 1: x=0; y=0; break;
		case 2: x=0; y=1; break;
		case 3: x=0; y=2; break;
		case 4: x=1; y=0; break;
		case 5: x=1; y=1; break;
		case 6: x=1; y=2; break;
		case 7: x=2; y=0; break;
		case 8: x=2; y=1; break;
		case 9: x=2; y=2; break;
		default: x=y=-1;
	}

	// Obtem o tempo corrente
	currentTime = time(NULL);

	// Formata e imprime o tempo, "ddd yyyy-mm-dd hh:mm:ss zzz"
	ts = *localtime(&currentTime);
	strftime(stringTime, sizeof(stringTime), "%a %Y-%m-%d %H:%M:%S %Z", &ts);

	char lance[20];
	sprintf(lance, "(x: %d, y: %d)", x, y);

	strcat(log, stringTime);
	strcat(log, ";");
	strcat(log, m->nome);
	strcat(log, ";");
	strcat(log, lance);
	strcat(log, ";");
	strcat(log, status);
	strcat(log, "\n\n");
	write(fd, log, strlen(log));

	memset(log ,0, sizeof(log) );

}


