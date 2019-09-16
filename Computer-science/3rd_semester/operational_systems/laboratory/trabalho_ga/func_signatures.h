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
void handle_sigint_sigchld();