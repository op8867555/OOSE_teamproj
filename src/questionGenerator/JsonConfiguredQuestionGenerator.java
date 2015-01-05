package questionGenerator;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.stream.Collectors;

import core.Question;
import core.QuestionGenerator;

import org.json.*;

abstract public class JsonConfiguredQuestionGenerator extends QuestionGenerator {

    JSONObject config;
    JSONArray questions;
    int currentQuestionIndex;

    abstract public Question parseQuestion(JSONObject o);

    public JsonConfiguredQuestionGenerator(String path) throws IOException {
        String raw_config = Files.lines(FileSystems.getDefault().getPath(path))
                                 .collect(Collectors.joining());
        this.config = new JSONObject(raw_config);
        this.questions = config.getJSONArray("questions");
    }

    @Override
    public Question getNextQuestion() {
        Question q = parseQuestion(questions.getJSONObject(currentQuestionIndex));
        currentQuestionIndex += 1;
        return q;
    }

}
