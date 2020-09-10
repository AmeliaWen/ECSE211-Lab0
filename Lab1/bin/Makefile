# Copyright 1996-2020 Cyberbotics Ltd.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

### Generic Webots Makefile system for Java controllers, modified from the original file to make sure that the clean target actually deletes the class files in the "/ca/mcgill/ecse211/project/" folder


# WEBOTS_HOME is a sine qua non condition to run this Makefile
ifndef WEBOTS_HOME
 $(error The WEBOTS_HOME environment variable is not defined.)
endif

# WEBOTS_HOME_PATH is commonly defined in the caller Makefile
# but as it is not necessary on non-windows OS, it seems safer to reconstruct it there if required
ifndef WEBOTS_HOME_PATH
 WEBOTS_HOME_PATH=$(strip $(subst % ,\ ,$(strip $(subst \,/,$(WEBOTS_HOME)))))
endif


###-----------------------------------------------------------------------------
### OS determination and OS specific commands
###-----------------------------------------------------------------------------

include $(WEBOTS_HOME_PATH)/resources/Makefile.os.include

###-----------------------------------------------------------------------------
### Paths
###-----------------------------------------------------------------------------

# compute the current directory, the CURDIR variable may be already set
ifndef CURDIR
 CURDIR = $(shell pwd)
endif

# compute the name of the controller from its directory (i.e. braiten)
BAD_NAME = $(basename $(notdir $(CURDIR)))

# this is a hack to work around a problem with spaces in dir names
NAME = $(word $(words $(BAD_NAME)),$(BAD_NAME))

# set jar file name
ifndef JAR_FILE
  JAR_FILE = $(NAME).jar
endif

###-----------------------------------------------------------------------------
### Compilation options
###-----------------------------------------------------------------------------

# set the Java compiler options
# add additional JARs here to link with them at compile time
ifneq ($(strip $(CLASSPATH)),)
  ifeq ($(OSTYPE),windows)
   JAVAC_OPTS += -Xlint -classpath "$(WEBOTS_CONTROLLER_LIB_PATH)/java/Controller.jar;lib/simlejos.jar;$(CLASSPATH);." -target 11 -source 11
  else
   JAVAC_OPTS += -Xlint:-options -Xlint -classpath "$(WEBOTS_CONTROLLER_LIB_PATH)/java/Controller.jar:lib/simlejos.jar:$(CLASSPATH):." -target 11 -source 11
  endif
 else
  ifeq ($(OSTYPE),windows)
   JAVAC_OPTS += -Xlint -classpath "$(WEBOTS_CONTROLLER_LIB_PATH)/java/Controller.jar;lib/simlejos.jar;." -target 11 -source 11
  else
   JAVAC_OPTS += -Xlint:-options -Xlint -classpath "$(WEBOTS_CONTROLLER_LIB_PATH)/java/Controller.jar":lib/simlejos.jar:. -target 11 -source 11
  endif
endif


###-----------------------------------------------------------------------------
### Makefile setup and rules
###-----------------------------------------------------------------------------

.PHONY: all clean release debug profile

$(JAR_FILE):
	@javac $(JAVAC_OPTS) *.java
	@jar cf $@ *.class

jar: clean $(JAR_FILE)


clean:
	@rm -rf *.jar *.class */*.class */*/*.class */*/*/*.class */*/*/*/*.class > /dev/null 2>&1 || :

