KB_RUNTIME ?= /kb/runtime
DEPLOY_RUNTIME ?= $(KB_RUNTIME)
KB_TOP ?= /kb/dev_container
TARGET ?= $(KB_TOP)
CURR_DIR = $(shell pwd)
SERVICE_NAME = $(shell basename $(CURR_DIR))
TARGET_DIR = $(TARGET)/services/$(SERVICE_NAME)
SERVLET_CLASS = us.kbase.meme.MemeServer
TARGET_PORT = 7049
THREADPOOL_SIZE = 20

default: compile

deploy: distrib

deploy-all: distrib

test:
	@echo "no tests"

compile: src lib
	./make_war.sh $(SERVLET_CLASS)

distrib:
	@echo "Target folder: $(TARGET_DIR)"
	mkdir -p $(TARGET_DIR)
	cp -f ./dist/service.war $(TARGET_DIR)
	cp -f ./glassfish_start_service.sh $(TARGET_DIR)
	cp -f ./glassfish_stop_service.sh $(TARGET_DIR)
	echo "./glassfish_start_service.sh $(TARGET_DIR)/service.war $(TARGET_PORT) $(THREADPOOL_SIZE)" > $(TARGET_DIR)/start_service.sh
	chmod +x $(TARGET_DIR)/start_service.sh
	echo "./glassfish_stop_service.sh $(TARGET_PORT)" > $(TARGET_DIR)/stop_service.sh
	chmod +x $(TARGET_DIR)/stop_service.sh

clean:
	@echo "nothing to clean"
