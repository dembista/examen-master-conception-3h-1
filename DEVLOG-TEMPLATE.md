# DevLog d'examen — 3 heures

**Nom et prénom :** Papa Demba Ndoye  
**JDK :** Java 17 cible, vérification avec JDK 25.0.2  
**Branche de travail :** dembandoye  
**Tag final :** v1.0.0-exam

> Trois entrées courtes suffisent. Compléter pendant l'épreuve. Ne pas inventer de commande exécutée, de résultat ou de hash. Utiliser un chemin de fichier si le commit n'existe pas encore.

## Entrée 1 — Conception initiale

- Heure : non relevée
- Décision de conception et raison : injecter `Configuration` et `Notifier` dans `Order`, afin de tester le métier sans dépendre directement du Singleton ni de la console.
- Autre option envisagée et raison du rejet : appeler directement `DefaultConfiguration.instance()` dans `Order`, ce qui empêcherait la substitution de configuration en test.
- Relation POO ou principe SOLID concerné : Strategy, dépendance à des abstractions et inversion des dépendances.
- Preuve : [docs/classes.puml](docs/classes.puml), [docs/analyse.md](docs/analyse.md)

## Entrée 2 — Réalisation

- Heure : non relevée
- Fonctionnalité réalisée : produits, catalogue, commande composée de lignes, trois stratégies de livraison, validation et notification ont été réalisés.
- Difficulté rencontrée, cause identifiée et résolution : le constructeur compact de `OrderLine` avait une visibilité privée incompatible avec le record public ; il a été rendu public.
- Vérification effectuée et résultat : compilation Java 17 réussie et exécution des tests automatisés réussie.
- Preuve : [src/exam/Order.java](src/exam/Order.java), [tests/UnitOrderTest.java](tests/UnitOrderTest.java)

## Entrée 3 — Vérification finale

- Heure : non relevée
- Commande de compilation exécutée : `javac -encoding UTF-8 --release 17 -d build\\classes src\\exam\\*.java tests\\*.java`
- Commande de tests exécutée : `java -cp build\\classes exam.AllTests`
- Résultat observé : 6 scénarios réussis, 0 échoué, 0 non exécuté.
- Fonctionnalités incomplètes et limites : aucune fonctionnalité demandée n'est incomplète ; aucune persistance ou concurrence n'est prévue.
- Preuve : [tests/AllTests.java](tests/AllTests.java), [README.md](README.md)

## Bilan critique — six à dix lignes

Le Singleton simplifie l'accès à la configuration par défaut, mais introduit un état global et complique l'isolation des tests s'il est appelé directement.
La solution injecte donc l'interface `Configuration` et réserve le Singleton au point de composition.
Si la notification échoue après validation, la commande reste `VALIDEE` avec son montant figé.
L'exception de notification est propagée à l'appelant afin de signaler l'échec.
Ce choix évite de valider deux fois une commande déjà engagée, mais peut laisser une notification non délivrée.
Le risque résiduel est la perte du signal côté système externe.
Une amélioration serait une file de notifications persistante avec reprise et idempotence.
Cette amélioration reste hors périmètre de l'épreuve.
