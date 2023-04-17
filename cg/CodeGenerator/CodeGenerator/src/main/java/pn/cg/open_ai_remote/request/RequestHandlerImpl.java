package pn.cg.open_ai_remote.request;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pn.cg.datastorage.constant.CodeGeneratorConstants;

import java.util.List;

public class RequestHandlerImpl implements RequestHandler {

    private final OpenAiService service;

    private static final Logger log = LoggerFactory.getLogger(RequestHandlerImpl.class);

    public RequestHandlerImpl() {

        service = new OpenAiService(System.getenv("OPENAI_TOKEN"), CodeGeneratorConstants.CONNECTION_TIMEOUT);
    }


    @Override
    public  String sendQuestionToOpenAi(String question) {
        CompletionRequest completionRequest = createCompletionRequestByQuestion(question);
        List<CompletionChoice> completionChoices = service.createCompletion(completionRequest).getChoices();
        String outputFromOpenAi = (completionChoices.get(0).getText());
        log.debug(outputFromOpenAi);
        return outputFromOpenAi;
    }
}




