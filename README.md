# Progetto di Ingegneria del Software
##### Flavio Rizzoglio, Gianluca Ruberto, Leonardo Mussato
###### Anno 2020/2021

## 1. Funzioni implementate
Sono state implementate le seguenti funzionalità:
![alt text](https://github.com/mightyflavieee/ing-sw-2021-rizzoglio-ruberto-mussato/blob/master/deliverables/readme_images/implemented_functions.PNG)

### Funzionalità aggiuntive
Le funzionalità aggiuntive scelte e implementate sono:
1. Partite multiple;
2. Persistenza;
3. Resilienza alle disconnessioni.

## 2. Istruzioni per l'esecuzione del Client
### CLI
Per startare la CLI, aprire il terminale e scrivere:
`java -jar MastersOfRenaissance.jar cli`

Una volta runnato il comando, selezionare il proprio nickname. A questo punto indicare se si vuole creare o joinare un game, tramite uno di questi due comandi:
1. Creazione: `create`;
2. Join: `join`.

Se si è selezionata la prima opzione, selezionare il numero di giocatori con i quali si vuole giocare. Se si è selezionata la seconda opzione, inserire l'id del game a cui si vuole partecipare. 
Attendere gli altri giocatori (se presenti) e iniziare a giocare!

### GUI
Ci sono due modi per inizializzare la GUI:
1. Tramite CLI: immettere il seguente comando `java -jar MastersOfRenaissance.jar gui`;
2. Tramite file .jar: cliccare sul file!

## 3. Server
Il file .jar del server è hostato su una macchina Linux (EC2 di Amazon AWS) ed è permanentemente attivo. Chiunque in possesso del .jar (client-side) può automaticamente connettersi al server.
Il file .jar del server è stato comunque incluso nella cartella `/deliverables`. 

### Coverage
![alt text](https://github.com/mightyflavieee/ing-sw-2021-rizzoglio-ruberto-mussato/blob/master/deliverables/coverage.PNG)
