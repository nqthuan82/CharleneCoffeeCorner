# Charlene's Coffee Corner

### Description
Charlene decided to open her very own little coffee shop on a busy street corner.
Being the careful entrepreneur, she decided to start off with a limited offering, with the option to expand her choice of products, as
business goes. She has a bonus program that is to attract as many regular‘s as possible to have a steady turnaround. Our task is to write a simple program whose output is formatted like a receipt you would receive at a supermarket. The input to the program is a list of products the shopper wants to purchase (large coffee with extra milk, small coffee with special roast, bacon roll, orange juice).

### Products
- Coffee (small, medium, large) 2.50 CHF, 3.00 CHF, 3.50 CHF
- Bacon Roll 4.50 CHF
- Freshly squeezed orange juice (0.25l) 3.95 CHF

###### Extras:
- Extra milk 0.30 CHF
- Foamed milk 0.50 CHF
- Special roast coffee 0.90 CHF

### Bonus program
- Offer a customer stamp card, where every 5th beverage is for free.
- If a customer orders a beverage and a snack, one of the extra's is free.
- Assumption 1: only one 5th beverage bonus and one extra bonus are applied in a receipt. 
- Assumption 2: if there are multiple beverages/extras in a receipt, the one with the cheapest price are chosen for customer as a bonus.

### Technical info:
- The libraries are Java SE 11 and JUnit 5.9.2
- Use Maven to manage project
- Unit tests for PosService