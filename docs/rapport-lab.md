# Rapport de laboratoire - WealthOnboard

## Informations du projet

- **Nom de travail :** WealthOnboard
- **Depot :** `fintech-clients-onboarding-portal`
- **Objectif :** construire progressivement un portail fintech d'accueil client configurable par des modeles JSON.
- **Architecture cible :** monolithe modulaire avec Java, Spring Boot et Vaadin.

## Methode de travail

Chaque etape du projet suit le meme cycle :

1. definir un objectif limite et observable;
2. expliquer les concepts techniques concernes;
3. implementer un petit increment fonctionnel;
4. executer les tests ou verifications appropries;
5. corriger les erreurs avant de continuer;
6. documenter le resultat dans ce rapport;
7. creer un commit Git explicite.

## Jalon 1 - Fondation du moteur de formulaires

### Resultat attendu

- demarrage de l'application Spring Boot;
- affichage d'une vue Vaadin minimale;
- representation Java d'un modele de formulaire;
- presence d'un premier modele de formulaire JSON;
- deserialisation du JSON avec Jackson;
- test automatise confirmant la deserialisation.

### Hors perimetre

Ce jalon n'inclut pas encore la base de donnees, le rendu dynamique complet, la generation PDF ou les regles conditionnelles interactives.

## Etape 1 - Initialisation du projet

**Date :** 31 aout 2026

### Objectif

Preparer le dossier de travail, le suivi de versions local et l'acces au futur depot GitHub.

### Commandes executees

```powershell
git init
git branch -M main
git status
gh auth login -h github.com
gh auth status
```

### Environnement verifie

| Outil | Version ou etat |
|---|---|
| Java | OpenJDK Temurin 25.0.4.1 LTS |
| Maven | 3.9.15 |
| Git | 2.48.1.windows.1 |
| GitHub CLI | Authentifie avec le compte `dee276` |

Le poste utilise Java 25, mais le projet ciblera Java 21 afin de conserver une base largement compatible avec Spring Boot et Vaadin.

### Resultat

- le depot Git local est initialise;
- la branche principale s'appelle `main`;
- GitHub CLI est authentifie par HTTPS;
- le nom de depot distant `dee276/fintech-clients-onboarding-portal` est disponible.

### Statut

Termine et valide.

## Journal des commits

| Etape | Commit suggere | Statut |
|---|---|---|
| Initialisation et rapport de laboratoire | `docs: initialize project lab report` | Cree (`0d3550f`) |

## Analyse de la direction visuelle

**Date :** 31 aout 2026

### Sources analysees

Cinq maquettes generees avec Figma AI ont ete fournies : tableau de bord d'onboarding, catalogue de modeles, editeur de modele, liste de documents et version mobile.

### Direction retenue

- interface de travail sobre et dense, adaptee aux operations financieres;
- barre laterale sombre de 216 px sur grand ecran;
- contenu principal clair avec bordures discretes et rayon maximal de 7 px;
- Inter pour l'interface et DM Mono pour les identifiants, dates et donnees tabulaires;
- bleu primaire reserve aux actions et a la navigation active;
- statuts exprimes par un libelle, une couleur et, lorsque pertinent, une icone;
- tableaux compacts pour les vues de gestion;
- animations courtes uniquement pour signaler un changement d'etat ou de contexte.

### Composants Vaadin pressentis

| Besoin | Composant ou approche |
|---|---|
| Structure principale | `AppLayout`, `DrawerToggle`, `SideNav` |
| Cas d'onboarding et documents | `Grid` |
| Formulaires | composants de champ Vaadin dans des layouts responsifs |
| Sections et filtres | `Tabs`, `HorizontalLayout`, champs de filtre |
| Confirmations | `Notification` avec theme adapte |
| Actions secondaires | `MenuBar` ou boutons avec infobulles |
| Indicateurs de statut | `Span` theme par classe CSS et texte explicite |

### Risques et corrections necessaires

1. La maquette mobile conserve la barre laterale ouverte et comprime la table. Sur petit ecran, le tiroir devra etre replie par defaut et ouvert temporairement par un bouton.
2. Une table desktop ne doit pas simplement deborder horizontalement. Les cas d'onboarding devront utiliser une presentation compacte ou masquer les colonnes secondaires selon la largeur.
3. Les textes et controles sont tres petits dans les maquettes. L'implementation conservera des zones cliquables suffisantes et une taille de texte lisible.
4. Les actions visibles seulement au survol doivent aussi etre accessibles au clavier et sur ecran tactile.
5. La couleur seule ne suffira jamais a communiquer un statut ou une anomalie.
6. Le constructeur visuel de modeles est une fonctionnalite interessante, mais il depasse le premier jalon. Le moteur JSON doit etre fonctionnel avant sa construction.

### Priorisation fonctionnelle

Pour le portfolio, les ecrans seront abordes dans cet ordre : formulaire client dynamique, tableau de bord conseiller, revision d'une soumission, generation et liste des documents, puis eventuellement constructeur visuel de modeles.

### Statut

Direction visuelle analysee et acceptee comme reference, avec adaptation responsive obligatoire.

## Etape 2 - Generation du squelette Spring Boot et Vaadin

**Date :** 31 aout 2026

### Objectif

Creer une application minimale avec les versions compatibles choisies par Vaadin Start, sans exemple metier ni dependance de base de donnees.

### Configuration generee

| Parametre | Valeur |
|---|---|
| Modele Vaadin Start | Empty |
| Group ID | `com.wealthonboard` |
| Artifact ID | `wealthonboard` |
| Java cible | 21 |
| Vaadin | 25.2.6 |
| Spring Boot | 4.1.0 |
| Build | Maven Wrapper |

L'archive generee a ete inspectee dans un dossier temporaire. Son depot `.git` interne a ete exclu afin de conserver l'historique Git deja initialise pour le projet.

### Verification executee

```powershell
.\mvnw.cmd --version
.\mvnw.cmd test
```

### Resultat

```text
No tests to run.
BUILD SUCCESS
Total time: 8.508 s
```

L'absence de tests est normale pour le squelette vide. La compilation reussie confirme que Maven resout les dependances et que le code genere est compatible avec l'environnement local.

### Statut

Squelette integre et compilation validee.

### Verification du demarrage web

Une vue minimale `MainView` a ete ajoutee avec la route racine `@Route("")`. Le port local par defaut a ete place a `8082`, car le port `8080` etait utilise par d'autres services. La configuration conserve la possibilite de fournir la variable d'environnement `PORT` :

```properties
server.port=${PORT:8082}
```

Le premier demarrage affichait la page Vaadin `No views found`. La classe `MainView` existait, mais sa declaration de package etait absente. Elle compilait alors dans le package Java par defaut, en dehors de l'arborescence analysee par Spring Boot.

La correction appliquee est :

```java
package com.wealthonboard.ui;
```

Apres recompilation et redemarrage :

- Spring Boot demarre avec succes;
- Tomcat ecoute sur `http://localhost:8082`;
- Vaadin detecte la route racine;
- le navigateur affiche `WealthOnboard` et `Fintech client onboarding portal`;
- le titre de page est configure par `@PageTitle("WealthOnboard")`.

### Statut final

Application Spring Boot et premiere vue Vaadin fonctionnelles. Etape terminee et validee.
