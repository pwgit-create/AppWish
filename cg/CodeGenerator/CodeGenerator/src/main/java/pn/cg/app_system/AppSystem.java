package pn.cg.app_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pn.cg.datastorage.DataStorage;
import pn.cg.datastorage.constant.CodeGeneratorConstants;
import pn.cg.open_ai_remote.OpenAiRemoteSystem;

public class AppSystem  {

    private static Logger log = LoggerFactory.getLogger(AppSystem.class);

    private static int retryCounter = 1;

    public static void StartCodeGenerator(String appWish) {


        log.debug("Started AppSystem");


        OpenAiRemoteSystem openAiRemoteSystem = new OpenAiRemoteSystem();

        boolean result = openAiRemoteSystem.CreateApp(appWish, false);

        if (!result) {

            while (CheckCompilationRetryCounter() && !result || !(retryCounter == CodeGeneratorConstants.retryClassCompileLimit)) {

                retryCounter++;
                log.debug("In CheckCompilationRetryCounter with counter -> " + retryCounter);
                result = openAiRemoteSystem.CreateApp(appWish, true);
            }

        }


    }

    private static boolean CheckCompilationRetryCounter() {

        if (retryCounter != CodeGeneratorConstants.retryClassCompileLimit
                && !DataStorage.getInstance().getCompilationJob().isResult()) {


            return true;

        } else return false;

    }

}

