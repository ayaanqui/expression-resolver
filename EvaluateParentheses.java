import java.util.ArrayList;
import java.util.Map;

public class EvaluateParentheses {
    private ArrayList<String> formattedUserInput;
    private String solvedInnerExpression;

    public EvaluateParentheses() {
    }

    public EvaluateParentheses(ArrayList<String> formattedUserInput) {
        this.formattedUserInput = formattedUserInput;
    }

    public void condense(int start) {
        Map<Integer, Integer> relatedParentheses = new RelatedParentheses(formattedUserInput).evaluateRelations();

        int end = relatedParentheses.get(start);

        String innerExpression = "";

        // Populate innerExpression with all of the elemnts inside the parentheses
        for (int t = start + 1; t < end; t++) {
            innerExpression += formattedUserInput.get(t);
        }

        Calculator newExpression = new Calculator();
        newExpression.setUp(innerExpression);
        solvedInnerExpression = newExpression.solveExpression() + "";

        // Removing elements from the back, to avoid IndexOutOfBounds Errors
        for (int t = end; t > start; t--) {
            formattedUserInput.remove(t);
        }
        formattedUserInput.set(start, solvedInnerExpression);
    }

    public ArrayList<String> getFormattedUserInput() {
        return formattedUserInput;
    }

    public String getSolvedInnerExpression() {
        return solvedInnerExpression;
    }
}