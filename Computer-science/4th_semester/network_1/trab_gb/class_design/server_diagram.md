```puml

@startuml

!include tam_skins.iuml
!include class_config.iuml

local_class(Server){
    -ArrayList<Client_handler> Clients
    -Thread clientPool 
}


global_interface(Runnable){
    +run()
}
global_interface(Thread){
    +start()
}
local_class(ClientPool){
    -ArrayList<Client_handler> Clients
    +ask_if_ready()
    +start_listening_string()
    +wait_for_answer()
    +send_game()
    +start_listening_string()
    +wait_for_stop()
    +read_answers()
    +set_winner()
}

Server creates(Client_handler,right)

Runnable implements(Thread, right)  
ClientPool implements(Runnable, right)

ClientPool composes(Server)

local_class(Client_handler){
    -GameOfStop Game
    -String Name
    -String answers
    -Boolean stop
}

local_class(WinnerCalculator){
    -ArrayList<Player> players
    -ArrayList<String> all_categories
    -String winner
}

local_class(Player){
    -points
    -name
    -answers
}

local_class(GameOfStop){
    +getCattegories()
    +addAnswer()
}
ClientPool uses(WinnerCalculator)
Player composes(WinnerCalculator, down)
Client_handler composes(ClientPool, right)
Client_handler uses(GameOfStop)


@enduml

```






