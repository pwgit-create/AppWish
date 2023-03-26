package pn.cg.datastorage.constant;

public class CodeGeneratorConstants {

    public final static String OPEN_AI_MODEL_GPT_3 = "text-davinci-003";

    /**
     *  OkHttp 3 connection session value in seconds
     */
    public final static int CONNECTION_TIMEOUT=600;

    /**
     * Limit the number of retries that will be sent to OpenAi when the compilation fials
     */
    public final static int retryClassCompileLimit = 5;

}
