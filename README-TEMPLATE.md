# Examen — Papa Demba Ndoye

## Environnement

- Version du JDK : Java 17 cible, vérification avec JDK 25.0.2
- Dépendances éventuelles : aucune ; tests exécutés avec des assertions Java

## Exécution depuis la racine du dossier

- Compiler : `if not exist build\\classes mkdir build\\classes && javac -encoding UTF-8 --release 17 -d build\\classes src\\exam\\*.java tests\\*.java`
- Lancer la démonstration : `java -cp build\\classes exam.Main`
- Lancer les tests : `java -cp build\\classes exam.AllTests`

## Remise

- Tag annoté : `v1.0.0-exam`
- Justification du numéro de version : première version complète remise avec le modèle métier, les stratégies, la validation, les tests et la documentation.
- Fonctionnalités réalisées : produits, catalogues, commandes, lignes composées, trois modes de livraison Strategy, configuration Singleton injectable, notification, validation métier, démonstration, UML et six scénarios automatisés.
- Fonctionnalités incomplètes / défauts connus : aucune fonctionnalité demandée n'est incomplète ; aucune base de données, interface web ou concurrence n'est prévue.
- Résultats de tests réellement observés : compilation Java 17 réussie ; 4 scénarios unitaires et 2 scénarios d'intégration réussis, 0 échec.
