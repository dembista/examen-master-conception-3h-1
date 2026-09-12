# Examen de Master - Conception et programmation orientées objet

## Environnement

- JDK cible : Java 17 (`javac --release 17`)
- JDK utilisé pour la vérification : 25.0.2 LTS
- Dépendances : aucune, tests automatisés avec `AssertionError`

## Exécution depuis la racine

```text
if not exist build\classes mkdir build\classes
javac -encoding UTF-8 --release 17 -d build\classes src\exam\*.java tests\*.java
java -cp build\classes exam.Main
java -cp build\classes exam.AllTests
```

La dernière commande exécute quatre scénarios unitaires et deux scénarios d'intégration.

## Conception

- `ShippingStrategy` applique Strategy ; les stratégies standard, express et retrait sont substituables.
- `DefaultConfiguration` est un Singleton initialisé par classe-holder, consommé via `Configuration` pour permettre les configurations de test.
- `Order` compose ses `OrderLine`, tandis que `Catalog` agrège des `Product` indépendants.
- `FragileProduct` spécialise `Product` pour porter une consigne d'emballage métier, ce qui donne un héritage utilisé par le modèle.
- `Notifier` est une dépendance ponctuelle abstraite appelée après validation.

Les détails des contrats et les diagrammes sont dans `docs/`.

## Remise

- Tag annoté : `v1.0.0-exam`
- Justification : première version complète remise, avec modèle, stratégies, validation, tests et documentation.
- Fonctionnalités réalisées : modèle métier, trois livraisons, injection de configuration et notification, démonstration, six scénarios automatisés, UML.
- Fonctionnalités incomplètes / défauts connus : aucun dans le périmètre demandé ; l'échec d'une notification est propagé après que la validation est acquise.
- Résultat observé : compilation Java 17 réussie et six scénarios réussis.

Aucun fichier compilé ne doit être inclus dans l'archive de remise ; le dossier `build/` est ignoré par Git.
