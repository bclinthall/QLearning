#https://ratiocaeli.com/blog/post/Makefile-for-Java-Projects
JAVAC =	javac

PACKAGES = solution util

BIN	= ./bin/

SRC	= ./src/

JAVAFLAGS =	-g -d $(BIN) -cp $(SRC)	

COMPILE	= $(JAVAC) $(JAVAFLAGS)

EMPTY =	

JAVAFILES =	$(subst	$(SRC),	$(EMPTY), $(wildcard $(SRC)*.java))
	
ifdef PACKAGES
PACKAGEDIRS	= $(addprefix $(SRC), $(PACKAGES))
PACKAGEFILES = $(subst $(SRC), $(EMPTY), $(foreach DIR,	$(PACKAGEDIRS),	$(wildcard $(DIR)/*.java)))
ALL_FILES =	$(PACKAGEFILES)	$(JAVA_FILES)
else
#ALL_FILES = $(wildcard	$(SRC).java)
ALL_FILES =	$(JAVA_FILES)
endif

# One of these should be the "main"	class listed in	Runfile
# CLASS_FILES =	$(subst	$(SRC),	$(BIN),	$(ALL_FILES:.java=.class))
CLASS_FILES	= $(ALL_FILES:.java=.class)

# The first	target is the one that is executed when	you	invoke
# "make". 

all	: $(addprefix $(BIN), $(CLASS_FILES))

# The line describing the action starts	with <TAB>
$(BIN)%.class :	$(SRC)%.java
	$(COMPILE) $<

clean :	
	rm -rf $(BIN)*
