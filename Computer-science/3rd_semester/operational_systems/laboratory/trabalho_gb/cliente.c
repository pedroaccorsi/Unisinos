#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <mqueue.h>
#include <sys/types.h>
#include <unistd.h>
#include <signal.h>

//FEITO POR JOAO E NICOLAS

//Nome da fila.
const char* NOME_FILA = "/filaJogoDaVelha";

//Estrutura de dados para a mensagem
typedef struct Jogada {
	char nome[40];
	int pid;
	int x;
	int y;
	int posicao;
	int startConection;
} TJogada;

//Declaração da fila
mqd_t queue;
TJogada m;


void openQueue() {
	//Obter descritor (mq_open+O_WRONLY+O_CREAT)
	queue = mq_open(NOME_FILA, O_WRONLY | O_CREAT, 0770, NULL);
	if (queue == (mqd_t)-1) {
		perror("mq_open");
		exit(2);
	}
}

void editMessage() {
	sleep(2);
	system("clear");
	printf("-------------------------------------------- \n");
	printf("%s, digite a posicao correspondente do tabuleiro \n",m.nome);
	printf("-------------------------------------------- \n");
	scanf("%d", &m.posicao); 
	
}

void sendMessage() {
	if (mq_send(queue, (const char*)&m, sizeof(TJogada), 29) != 0) {
		perror("send #29");
	}
}

void signal_handler() { 
    printf("Recebeu o sinal \n"); 
}

void sigint_handler() {
	printf("Encerrando\n");
	m.posicao = -1;
	m.pid = getpid();
	m.startConection = 10;
	openQueue();
	//Enviar (mq_send)
	sendMessage();
		
	//Liberar descritor (mq_close)
	mq_close(queue);
	exit(10);
}


void deal_with_signals() {
	signal(SIGKILL, signal_handler);
	signal(SIGQUIT, signal_handler);
	signal(SIGTERM, signal_handler);
	signal(SIGINT, sigint_handler);
}
int main(int argc, char* argv[]) {
	deal_with_signals();
	int pid = getpid();
	printf("Cliente, PID: %d \n\n", pid);

	//Declaração da mensagem
	printf("Digite o seu nome: \n");
	scanf("%s", m.nome);
	m.posicao = -1;
	m.startConection = 1;
	m.pid = pid;

	openQueue();

	//Enviar (mq_send)
	sendMessage();

	m.startConection = 0;
	sleep(1);
	//Texto a ser enviado na mensagem - entrada por linha de comando
	for (;;) {
		editMessage();
		openQueue();

		//Enviar (mq_send)
		sendMessage();
		
		//Liberar descritor (mq_close)
		mq_close(queue);
		printf("Mensagem enviada: Jogador %s, posição %d \n \n", m.nome, m.posicao);
	}

	printf("Mensagem enviada!\n");
	exit(EXIT_SUCCESS);
}
