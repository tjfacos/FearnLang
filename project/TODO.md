Basic:
* Do standard library imports in a less stupid way
* HashMap for Symbol Tables

NTS: TEST CHANGES TO DO WITH POINT 2 (fuck with imports)

Optimisation:
* Constant folding
* Constant propogation

Done:
* Migrate to gradle
* Modify selection statements to allow any kind of statement, not just compound
* Change the generator stack to only store the program name
* Rationalise the compiling of imports, so there's only one compile method (probably using parameters)