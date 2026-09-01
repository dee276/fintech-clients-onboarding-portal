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

## Etape 3 - Modele Java des formulaires JSON

**Date :** 31 aout 2026

### Objectif

Representer la structure configurable d'un formulaire sans introduire de logique Vaadin ou de persistance.

### Fichiers ajoutes

```text
form/model/FormTemplate.java
form/model/FormSection.java
form/model/FormField.java
form/model/VisibilityRule.java
```

Les quatre types utilisent des `record` Java. Ce choix convient a des objets de configuration immuables dont la responsabilite principale est de transporter les donnees deserialisees depuis JSON.

La structure est hierarchique :

```text
FormTemplate
  -> List<FormSection>
       -> List<FormField>
            -> VisibilityRule optionnelle
```

`FormField.type` reste une chaine afin que le moteur de rendu puisse etre etendu progressivement. La valeur attendue d'une regle est representee par `JsonNode`, ce qui preserve son type JSON : texte, nombre ou booleen.

La propriete JSON `equals` est mappee vers `expectedValue` en Java avec `@JsonProperty("equals")`. Le nom Java evite une confusion avec la methode standard `Object.equals`.

### Dependance

Jackson Databind est maintenant une dependance directe du projet :

```xml
<dependency>
    <groupId>tools.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>
```

Le projet utilise Jackson 3 et le package `tools.jackson.databind` pour `JsonNode`. Les annotations conservent le package `com.fasterxml.jackson.annotation`.

### Verification

```text
Compiling 6 source files with release 21
No tests to run
BUILD SUCCESS
```

### Statut

Modele Java termine et compilation validee. Le modele JSON, le service de chargement et le premier test de deserialisation restent a realiser pour terminer le jalon 1.

## Etape 4 - Chargement et deserialisation du premier formulaire

**Date :** 31 aout 2026

### Modele JSON

Le fichier `src/main/resources/forms/individual-investor.json` definit deux sections et huit champs. Il couvre les types `text`, `date`, `email`, `boolean` et `select`.

Les champs de residence demontrent deux conditions opposees :

```text
canadianResident = true  -> afficher province
canadianResident = false -> afficher country et foreignTaxId
```

La syntaxe a d'abord ete validee avec `ConvertFrom-Json`. Aucun identifiant de champ duplique n'a ete detecte et les valeurs conditionnelles ont conserve le type booleen.

### Service de chargement

`FormTemplateService` recoit l'`ObjectMapper` par injection de dependance. La methode `loadTemplate` transforme un identifiant de formulaire en chemin classpath, ouvre la ressource avec `ClassPathResource`, puis la deserialise en `FormTemplate`.

Le flux est ferme avec un `try-with-resources`. Une erreur de lecture est exposee sous forme d'`IllegalStateException` contenant l'identifiant du formulaire concerne.

### Premier test JUnit

`FormTemplateServiceTest` est un test unitaire pur. Il construit un `JsonMapper` sans lancer Spring Boot, charge le formulaire et verifie :

- l'identifiant et le titre du formulaire;
- la presence de deux sections;
- le type et le caractere obligatoire de `firstName`;
- les options de `province`;
- la condition booleenne positive de `province`;
- la condition booleenne negative de `country`.

### Resultat

```text
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
Total time: 12.963 s
```

## Bilan du jalon 1

Les objectifs du premier jalon sont atteints :

- l'application Spring Boot demarre;
- Vaadin affiche une vue sur le port local `8082`;
- le modele Java des formulaires est present;
- un formulaire JSON est stocke dans les ressources;
- Jackson deserialise ce formulaire par un service dedie;
- un test JUnit confirme le comportement.

Le prochain jalon introduira le rendu dynamique des champs Vaadin a partir du `FormTemplate`. Aucune logique de rendu n'a ete ajoutee pendant le jalon 1.

## Jalon 2 - Rendu dynamique Vaadin

**Date :** 1 septembre 2026

### Fabrique de composants

`FieldComponentFactory` centralise la correspondance entre un type JSON et un composant Vaadin :

| Type JSON | Composant Vaadin |
|---|---|
| `text` | `TextField` |
| `textarea` | `TextArea` |
| `number` | `NumberField` |
| `email` | `EmailField` |
| `date` | `DatePicker` |
| `boolean` | `RadioButtonGroup<Boolean>` |
| `select` | `Select<String>` |

Chaque composant recoit l'identifiant, le libelle et l'indicateur de champ obligatoire du `FormField`. Les options JSON alimentent le composant `Select`. Un type inconnu produit une `IllegalArgumentException` explicite.

Le type booleen utilise les choix `Yes` et `No` sans valeur initiale. Cette approche permet de distinguer une reponse negative d'une question sans reponse.

### Renderer dynamique

`DynamicFormRenderer` parcourt les sections du `FormTemplate`, cree un conteneur et un titre par section, puis delegue chaque champ a la fabrique. Il ne charge pas le JSON lui-meme et ne contient aucune condition liee au formulaire investisseur.

```text
FormTemplate
  -> DynamicFormRenderer
       -> FieldComponentFactory
            -> composants Vaadin
```

`MainView` charge le template `individual-investor` par `FormTemplateService` et transmet le modele au renderer. Le titre de la page provient egalement du JSON.

### Mise en page

Une mise en page de base a ete ajoutee sans reproduire prematurement le dashboard final :

- largeur de lecture limitee a 720 px;
- champs principaux sur toute la largeur disponible;
- sections separees par une bordure discrete;
- espacements et hierarchie typographique coherents;
- adaptation des marges et du titre sous 700 px.

La page et `styles.css` ont toutes deux repondu avec le statut HTTP `200` sur `http://localhost:8082`. La feuille servie contient les regles responsive et les styles de section attendus.

### Tests

La suite contient maintenant dix tests :

```text
DynamicFormRendererTest:      1
FieldComponentFactoryTest:    8
FormTemplateServiceTest:      1
Failures:                     0
Errors:                       0
BUILD SUCCESS
```

### Limites intentionnelles

Le renderer affiche encore tous les champs. La collecte des valeurs, les regles de visibilite, la validation et le bouton de soumission appartiennent au jalon 3.

### Statut

Jalon 2 termine et valide.
