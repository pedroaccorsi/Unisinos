```puml

@startuml

!include tam_skins.iuml
!include class_config.iuml

local_class(Client)
local_class(GameOfStop){
    +getCattegories()
    +addAnswer()
}
Client uses(GameOfStop)




@enduml

```






