import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Stack;

public class RelatedParentheses {
    private ArrayList<String> userInpList = new ArrayList<String>();
    
    public RelatedParentheses() {}

    public RelatedParentheses(ArrayList<String> userInpList) {
        this.userInpList = userInpList;
    }

    public Map<Integer, Integer> evaluateRelations() {
        Stack<Integer> openingParenthesis = new Stack<>();
        Map<Integer, Integer> relationships = new TreeMap<Integer, Integer>();
        
        for (int i = 0; i < userInpList.size(); i++) {
            if (userInpList.get(i).equals("(")) {
                openingParenthesis.push(i);
            } else if (userInpList.get(i).equals(")") && openingParenthesis.size() > 0) {
                relationships.put(
                    openingParenthesis.peek(), i
                );
                openingParenthesis.pop();
            }
        }
        return relationships;
    }
}