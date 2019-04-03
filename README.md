paint - Projet Programmation Répartie

# Compilation

## Sur Windows 

Lancer le script `build.bat` dans le répertoire paintChatServer.

## Sur Linux

Exécuter la commande suivante dans un terminal : (dans le répertoire paintChatServer)
```
javac -cp src;src/paint;src/paint/;. src/paint/*.java -d out/production/paintChatServer/
```


# Utilisation 

Vous trouverez dans le dossier `paintChatServer/out/production/paintChatServer/` le fichier `configuration.ini`

Il est formé de la façon suivante :
```
server=localhost
port=8000
```
S'il s'agit de la configuration du serveur, il faut laisser localhost.
S'il s'agit de la configuration du client, il faut mettre l'IP du serveur distant.

Une fois la configuration faite :

## Sur Windows

Lancer le script `start-server.bat` pour lancer le serveur.
Lancer le script `start-client.bat` pour lancer un client.

## Sur Linux

Lancer le script `start-server.sh` pour lancer le serveur.
Lancer le script `start-client.sh` pour lancer un client.


## Comment ça marche ?

### Envoyer des messages 

Cliquez dans la boîte de texte où l'on peut écrire, écrire le message et appuyer sur la touche Entrée.

### Faire des dessins

Choisir une forme, choisir une couleur, faire un premier clic puis un second clic ailleurs.
Pour le texte, cliquer à l'endroit où le texte doit apparaître puis écrire le message dans la boîte de dialogue.


# Fonctionnalités

Lorsqu'un client se connecte, il demande au serveur l'historique des dessins.
Il est possible d'envoyer et de recevoir des messages. Il est possible de dessiner (carrés, cercles, flèches), de gommer (toutes les formes, sauf le texte), et d'annuler le dernier dessin effectué.
On peut changer la couleur de notre dessin et choisir si l'on colore le dessin que l'on a fait.


# Les points à améliorer

- Les flèches ne sont pas parfaites.
- On ne peut pas gommer le texte.
- (voulu) les messages ne sont pas synchronisés à la connexion d'un client.
