package questionGenerator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import core.Question;
import core.QuestionGenerator;

public class RandonizedQuestionGenerator extends QuestionGenerator {

    List<Question> qs = new LinkedList<Question>();

    public RandonizedQuestionGenerator(QuestionGenerator qg) {

        for (Question q = qg.getNextQuestion();
               q != null;
               q = qg.getNextQuestion())
            qs.add(q);
        Collections.shuffle(qs);
    }

    @Override
    public Question getNextQuestion() {
        @SuppressWarnings("unchecked")
        Queue<Question> q = (Queue<Question>) qs;
        return q.poll();
    }

}
