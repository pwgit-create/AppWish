package pn.cg.open_ai_remote.request;

import com.theokanning.openai.completion.CompletionRequest;

import static pn.cg.datastorage.constant.CodeGeneratorConstants.OPEN_AI_MODEL_GPT_3;

/**
 * Handles all requests to the Open AI Api
 */
public interface RequestHandler {

    /**
     * Creates a request with a question as prompt and makes it ready for being sent into OpenAi´s api
     *
     * @param question
     * @return
     */
    default CompletionRequest createCompletionRequestByQuestion(String question) {

        return CompletionRequest.builder()
                .prompt(question)
                .model(OPEN_AI_MODEL_GPT_3)
                .echo(true)
                .maxTokens(4000)
                .build();

    }

    /**
     * Sends a question to OpenAi´s api and gets a reply
     * @param question
     * @return String
     */
    String sendQuestionToOpenAi(String question);

}
