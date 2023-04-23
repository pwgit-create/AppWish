package pn.cg.app_system.code_generation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pn.cg.datastorage.ThreadPoolMaster;
import pn.cg.datastorage.constant.CodeGeneratorConstants;
import pn.cg.datastorage.constant.CommonStringConstants;
import pn.cg.datastorage.constant.ScriptConstants;
import pn.cg.task.CompileClassTask;
import pn.cg.util.TaskUtil;

import java.util.concurrent.ExecutorService;

import static pn.cg.datastorage.constant.ScriptConstants.JAVAC_NO_EXTERNAL_JARS_SCRIPT_NAME;
import static pn.cg.datastorage.constant.ScriptConstants.JAVAC_SCRIPT_NAME;

public class ClassCompiler {

    private static final Logger log = LoggerFactory.getLogger(ClassCompiler.class);

    public ClassCompiler() {
    }

    /**
     * Tries to compile a file with java source code into byte code
     *
     * @param className
     */
    public void compileClass(String className) {


        String scriptToUse ="";

        if(CodeGeneratorConstants.USE_EXTERNAL_JARS_WHEN_COMPILING)
            scriptToUse = JAVAC_SCRIPT_NAME;
        else scriptToUse = JAVAC_NO_EXTERNAL_JARS_SCRIPT_NAME;

        ExecutorService executor = ThreadPoolMaster.getInstance().getExecutor();

        log.debug("Compiler path -> "+TaskUtil.addFilePathToClassName(className + CommonStringConstants.JAVA_FILE_EXTENSION));

        executor.execute(new CompileClassTask(scriptToUse, new String[]{TaskUtil.addFilePathToClassName(className + CommonStringConstants.JAVA_FILE_EXTENSION)}));


    }




}
