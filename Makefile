code-generator:
	@echo "***Installing Code Generator***"
	cd cg/CodeGenerator/CodeGenerator && mvn clean install
appwish:code-generator
	@echo "***Installing App Wish***"
	cd AppWish/AppWish && mvn clean install
run:appwish
	@echo "*** Starting App Wish ***"
	cd AppWish/AppWish && mvn javafx:run
code-generator-win:
	@echo "***Installing Code Generator***"
	cd cg\CodeGenerator\CodeGenerator && mvn clean install
appwish-win:code-generator-win
	@echo "***Installing App Wish***"
	cd AppWish\AppWish && mvn clean install
run-win:appwish-win
	@echo "*** Starting App Wish ***"
	cd AppWish\AppWish && mvn javafx:run
