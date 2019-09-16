#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <pthread.h>
#include <string.h>
#include <signal.h>
#include <sys/signal.h>
#include <stdlib.h>
#include <sys/types.h>
 
//Definições de menu
enum menu{
    WEB_BROWSER         = 1,
    TEXT_EDITOR         = 2,
    TERMINAL            = 3,
    FINALIZAR_PROCESSO  = 4,
    QUIT                = 5
};

typedef struct estruturaProcesso {
    int name;
    pid_t pid;
    char codigoStatusExecucao[32];
} estruturaProcesso;

volatile estruturaProcesso processos[3]={};

#include "func_signatures.h"

int child;

int main(){

    define_processes();
    //define_sig_handler();

    signal(SIGTERM, sig_handler);
    signal(SIGINT , sig_handler);
    signal(SIGCHLD, sig_handler);

    while(1){
        executaOpcao(menuSelecao(), processos);
    }

}

void define_sig_handler(){
    struct sigaction action;
	memset(&action, 0, sizeof(struct sigaction));
	action.sa_handler = sig_handler;
	sigaction(SIGTERM, &action, NULL);
}

void sig_handler(int signal){

    switch (signal) {

        case SIGCHLD:
            child += 1;
             break;
        
        case SIGTERM:
            if (getpid() == 0){
                 exit(15);
            }
                
            break;

        case SIGINT:
             break;
    }

}

void define_processes(){

    strcpy(processos[0].codigoStatusExecucao, "not_running");
    processos[0].name = WEB_BROWSER;

    strcpy(processos[1].codigoStatusExecucao, "not_running");
    processos[1].name = TEXT_EDITOR;

    strcpy(processos[2].codigoStatusExecucao, "not_running");
    processos[2].name = TERMINAL;  

}

int menuSelecao(){
    
    int opcao = 0;

    system("clear"); 

    printf(" \n<<<< Applications Menu >>>\n");
    printf("\t1) Web Browser             (%s, pid = %d)\n", processos[0].codigoStatusExecucao, processos[0].pid);
    printf("\t2) Text Editor             (%s, pid = %d)\n", processos[1].codigoStatusExecucao, processos[1].pid);
    printf("\t3) Terminal                (%s, pid = %d)\n", processos[2].codigoStatusExecucao, processos[2].pid);
    printf("\t4) Finalizar processo\n");
    printf("\t5) Quit\n");
    printf(" Opção: ");
    scanf("%d", &opcao);
    return opcao;
}

void executaOpcao(int opcao, estruturaProcesso processo[]){
    
    if (opcao == WEB_BROWSER || opcao == TEXT_EDITOR || opcao == TERMINAL){
        executaOpcaoProcessoFilho(opcao);
    } else {
        executaOpcaoProcessoPai(opcao);
    }     

}

void executaOpcaoProcessoFilho(int opcao){
    switch (opcao) {
        case WEB_BROWSER:
            webBrowser();
        break;
        case TEXT_EDITOR:
            textEditor();
        break;
        case TERMINAL:
            terminal();
        break;
    }
}

void webBrowser(){

    char url[200];
    int status;
    int new_pid;
    
    printf("Digite a URL que deseja buscar: "); 
    scanf("%s", &url);

    new_pid = fork();     

    switch (new_pid){

        case -1:
            strcpy(processos[0].codigoStatusExecucao, "failed");
            break;
        
        case 0:
            
            execlp("firefox","firefox --new-window", url, NULL);     
            exit(13);
            break;

        default:
            strcpy(processos[0].codigoStatusExecucao, "running");
            processos[0].name = WEB_BROWSER;
            processos[0].pid = new_pid;
            break;

    } 
    
}

void textEditor(){

    int new_pid = fork();     

    switch (new_pid){

        case -1:
            strcpy(processos[1].codigoStatusExecucao, "failed");
            break;
        
        case 0:

            execlp("gedit","gedit --new-window", NULL); 
            break;

        default:
            strcpy(processos[1].codigoStatusExecucao, "running");
            processos[1].name = TEXT_EDITOR;
            processos[1].pid  = new_pid;
            break;

    } 

}

void terminal(){

    int new_pid = fork();     

    switch (new_pid){

        case -1:
            strcpy(processos[2].codigoStatusExecucao, "failed");
            printf("deu ruim");
            break;
        
        case 0:
            execl("gnome-terminal", "gnome-terminal", "xs", NULL);
            break;

        default:
            strcpy(processos[2].codigoStatusExecucao, "running");
            processos[2].name = TERMINAL;
            processos[2].pid  = new_pid;
            break;
    } 

}

void executaOpcaoProcessoPai(int opcao){
    switch (opcao) {
        case FINALIZAR_PROCESSO:
            finalizarProcesso();
        break;
        case QUIT:
            quit();
        break;
    }
}
 

void finalizarProcesso(){

    int lv_process_to_be_killed =0;
    
    printf("Digite o número do processo a ser terminado: "); 
    scanf("%d", &lv_process_to_be_killed);

    switch (lv_process_to_be_killed)
    {
    case WEB_BROWSER:
        
        strcpy(processos[0].codigoStatusExecucao, "aborted");
        kill(processos[0].pid, SIGTERM);
        processos[0].pid = 0;
        break;

    case TEXT_EDITOR:
        
        strcpy(processos[1].codigoStatusExecucao, "aborted");
        kill(processos[1].pid, SIGTERM);
        processos[1].pid = 0;
        break;

    case TERMINAL:
        
        strcpy(processos[2].codigoStatusExecucao, "aborted");
        kill(processos[2].pid, SIGTERM);
        processos[2].pid = 0;
        break;

    }
}

void quit(){ exit(0);}