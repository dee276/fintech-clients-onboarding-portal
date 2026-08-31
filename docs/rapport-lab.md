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
| Initialisation et rapport de laboratoire | `docs: initialize project lab report` | A creer |

