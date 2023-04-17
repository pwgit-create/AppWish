package pn.cg.task;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pn.cg.app_system.code_generation.model.CompilationJob;
import pn.cg.datastorage.DataStorage;
import pn.cg.task.base.ShellScriptTask;
import pn.cg.util.StringUtil;

public class CompileClassTask extends ShellScriptTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(CompileClassTask.class);

    private  String assumedClassName;

    public CompileClassTask(String scriptName, String[] inputArgs) {
        super(scriptName, inputArgs);
        this.assumedClassName = inputArgs[0];

    }

    @Override
    public void run() {
        log.debug("Starting compile algorithm");
        String consoleOutput = RunShellScript();
        handleConsoleOutput(consoleOutput);
    }

    /**
     * Handles the console output from the compile process (javac)
     *
     * @param javacOutput String that contains an error message from a failed Java compilation
     */
    private void handleConsoleOutput(String javacOutput) {
        if (javacOutput != null){
            log.debug(javacOutput);}
        assert javacOutput != null;
        if (javacOutput.isEmpty()) {
            successFullCompilation();
        } else {
            compilationError(javacOutput);
        }
    }


    /**
     * Set compilation job to status successful,and add the value to the shared singleton
     *
     */
    private  void successFullCompilation() {
        log.debug("successFullCompilation");
        CompilationJob compilationJob = new CompilationJob(assumedClassName);
        compilationJob.setResult(true);
        compilationJob.setErrorMessage(null);
        DataStorage.getInstance().setCompilationJob(compilationJob);

    }

    /**
     * Set compilation job to status error,and adds the sanitized error String to the shared singleton
     */
    private  void compilationError(String javacOutput) {
        log.debug("compilationError");
        CompilationJob compilationJob = new CompilationJob(assumedClassName);
        compilationJob.setResult(false);
        compilationJob.setErrorMessage(StringUtil
                .removeBadRequestChars(StringUtil
                        .getFirstLine(javacOutput)));
        DataStorage.getInstance().setCompilationJob(compilationJob);
    }


}
