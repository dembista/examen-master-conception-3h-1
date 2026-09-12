# Contrats et justification

## Invariants

1. Une `Product` conserve une référence non vide, un prix en centimes positif ou nul et un poids strictement positif.
2. Une `Order` possède un client non vide, des lignes de quantité strictement positive et reste modifiable seulement à l'état `BROUILLON`.
3. Une commande `VALIDEE` conserve son total calculé, ne peut plus recevoir de ligne ni changer de stratégie, et ne déclenche qu'une notification lors de sa validation réussie.

## Contrat de validation

- Préconditions : la commande est `BROUILLON`, contient au moins une ligne et son poids total ne dépasse pas la limite de `Configuration`.
- Postconditions en cas de succès : le total sous-total + livraison est calculé puis figé, l'état devient `VALIDEE` et `Notifier.notify` reçoit l'identifiant client et le montant.
- Refus : une exception métier est levée, l'état reste `BROUILLON` et aucune notification n'est appelée.

## Relations et SOLID

`FragileProduct` hérite de `Product` et ajoute une consigne d'emballage utile au domaine ; le code qui attend un produit peut donc l'utiliser sans traitement spécial.
`Order` compose ses `OrderLine` : une ligne est créée par la commande et n'est jamais partagée.
`Catalog` agrège des produits : un même produit peut être dans plusieurs catalogues et vit indépendamment.
`Order` dépend ponctuellement de `Notifier` lors de la validation, sans connaître la technique de notification.
`ShippingStrategy` est Strategy : `Order` délègue le tarif à une abstraction et les trois stratégies respectent la même signature.
Une nouvelle stratégie peut être ajoutée sans modifier `Order` ni la validation, ce qui applique ouvert/fermé.
L'injection de `Configuration` et `Notifier` applique l'inversion des dépendances et rend les tests indépendants du Singleton et de la console.
`DefaultConfiguration` fournit l'instance unique par classe-holder, mais seul le point de composition utilise ce Singleton.
Les stratégies sont substituables car elles acceptent le même sous-total et le même poids et retournent toujours des frais en centimes, sans modifier la commande.
L'encapsulation de `Order` protège les transitions d'état et interdit les opérations après validation.
