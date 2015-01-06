package questionGenerator;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.stream.Collectors;

import core.AnswerType;
import core.Question;
import core.QuestionGenerator;
import answerValidator.*;

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

    public static void main (String[] args) {
        String path = "/tmp/test.json";
        AnswerType at = new AnswerType() {
            @Override
            public void addOption(Object o) {}

            @Override
            public Object answer() { return null; }};

        try {
            JsonConfiguredQuestionGenerator qg =
                new JsonConfiguredQuestionGenerator(path) {
                    @Override
                    public Question parseQuestion(JSONObject o) {
                        String qs = o.getString("question");
                        Integer a = o.getInt("answer");
                        Question q = new Question(
                                new EqualityAnswerValidator<Integer>(a), null) {
                                };
                        System.out.println(qs);
                        System.out.println(a);
                        return q;
                    }
                };
            qg.getNextQuestion();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
