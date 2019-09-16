#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <pthread.h>
#include <string.h>

//Definições de menu
#define WEB_BROWSER         1
#define TEXT_EDITOR         2
#define TERMINAL            3
#define FINALIZAR_PROCESSO  4
#define QUIT                5

enum statusExec{
    not_running,
    running,
    finished,
    failed,
    aborted
};

typedef struct estruturaProcesso {
    int name;
    pid_t pid;
    char codigoStatusExecucao[32];
} estruturaProcesso;

estruturaProcesso processos[3]={};

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
void registraInformacaoProcessoFilho();
void finalizarProcesso();
void quit();
void define_processes();

int main(){

    define_processes();

    while(1){
        executaOpcao(menuSelecao(), processos);
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
    printf(" <<<< Applications Menu >>>\n");
    printf("\t1) Web Browser             (%s, pid = %d)\n", processos[0].codigoStatusExecucao, processos[0].pid);
    printf("\t2) Text Editor             (%s, pid = %d)\n", processos[1].codigoStatusExecucao, processos[1].pid);
    printf("\t3) Terminal                (%s, pid = %d)\n", processos[2].codigoStatusExecucao, processos[2].pid);
    printf("\t4) Finalizar processo");
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
    
    registraInformacaoProcessoFilho();
        //colocar aqui a parte que ira registrar o pid e o status do processo que ocorreu no filho
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
    
    printf("Digite a URL que deseja buscar: "); 
    scanf("%s", &url);

    wait(0.1);

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
            strcpy(processos[0].codigoStatusExecucao, "failed");
            break;

    } 
    
}

void textEditor(){
    printf("TE");
}

void terminal(){
    printf("T");
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

void registraInformacaoProcessoFilho(){
    printf("Registrar aqui");
}

void finalizarProcesso(){
    printf("FP");
}

void quit(){
    printf("Q");
}
