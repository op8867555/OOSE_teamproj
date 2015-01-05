package core;

public abstract class Question {
    protected AnswerValidator av;
    protected AnswerType at;
    protected Object ans;

    public Question(AnswerValidator av, AnswerType at) {
        this.av = av;
        this.at = at;
    }

    public boolean validate() {
        return this.av.validate(this.ans);
    }

   public void setAnswer(Object ans) {
        this.ans = ans;
   }

   public void answer() {
       this.ans = at.answer();
   }
}
