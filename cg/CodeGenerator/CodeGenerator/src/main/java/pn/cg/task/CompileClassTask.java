package pn.cg.task;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pn.cg.app_system.code_generation.model.CompilationJob;
import pn.cg.datastorage.DataStorage;
import pn.cg.task.base.ShellScriptTask;
import pn.cg.util.StringUtil;

public class CompileClassTask extends ShellScriptTask implements Runnable {

    private static Logger log = LoggerFactory.getLogger(CompileClassTask.class);

    private String assumedClassName;

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
     * Handles the console output from the javac command
     *
     * @param javacOutput
     */
    private void handleConsoleOutput(String javacOutput) {


        if (javacOutput != null)
            log.debug(javacOutput);
        else
            return;

        if (javacOutput.isEmpty()) {

            DataStorage.getInstance().setCompilationJob(new CompilationJob(assumedClassName));
            DataStorage.getInstance().getCompilationJob().setResult(true);
            successFullCompilation();
        } else {



            DataStorage.getInstance().setCompilationJob(new CompilationJob(assumedClassName));
            DataStorage.getInstance().getCompilationJob().setErrorMessage(StringUtil
                    .removeBadRequestChars(StringUtil
                            .getFirstLine(javacOutput)));
            DataStorage.getInstance().getCompilationJob().setResult(false);


            compilationError(javacOutput);
        }
    }


    private synchronized   void successFullCompilation() {
        log.debug("successFullCompilation");
        



    }

    private synchronized   void compilationError(String javacOutput) {
        log.debug("compilationError");
        log.debug(javacOutput);

    }


}
