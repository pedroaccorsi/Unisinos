#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <pthread.h>
#include <string.h>
#include <signal.h>
#include <sys/signal.h>
#include <stdlib.h>

//Definições de menu
#define WEB_BROWSER         1
#define TEXT_EDITOR         2
#define TERMINAL            3
#define FINALIZAR_PROCESSO  4
#define QUIT                5

typedef struct estruturaProcesso {
    int name;
    pid_t pid;
    char codigoStatusExecucao[32];
} estruturaProcesso;

estruturaProcesso processos[3]={};

void signal_handler();

//Funções do menu de seleção
int menuSelecao();
void executaOpcao(int opcao, estruturaProcesso processo[]);
void executaOpcaoProcessoFilho(int opcao);
void executaOpcaoProcessoPai(int opcao);

//Funcionalidades dos processos filhos
void webBrowser();
void textEditor();
void terminal();

//Funcionalidades do processo pai
void finalizarProcesso();
void quit();
void define_processes();
void define_sig_handler();
void sig_handler(int signal);

int main(){

    define_processes();
    define_sig_handler();

    while(1){
        executaOpcao(menuSelecao(), processos);
    }

}

void define_sig_handler(){
    struct sigaction sa;
    sa.sa_handler = &sig_handler;
    sa.sa_flags = SA_RESTART;
    sigfillset(&sa.sa_mask);
}

void sig_handler(int signal){
    exit(15);
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
    printf(" <<<< Applications Menu >>>\n");
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
    
    printf("Digite a URL que deseja buscar: "); 
    scanf("%s", &url);

    strcpy(processos[0].codigoStatusExecucao, "running");
    processos[0].name = WEB_BROWSER;
    processos[0].pid  = fork();     

    switch (processos[0].pid){

        case -1:
            strcpy(processos[0].codigoStatusExecucao, "failed");
            printf("deu ruim");
            break;
        
        case 0:
            
            execlp("firefox","firefox --new-window", url, NULL); 
            break;
    } 
    
}

void textEditor(){

    strcpy(processos[1].codigoStatusExecucao, "running");
    processos[1].name = TEXT_EDITOR;
    processos[1].pid  = fork();     

    switch (processos[1].pid){

        case -1:
            strcpy(processos[1].codigoStatusExecucao, "failed");
            printf("deu ruim");
            break;
        
        case 0:
            
            execlp("gedit","gedit --new-window", NULL); 
            break;
    } 

}

void terminal(){

    strcpy(processos[2].codigoStatusExecucao, "running");
    processos[2].name = TERMINAL;
    processos[2].pid  = fork();     

    switch (processos[2].pid){

        case -1:
            strcpy(processos[2].codigoStatusExecucao, "failed");
            printf("deu ruim");
            break;
        
        case 0:
            execl("/usr/bin/bash", "bash", "-c", "xs", NULL);
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
        processos[0].pid = 0;
        kill(processos[0].pid, SIGTERM);
        break;

    case TEXT_EDITOR:
        
        strcpy(processos[1].codigoStatusExecucao, "aborted");
        processos[1].pid = 0;
        kill(processos[1].pid, SIGTERM);
        break;

    case TERMINAL:
        
        strcpy(processos[2].codigoStatusExecucao, "aborted");
        processos[2].pid = 0;
        kill(processos[2].pid, SIGTERM);
        break;

    }
}

void quit(){ exit(0);}