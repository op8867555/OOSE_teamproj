package example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

import core.AnswerType;

 public class Answers extends AnswerType {

    ArrayList<Integer> as = new ArrayList<Integer>();
    static Scanner s = new Scanner(System.in);
    @Override
    public void addOption(Object o) {
        as.add((Integer) o);
    }

    public Integer prompt() throws IOException {
        System.out.println("options:");
        IntStream.range(0, this.as.size()).forEach(idx ->
                System.out.println(idx + ". " + as.get(idx)));
        int choice = s.nextInt();
        Integer ans = as.get(choice);
        as = new ArrayList<Integer>();
        return ans;
    }

    @Override
    public Object answer() {
        try {
            return prompt();
        } catch (Exception e){
            return 0;
        }
    }
}