import javax.swing.*;

public class Calculator {
    private char operator;
    private int operand1;
    private int operand2;
    private String expression;
    private boolean isValid = false;
    private int x;
    int answer;
    String[] splited;
    public Calculator() {
    }

    public char getOperator() {
        return operator;
    }

    public int getOperand1() {

        return operand1;
    }

    public int getOperand2() {
        return operand2;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;

    }
    public boolean verify(){
        splited = expression.split(" ");
        if(splited.length == 3){
            if((Integer.valueOf(splited[0])) instanceof Integer){
                if((Integer.valueOf(splited[2])) instanceof Integer) {
                    operand1 = Integer.parseInt(splited[0]);
                    operand2 = Integer.parseInt(splited[2]);
                    try{
                        x = (Integer.parseInt(splited[1]));
                        isValid = false;
                    }
                    catch(Exception ex){
                        if (splited[1].charAt(0)=='/' && getOperand2() == 0){
                            isValid = false;
                        } else {
                            operator = splited[1].charAt(0);
                            isValid = true;

                        }
                    }
                }
            }
        } else {
            isValid = false;
        }
        return isValid;
    }
    public int evaluate(){
        if(getOperator() == '*'){
            answer = getOperand1()*getOperand2();
        }
        if(getOperator() == '/'){
            answer = getOperand1()/getOperand2();
        }
        if(getOperator() == '+'){
            answer = getOperand1()+getOperand2();
        }
        if(getOperator() == '-'){
            answer = getOperand1()-getOperand2();
        }
        return answer;
    }
}