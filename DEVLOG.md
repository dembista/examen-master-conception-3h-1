# DevLog d'examen - 3 heures

**Nom et prénom :** Papa Demba Ndoye  
**JDK :** Java 17 cible, vérification avec JDK 25.0.2  
**Branche de travail :** dembandoye  
**Tag final :** v1.0.0-exam

## Entrée 1 - Conception initiale

- Heure : début de réalisation
- Décision : injecter `Configuration` et `Notifier` dans `Order`, afin que le métier ne dépende pas d'un accès global au Singleton.
- Option rejetée : appeler directement `DefaultConfiguration.instance()` dans `Order`, car cela empêcherait la substitution en test.
- Relation / SOLID : dépendance à une abstraction et Strategy pour les frais.
- Preuve : `docs/classes.puml`, `docs/analyse.md`, `src/exam/Configuration.java`.

## Entrée 2 - Réalisation

- Heure : après réalisation du modèle
- Fonctionnalité : produits, catalogue, commande composée de lignes, trois stratégies et notification ont été réalisés.
- Difficulté : le constructeur compact du record `OrderLine` devait avoir une visibilité cohérente avec le record public ; il a été corrigé avant la validation.
- Vérification : compilation en cible 17 et exécution du programme de tests.
- Preuve : `src/exam/Order.java`, `tests/UnitOrderTest.java`.

## Entrée 3 - Vérification finale

- Heure : fin de réalisation
- Commande de compilation : `javac -encoding UTF-8 --release 17 -d build\\classes src\\exam\\*.java tests\\*.java`
- Commande de tests : `java -cp build\\classes exam.AllTests`
- Résultat observé : 4 scénarios unitaires et 2 scénarios d'intégration réussis, aucun échec.
- Fonctionnalités incomplètes et limites : aucune fonctionnalité demandée n'est incomplète ; aucune persistance ni concurrence n'est prévue par le sujet.
- Preuve : `tests/AllTests.java`, `README.md`.

## Bilan critique

Le Singleton simplifie l'accès à la configuration par défaut, mais il introduit un état global et peut compliquer l'isolation de tests si le métier l'appelle directement.
La solution limite ce risque en injectant l'interface `Configuration` et en réservant le Singleton au point de composition.
Si la notification échoue après validation, la commande reste `VALIDEE` avec son montant figé, puis l'exception de notification est propagée à l'appelant.
Ce choix évite de valider deux fois une commande déjà engagée, mais peut laisser une notification non délivrée.
Le risque résiduel est la perte du signal côté système externe.
Une amélioration serait une file de notifications persistante avec reprise et idempotence.
Cette amélioration est hors périmètre de l'épreuve.
