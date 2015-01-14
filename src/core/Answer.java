package core;

public class Answer {
    Question q;
    Object ans;
    Boolean isRight = null;

    public Answer(Question q) {
        this.q = q;
    }

    public boolean validate() {
        if (isRight == null)
            this.isRight = this.q.validate(this.ans);
        return this.isRight;
    }

    public void setAnswer(Object ans) {
        this.ans = ans;
    }


}
