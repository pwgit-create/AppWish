package pn.cg.open_ai_remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pn.cg.open_ai_remote.request.RequestHandler;
import pn.cg.app_system.code_generation.ClassCompiler;
import pn.cg.app_wish.QuestionBuilder;
import pn.cg.datastorage.DataStorage;
import pn.cg.datastorage.constant.QuestionConstants;
import pn.cg.open_ai_remote.request.RequestHandlerImpl;
import pn.cg.util.FileUtil;
import pn.cg.util.StringUtil;
import pn.cg.util.TaskUtil;

import java.io.File;
import java.io.IOException;
import java.util.IllegalFormatCodePointException;

import static pn.cg.datastorage.constant.CommonStringConstants.ERROR;
import static pn.cg.datastorage.constant.CommonStringConstants.JAVA_FILE_EXTENSION;

/**
 * This class holds the logic for the requests and responses to the Open AI API
 */
public class OpenAiRemoteSystem {

    private ClassCompiler classCompiler;

    private RequestHandler requestHandler;
    private static Logger log = LoggerFactory.getLogger(OpenAiRemoteSystem.class);

    public OpenAiRemoteSystem() {

        classCompiler = new ClassCompiler();
        requestHandler = new RequestHandlerImpl();
    }

    /**
     * Create an app with default strategy
     *
     * @param appWish
     * @Strategy Send only 1 question to OpenAi and create only one .java file (if possible)
     */
    public boolean CreateApp(String appWish, boolean isRetryCompilation) {


        QuestionBuilder questionBuilder = new QuestionBuilder(appWish);
        String outputFromOpenAi = "";


        // Fetch response from OpenAi´s remote api

        if (isRetryCompilation) {
            while (DataStorage.getInstance().getCompilationJob().getErrorMessage() == null) {
            }
            log.error("Class did not compile\nHere is the question that would be sent to open ai" +
                    questionBuilder.createCompileErrorQuestion(DataStorage.getInstance().getCompilationJob().getErrorMessage()));


            //outputFromOpenAi = requestHandler.sendQuestionToOpenAi(questionBuilder.createCompileErrorQuestion(DataStorage.getInstance().getCompilationJob().getErrorMessage()));
           outputFromOpenAi = requestHandler.sendQuestionToOpenAi(QuestionConstants.CLASS_DID_NOT_COMPILE_PREFIX_2 + appWish);
        } else {
            outputFromOpenAi = requestHandler.sendQuestionToOpenAi(questionBuilder.createFeatureQuestion());
        }

        // Extract class name
        String className = StringUtil.extractClassNameFromTextWithJavaClasses(outputFromOpenAi);

        if (className.equalsIgnoreCase(ERROR)) {

            throw new IllegalFormatCodePointException(0);
        } else {

            //remove the request query from the text output
            String javaSourceCode = StringUtil.removeFirstLine(outputFromOpenAi);

            // Create file instance with class name and file extension
            File file = new File(TaskUtil.addFilePathToClassName(className + JAVA_FILE_EXTENSION));

            // Save the path to shared storage (if user wants to execute the java app after the build
            DataStorage.getInstance().setJavaExecutionPath(file.getAbsolutePath());

            // Write the Java code provided from OpenAi to file
            try {
                FileUtil.writeDataToFile(file, javaSourceCode);
            } catch (IOException e) {
                log.error(e.toString());
                throw new RuntimeException(e);
            }

            classCompiler.compileClass(className);

            while (DataStorage.getInstance().getCompilationJob() == null) {
            }
            while (DataStorage.getInstance().getCompilationJob().isResult() == null) {
            }


            if (!DataStorage.getInstance().getCompilationJob().isResult()) {

                log.debug("Entry block for compilation error is hit");

                return false;
            }
            return true;
        }


    }


}
