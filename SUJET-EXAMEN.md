# Examen de Master — Conception et programmation orientées objet

**Durée : 3 heures — Note : /20**  
**Format proposé :** épreuve individuelle pratique sur ordinateur.  
**Livrables :** conception PlantUML, réalisation Java, tests automatisés, historique Git et DevLog.

Les conditions d'accès à Internet, aux documents et aux outils d'assistance sont fixées par l'enseignant avant le début de l'épreuve.

## 1. Contexte et problème de conception

Une boutique en ligne souhaite gérer des commandes et proposer plusieurs modes de livraison. Le client doit pouvoir changer de mode avant la validation de sa commande. L'ajout d'un mode ne doit pas nécessiter de modifier les règles déjà implémentées ni le traitement de validation.

Vous devez concevoir et réaliser une solution Java qui sépare les responsabilités, protège les invariants métier et permet de tester les composants indépendamment.

## 2. Besoins et règles métier

Un produit possède une référence, un prix en centimes et un poids en grammes. Un catalogue regroupe des produits existants. Le même produit peut appartenir à plusieurs catalogues et son existence ne dépend pas d'un catalogue.

Une commande appartient à un client identifié par une chaîne non vide. Elle contient des lignes associant un produit à une quantité. Les lignes sont créées et gérées exclusivement par la commande et ne doivent pas être partagées entre commandes.

Les références sont non vides, les prix positifs ou nuls, les poids et les quantités strictement positifs. Les montants sont calculés en centimes entiers.

| Mode de livraison | Calcul des frais |
|---|---|
| Standard | 500 centimes ; gratuit lorsque le sous-total des produits atteint 10 000 centimes |
| Express | 1 000 centimes + 200 centimes par kilogramme commencé |
| Retrait en magasin | Gratuit |

Le sous-total est la somme des prix unitaires multipliés par les quantités. Le poids total suit le même principe. Le total à payer est le sous-total augmenté des frais de livraison. Pour limiter le périmètre, la destination n'intervient pas dans les tarifs.

Une commande commence à l'état **BROUILLON**. Elle peut recevoir des lignes et changer de mode de livraison. La validation est refusée si elle est vide ou si son poids dépasse **30 000 grammes**, quel que soit le mode. Une validation réussie fige le montant total et passe la commande à l'état **VALIDEE**. Toute modification ultérieure et toute seconde validation sont refusées.

Après validation, le système appelle un composant de notification avec l'identifiant du client et le total validé. Une notification console suffit ; aucun message réel n'est envoyé. En cas de refus métier, aucune notification n'est déclenchée.

Une configuration unique fournit le seuil de gratuité et le poids maximal. Pour les tests, il doit être possible de substituer une configuration différente sans modifier l'instance globale.

## 3. Contraintes de conception

Votre solution doit mettre en évidence :

- un héritage pertinent, une composition, une agrégation et une dépendance ponctuelle ;
- le patron **Strategy** pour les modes de livraison ;
- le patron **Singleton** pour la configuration ;
- les principes **SOLID**, notamment la dépendance à des abstractions et la possibilité d'ajouter une stratégie sans modifier le noyau.

Une réalisation d'interface, à elle seule, ne suffit pas pour la question sur l'héritage : proposer une spécialisation par héritage de classe et en justifier l'utilité. Éviter une classe abstraite sans responsabilité uniquement destinée à satisfaire la consigne.

Le Singleton ne doit pas imposer un accès global direct depuis le code métier : prévoir une abstraction permettant la substitution en test.

## 4. Travail demandé et barème

### Partie A — Analyse et conception PlantUML — 5 points

1. **Contrats métier — 1 point.** Énoncer trois invariants et préciser les préconditions et postconditions de la validation.
2. **Diagramme de classes — 2 points.** Fournir un fichier `.puml` comprenant les responsabilités principales, interfaces, cardinalités, états et relations exigées. Identifier les rôles des deux patrons.
3. **Diagramme de séquence — 1 point.** Représenter la validation, le calcul délégué, la notification et une branche de refus métier.
4. **Justification — 1 point.** En dix à quinze lignes, justifier les quatre relations objet et expliquer concrètement l'application de deux principes SOLID. Expliquer également pourquoi les stratégies respectent un contrat substituable.

Les fichiers PlantUML doivent être cohérents avec le code final. Une image exportée n'est pas exigée.

### Partie B — Réalisation Java — 8 points

1. **Modèle métier — 2 points.** Réaliser produits, catalogues, commandes et lignes ; protéger les invariants et l'encapsulation.
2. **Strategy — 2 points.** Implémenter les trois modes, le calcul correct des frais et le changement de stratégie avant validation.
3. **Singleton et injection — 1 point.** Garantir une instance de configuration par chargeur de classes et permettre sa substitution dans les tests.
4. **Validation et notification — 2 points.** Vérifier le contenu et le poids, figer le total, interdire les opérations après validation et déclencher la notification via une abstraction.
5. **Démonstration — 1 point.** Fournir un programme principal qui crée une commande, affiche son total, change sa livraison puis la valide.

Utiliser Java 17 ou la version installée et annoncée par l'enseignant. Une interface console suffit. Aucune base de données, interface graphique, API Web, authentification ou concurrence d'exécution n'est à implémenter. Des exceptions explicites conviennent pour les refus métier.

### Partie C — Tests unitaires et d'intégration — 4 points

Fournir **au moins six scénarios automatisés**, exécutables par une commande documentée.

**Quatre scénarios unitaires — 2 points :**

- Standard : vérifier les frais juste sous le seuil et au seuil de gratuité.
- Express : vérifier les frais pour 1 000 g et 1 001 g.
- Commande : vérifier le changement de total avec deux stratégies de test à frais fixes.
- Commande : vérifier qu'une modification est refusée après validation.

**Deux scénarios d'intégration — 2 points :**

- Assembler les vrais composants métier et une vraie stratégie ; valider une commande et vérifier le total, l'état et une unique notification contenant le bon client et le bon montant.
- Tenter de valider une commande dépassant le poids maximal ; vérifier le refus, l'état BROUILLON et l'absence de notification.

Une notification en mémoire peut servir d'espion en intégration. Distinguer dans les fichiers ou les noms les tests unitaires et les tests d'intégration. Chaque scénario suit préparation, action et vérification. Plusieurs assertions peuvent appartenir au même scénario.

JUnit est autorisé s'il est disponible dans l'environnement. À défaut, un exécuteur Java avec vérifications levant `AssertionError` est accepté. L'affichage de résultats sans assertions ne constitue pas un test automatisé.

### Partie D — Git, DevLog et recul critique — 3 points

1. **Versionnement Git — 1 point.** Initialiser un dépôt local, produire au moins trois commits correspondant à des étapes réelles, utiliser une branche de travail et l'intégrer à `main`. Créer un tag annoté pour la remise et justifier son numéro dans le README. Exclure les fichiers compilés. Aucun dépôt distant ni pull request n'est exigé.
2. **DevLog — 1 point.** Compléter le modèle fourni avec trois entrées courtes : conception initiale, réalisation et difficulté rencontrée, vérification finale. Chaque entrée indique une décision, un résultat réel et une référence à un fichier, un test ou un commit existant. Le journal complet doit tenir en une à deux pages environ.
3. **Analyse critique — 1 point.** En six à dix lignes dans le DevLog, expliquer une limite du Singleton et le comportement que vous retenez si la notification échoue après validation. Identifier un risque et une amélioration possible, sans devoir implémenter cette amélioration.

Le nombre de commits au-delà du minimum et la longueur du journal n'apportent aucun point supplémentaire. Ne pas reconstruire artificiellement une progression en fin d'épreuve.

## 5. Répartition indicative des 180 minutes

| Activité | Durée |
|---|---:|
| Lecture, contrats et diagrammes | 35 min |
| Réalisation Java | 80 min |
| Tests et corrections | 40 min |
| Vérification de la remise et documentation finale | 25 min |
| **Total** | **180 min** |

Tenir Git et les trois entrées du DevLog au fil de l'épreuve ; leur mise à jour est incluse dans ces durées.

## 6. Dossier à remettre

```text
NOM_Prenom/
  README.md
  DEVLOG.md
  .gitignore
  .git/                 # historique local à conserver
  docs/
    classes.puml
    validation.puml
    analyse.md          # contrats et justification de la partie A
  src/                  # code Java
  tests/                # tests unitaires et d'intégration
  [script ou fichier de construction, si utilisé]
```

Le README précise la version du JDK, les commandes de compilation, de démonstration et de tests, le tag de remise et les fonctionnalités incomplètes. L'organisation des packages est libre.

Remettre une archive ZIP conservant le dossier caché `.git` ; vérifier sa présence dans l'archive. Ne pas joindre les fichiers compilés ni les caches de dépendances. L'enseignant précise le canal de dépôt et les règles de nommage institutionnelles.

Une solution partielle reste évaluable : indiquer honnêtement ce qui fonctionne, échoue ou n'a pas été testé. La compilation, la cohérence UML/code, la pertinence des assertions et la justification des choix comptent davantage que l'ajout de fonctionnalités hors sujet.
