package pn.cg.app_system.code_generation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pn.cg.datastorage.ThreadPoolMaster;
import pn.cg.datastorage.constant.CommonStringConstants;
import pn.cg.datastorage.constant.ScriptConstants;
import pn.cg.task.CompileClassTask;
import pn.cg.util.TaskUtil;

import java.util.concurrent.ExecutorService;

public class ClassCompiler {

    private static Logger log = LoggerFactory.getLogger(ClassCompiler.class);

    public ClassCompiler() {

    }

    /**
     * Tries to compile a file with java source code into byte code
     *
     * @param className
     */
    public void compileClass(String className) {


        ExecutorService executor = ThreadPoolMaster.getInstance().getExecutor();

        log.debug("Compiler path -> "+TaskUtil.addFilePathToClassName(className + CommonStringConstants.JAVA_FILE_EXTENSION));

        executor.execute(new CompileClassTask(ScriptConstants.JAVAC_SCRIPT_NAME, new String[]{TaskUtil.addFilePathToClassName(className + CommonStringConstants.JAVA_FILE_EXTENSION)}));


    }




}