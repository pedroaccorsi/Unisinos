#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

//Definições de menu
#define WEB_BROWSER         1
#define TEXT_EDITOR         2
#define TERMINAL            3
#define FINALIZAR_PROCESSO  4
#define QUIT                5

typedef struct estruturaProcesso {
    pid_t pid;
    int codigoStatusExecucao;
} estruturaProcesso;

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

int main(){
    estruturaProcesso processo[3];
    while(1){
        executaOpcao(menuSelecao(), processo);
    }
}

int menuSelecao(){
    int opcao = 0;

    printf(" <<<< Applications Menu >>>\n");
    printf("\t1) Web Browser\n");
    printf("\t2) Text Editor\n");
    printf("\t3) Terminal\n");
    printf("\t4) Finalizar processo\n");
    printf("\t5) Quit\n");
    printf(" Opção: ");
    scanf("%d", &opcao);
    return opcao;
}

void executaOpcao(int opcao, estruturaProcesso processo[]){
    if ((processo[opcao].pid=fork()) < 0) {
        perror("Erro no fork!");
    } else if (processo[opcao].pid == 0) {
        executaOpcaoProcessoFilho(opcao);
    } else {
        //Se é uma funcionalidade executada no processo pai
        if (opcao == FINALIZAR_PROCESSO || opcao == QUIT){
            executaOpcaoProcessoPai(opcao);
        } else {
            registraInformacaoProcessoFilho();
        //colocar aqui a parte que ira registrar o pid e o status do processo que ocorreu no filho
        }
    }
}

void executaOpcaoProcessoFilho(int opcao){
    switch (opcao) {
        case WEB_BROWSER
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
