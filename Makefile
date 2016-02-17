PKG=edu.matc.entjava
PKG_PATH=$(subst .,/,$(PKG))
JAVA_FILES:=$(wildcard src/$(PKG_PATH)/*.java)
JAVA_CLASSES:=$(patsubst src/%.java,out/class/%.class,$(JAVA_FILES))
MAIN_CLASS?=SantaInAnElevatorRunner
ARGS?=src/SantaUpDown.txt

JAR ?= $(notdir $(shell pwd)).jar
LIBS = ../lib/log4j.jar
TLIBS = ../lib/junit-4.12.jar:../lib/hamcrest-core-1.3.jar
COBERTURA_LIBS = ../cobertura-2.1.1/lib/slf4j-api-1.7.5.jar:../cobertura-2.1.1/cobertura-2.1.1.jar
CLIBS = .:out/class:$(LIBS)
RLIBS = out/class:res:$(LIBS)
TCLIBS = .:$(TLIBS)
TRLIBS = out/test_class:$(RLIBS):$(TLIBS):$(COBERTURA_LIBS)

.PHONY: build jar run clean build_tests run_tests cobertura-instrument cobertura-report cobertura-check test check report
LIST:=

$(JAVA_CLASSES) : out/class/%.class : src/%.java
	$(eval LIST+=$<)

jar: $(JAR)

$(JAR): build
	[ -d out/jar ] || mkdir -p out/jar
	jar -cfM out/jar/$(JAR) -C res . -C out/class .

run: build
	@echo Running $(MAIN_CLASS)...
	java -cp $(RLIBS) $(PKG_PATH)/$(MAIN_CLASS) $(ARGS)

build: $(JAVA_CLASSES)
	@if [ ! -z "$(LIST)" ] ; then \
		echo Building class files... ; \
		[ -d out/class ] || mkdir -p out/class ; \
		echo $(run_javac) ; \
		$(run_javac); \
	fi

define run_javac
javac -cp $(CLIBS) -d out/class $(LIST)
endef

build_tests:
	[ -d out/test_class ] || mkdir -p out/test_class
	find tests -name 'SantaInAnElevatorTest*.java' -exec javac -cp out/class:$(TCLIBS) -d out/test_class {} +

run_tests:
	java -Dnet.sourceforge.cobertura.datafile=out/cobertura.ser -cp $(TRLIBS) SantaInAnElevatorTestRunner

clean:
	rm -rf out
	rm -f *.log

cobertura-instrument:
	../cobertura-2.1.1/cobertura-instrument.sh --datafile out/cobertura.ser out/class/.

cobertura-report: out/cobertura.ser
	../cobertura-2.1.1/cobertura-report.sh --format html --datafile out/cobertura.ser --destination out/coverage --srcdir src out/class

cobertura-check: out/cobertura.ser
	../cobertura-2.1.1/cobertura-check.sh --datafile out/cobertura.ser out/class

test: cobertura-instrument build_tests run_tests
check: cobertura-check
report: cobertura-report
	firefox out/coverage/index.html


DEPLOY_ROOT ?= $(notdir $(shell pwd))
REMOTE_USER ?= tomcat
REMOTE_ADDR ?= sol.blackshard.net
REMOTE_PORT ?= 11322
REMOTE_PATH ?= /opt/local/share/tomee/webapps
REMOTE      ?= $(REMOTE_USER)@$(REMOTE_ADDR):$(REMOTE_PATH)
WAR			?= $(DEPLOY_ROOT).war

war: $(WAR)

$(WAR): $(JAR)
	[ -d out ] || mkdir out
	cp $(JAR) $(WAR)

undeploy:
	rm -f /opt/tomee/webapps/$(DEPLOY_ROOT)
	rm -f /opt/tomee/webapps/$(DEPLOY_ROOT).war

deploy: $(WAR)
	cp out/$(DEPLOY_ROOT).war /opt/tomee/webapps/

deploy_remote: $(WAR)
	scp -P $(REMOTE_PORT) out/$(DEPLOY_ROOT).war $(REMOTE)
