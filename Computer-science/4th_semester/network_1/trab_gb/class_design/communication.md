```puml

@startuml

!include tam_skins.iuml
!include class_config.iuml

local_interface(Listener){
    +boolean listenString()
    +boolean waitForInputString()
    +boolean hasInputString()
    +boolean listenObject()
    +boolean waitForInputObject()
    +boolean hasInputObject()
}

local_class(ServerListener)
local_class(ClientListener)

ServerListener implements(Listener, up)
ClientListener implements(Listener, up)

local_interface(IO_handler){
    +String EOF
    +String read(String txt)
    +String read()
    +Object read_obj(String txt)
    +void write(String txt)
    +void write(Object obj)
}

local_class(IO_handler_client)
local_class(IO_handler_server)
local_class(IO_handler_user)

IO_handler_client implements(IO_handler, up)
IO_handler_server implements(IO_handler, up)
IO_handler_user   implements(IO_handler, up)

IO_handler composes(Listener, down)

@enduml

```






