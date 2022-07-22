# SistemaVotoScrutinamentoElettronico
Si progetti un sistema di voto e scrutinio elettronico. Il sistema deve essere generico e prevedere
diverse modalità di voto e di definizione del vincitore.
Per quanto riguarda le modalità di voto, quelle da supportare sono: 
• voto ordinale: all’elettore è richiesto di ordinare i candidati (o gruppi/partiti) presenti nella 
scheda in base alle proprie preferenze.
• voto categorico: l’elettore inserisce una preferenza per un candidato (o gruppo/partito).
• voto categorico con preferenze: l’elettore inserisce una preferenza per un gruppo/partito 
e ha la possibilità di indicare una o più preferenze tra i candidati del gruppo/partito 
selezionato (niente voto disgiunto).
• referendum: consiste in una domanda fatta all’elettorato con la quale si chiede se si sia 
favorevoli o contrari a un determinato quesito.
Per quanto riguarda i possibili modi per definire il vincitore della procedura di voto, l’insieme 
minimo da considerare comprende: 
• maggioranza: il vincitore è il candidato che ha ottenuto il maggior numero di voti.
• maggioranza assoluta: il vincitore è il candidato che ha ottenuto la maggioranza assoluta 
dei voti, cioè il 50% + 1 dei voti espressi.
• referendum senza quorum: si procede al conteggio dei voti indipendentemente se abbia 
partecipato o meno alla consultazione la maggioranza degli aventi diritto al voto.
• referendum con quorum: si procede al conteggio dei voti espressi solo nel caso in cui abbia 
partecipato alla consultazione la maggioranza degli aventi diritto al voto.
I voti devono poter venire espressi di persona in un seggio elettorale dopo che si è svolta la fase di 
riconoscimento dell’identità del votante e della verifica del suo diritto al voto (che si assume 
avvengano manualmente), oppure a distanza (in questo caso, la fase di identificazione e verifica 
del diritto di voto vengono effettuati dal sistema da implementare).
Vanno considerate due tipologie di utente: l’elettore e l’impiegato/gestore del sistema che deve 
poter configurare una sessione di voto, specificando le modalità di voto e di calcolo del vincitore, 
inserire le liste dei candidati, far partire la fase di scrutinio e visualizzare l’esito del voto.
Alcuni dei requisiti essenziali che il sistema deve prevedere sono:
• il voto espresso deve rimanere segreto e non deve essere riconducibile all’elettore;
• ogni elettore può votare una volta sola;
• per ogni scheda è ammesso un solo voto valido o l’esercizio della facoltà di astenersi dalla 
scelta (scheda bianca);
• il conteggio dei voti elettronici si può attivare solo dopo la chiusura di tutte le operazioni di 
voto.
Si deve prevedere anche un sistema di auditing per la verifica del corretto funzionamento 
dell’applicazione, per esempio basato su un sistema di log (semplificato).
Si noti che la descrizione del sistema da implementare è volutamente incompleta. Dovete 
specificare meglio i requisiti durante la fase di analisi e specifica dei requisiti del vostro progetto (e 
darne documentazione)
