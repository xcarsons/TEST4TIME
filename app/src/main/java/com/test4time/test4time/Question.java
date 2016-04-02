package com.test4time.test4time;

import java.util.Random;
/**
 * Question.java
 * Initially created on 1/29/2016.
 *
 * Class used to hold information about the current question.
 * This holds the answer, left parameter, right parameter, and operator symbol of the question.
 * It also contains methods to generate a new question (createMathQuestion, etc.)
 *
 */
public class Question {

    //TODO: add other types of questions (non-Math)

    //TODO: change to an enum?
    /** Current grade level of the current question
     * Possible values include: P, K, 1, 2, 3, etc. */
    static char LEVEL;

    /** The value displayed on the left of the operator symbol*/
    String left;
    /** The value displayed on the right of the operator symbol*/
    String right;
    /** The actual answer of the current Question. This is compared to the user's input*/
    String answer;
    /** The operator symbol for the current Question.
     * Possible values include: +, -, *, /  */
    String opSign;

    /** Initialize a new question without a starting question.
     * This can be used to create a base Question object,
     * then call the createQuestion functions, such as createMathQuestion(). */
    public Question() {

    }

    /** Initialize a new question with a starting grade level.
     * This can be used to create a base Question object,
     * then an appropriate question will be generated based on grade LEVEL. */
    public Question(char level) {
        nextMathQuestion(level);
    }

    /** Initialize a new question with a starting question.
     *  (leftNew opNew rightNew = answerNew) */
    public Question(String leftNew, String rightNew, String opNew, String answerNew) {
        left = leftNew;
        right = rightNew;
        opSign = opNew;
        answer = answerNew;
    }

    /** Should nextMathQuestion return a Question?
     *      > Question q = new Question();
     *      > q = nextMathQuestion(..)
     *  Or, will this be called by the created Question object?
     *      > Question q = new question();
     *      > q.nextMathQuestion(..)*/

    /** Set the current Question object's values to a new Math question based on grade LEVEL */
    public void nextMathQuestion(char LEVEL) {
        Random random = new Random();
        this.LEVEL = LEVEL;
        //TODO: add more grade levels
        //another idea: instead of completely random ranges,
        //   have higher grades pick between lower grades level (a small chance)?
        //also, the higher level problems (137+225=362) may be too difficult for mental math
        switch(LEVEL) {
            // grade P only have Addition problems
            case 'P':
                //NOTE: it may be more appropriate to give Preschoolers counting problems
                //      this could be like "How many ___ are there?"
                //      for now, they just get additions up to 5 + 5
                nextAddQuestion(LEVEL);
                break;
            // grade K have Addition and Subtraction problems
            case 'K':
                //randomly select between Addition and Subtraction
                if(random.nextInt(2) % 2 == 0) {
                    nextAddQuestion(LEVEL);
                } else {
                    nextSubQuestion(LEVEL);
                }
                break;
            // grade 1 have Addition and Subtraction problems
            case '1':
                if(random.nextInt(2) % 2== 0) {
                    nextAddQuestion(LEVEL);
                } else {
                    nextSubQuestion(LEVEL);
                }
                break;
            // grade 2 have Addition and Subtraction problems with 2 digits
            case '2':
                if(random.nextInt(2) % 2== 0) {
                    nextAddQuestion(LEVEL);
                } else {
                    nextSubQuestion(LEVEL);
                }
                break;
            // grade 3 have Addition and Subtraction problems with 3 digits
            case '3':
                if(random.nextInt(2) % 2== 0) {
                    nextAddQuestion(LEVEL);
                } else {
                    nextSubQuestion(LEVEL);
                }
                break;
            // otherwise, only do Addition problems
            default:
                nextAddQuestion(LEVEL);
        }
    }

    /** A method that returns a new value using base and multiplier
     * @param base The start value to use (2,3, or 5)
     * @param multiplier The value to multiplied with the to-be-calculated base value*/
    private int getMultiplierValue(int base, int multiplier) {
        switch(base) {
            //use a base value of 2 with multiplier
            case 0:
                return 2 * multiplier;
            //use a base value of 5 with multiplier
            case 1:
                //NOTE: THIS IS 5
                // 2 and 5 are better numbers than 2 and 3 for getting 'simple' numbers
                // so 5 is the next option
                return 5 * multiplier;
            //use a base value of 5 with multiplier
            case 2:
                return 3 * multiplier;
            //if not specified, use a base of 2
            default:
                return 2 * multiplier;
        }
    }

    /** Set the current Question values to a new Addition problem based on grade LEVEL */
    public void nextAddQuestion(char LEVEL) {
        Random random = new Random();
        int leftMax = 0, rightMax = 0;
        int leftVal = -1, rightVal = -1;
        boolean setValues = true;
        switch(LEVEL) {
            case 'P':
                //note, these MaxValues ARE included in the range
                // so max of 5 means values [0,1,2,3,4,5]
                leftMax = 5;
                rightMax = 5;
                break;
            case 'K':
                leftMax = 10;
                rightMax = 10;
                break;
            case '1':
                leftMax = 20;
                rightMax = 20;
                break;
            case '2':
                //make these numbers nicer/easier for mental math
                //  - maybe have it pick like multiples of 2,3 or 5?


                //turn off setValues, so the leftMax and rightMax values are used
                setValues = false;
                {
                    //get a number 0,1,or 2 to get a base value
                    //  where 0 sets a base of 2, 1 sets a base of 5, and 2 sets a base of 3
                    int leftBase = random.nextInt(2);
                    int rightBase = random.nextInt(2);
                    //get a number from 0-20 to multiply with base
                    int leftMultiplier = random.nextInt(21);
                    int rightMultiplier = random.nextInt(21);
                    leftMax = getMultiplierValue(leftBase, leftMultiplier);
                    rightMax = getMultiplierValue(rightBase, rightMultiplier);
                }
                //leftMax = 100;
                //rightMax = 100;
                break;
            case '3':
                //make these numbers nicer/easier for mental math
                //  - maybe have it pick like multiples of 2,3 or 5?

                //turn off setValues, so the leftMax and rightMax values are used
                setValues = false;
                {
                    //get a number 0,1,or 2 to get a base value
                    //  where 0 sets a base of 2, 1 sets a base of 5, and 2 sets a base of 3
                    int leftBase = random.nextInt(2);
                    int rightBase = random.nextInt(2);
                    //get a number from 0-60 to multiply with base
                    int leftMultiplier = random.nextInt(61);
                    int rightMultiplier = random.nextInt(61);
                    //maximum value is 5 * 60 = 300
                    leftMax = getMultiplierValue(leftBase, leftMultiplier);
                    rightMax = getMultiplierValue(rightBase, rightMultiplier);
                }

                //leftMax = 300;
                //rightMax = 300;
                break;
            default:
                //otherwise, just have a max of 3
                leftMax = 3;
                rightMax = 3;
                break;
        }

        //if setValues, then use the Max values to generate a new random number
        if (setValues) {
            leftVal = random.nextInt(leftMax + 1);
            rightVal = random.nextInt(rightMax + 1);
        } else {
            //otherwise, just set the new values to the max values
            leftVal = leftMax;
            rightVal = rightMax;
        }
        left = Integer.toString(leftVal);
        right = Integer.toString(rightVal);
        answer = Integer.toString(leftVal + rightVal);
        opSign = "+";
    }

    /** Set the current Question values to a new Subtraction problem based on grade LEVEL */
    public void nextSubQuestion(char LEVEL) {
        Random random = new Random();
        int leftMax, rightMax;
        int leftVal, rightVal;
        boolean setValues = true;
        switch(LEVEL) {
            case 'P':
                //note, these MaxValues ARE included in the range
                // so max of 5 means values [0,1,2,3,4,5]
                leftMax = 10;
                rightMax = 5;
                break;
            case 'K':
                leftMax = 20;
                rightMax = 10;
                break;
            case '1':
                leftMax = 30;
                rightMax = 20;
                break;
            case '2':
                //make these numbers nicer/easier for mental math
                //  - maybe have it pick like multiples of 2,3 or 5?

                setValues = false;
                {
                    //get a number 0,1,or 2 to get a base value
                    //  where 0 sets a base of 2, 1 sets a base of 5, and 2 sets a base of 3
                    int leftBase = random.nextInt(2);
                    int rightBase = random.nextInt(2);
                    //get a number from 0-20 to multiply with base
                    int leftMultiplier = random.nextInt(31);
                    int rightMultiplier = random.nextInt(21);
                    leftMax = getMultiplierValue(leftBase, leftMultiplier);
                    rightMax = getMultiplierValue(rightBase, rightMultiplier);
                }

                //leftMax = 150;
                //rightMax = 100;
                break;
            case '3':
                //make these numbers nicer/easier for mental math
                //  - maybe have it pick like multiples of 2,3 or 5?
                setValues = false;
                {
                    //get a number 0,1,or 2 to get a base value
                    //  where 0 sets a base of 2, 1 sets a base of 5, and 2 sets a base of 3
                    int leftBase = random.nextInt(2);
                    int rightBase = random.nextInt(2);
                    //get a number from 0-100 to multiply with base
                    int leftMultiplier = random.nextInt(101);
                    int rightMultiplier = random.nextInt(61);
                    leftMax = getMultiplierValue(leftBase, leftMultiplier);
                    rightMax = getMultiplierValue(rightBase, rightMultiplier);
                }
                //leftMax = 500;
                //rightMax = 300;
                break;
            default:
                //otherwise, just have a max of 3
                leftMax = 5;
                rightMax = 3;
                break;
        }

        if(setValues) {
            leftVal = random.nextInt(leftMax + 1);
            rightVal = random.nextInt(rightMax + 1);
        } else {
            leftVal = leftMax;
            rightVal = rightMax;
        }
        // if the left value is smaller than the right value, swap them so you don't get negatives
        if(leftVal < rightVal) {
            int temp = leftVal;
            leftVal = rightVal;
            rightVal = temp;
        }

        left = Integer.toString(leftVal);
        right = Integer.toString(rightVal);
        answer = Integer.toString(leftVal - rightVal);
        opSign = "-";
    }

    /** Set the current Question values to a new Multiplication problem based on grade LEVEL */
    public void nextMulQuestion(char LEVEL) {
        //TODO: multiplication questions
        // this will probably be 4th+ grade level
    }

    /** Set the current Question values to a new Division problem based on grade LEVEL */
    public void nextDivQuestion(char LEVEL) {
        //TODO: division questions
        // this will probable be 4th/5th+ grade level
    }

    /** --OLD VERSION - this method is no longer used--
     * Creation of Math questions all in one method based on LEVEL */
    public void create(int LEVEL) {
        //TODO: add more levels of questions
        int leftMax = 1;
        int rightMax = 1;
        int opMax = 1;
        Random random = new Random();
        switch (LEVEL) {
            case 0:
                leftMax = 10;
                rightMax = 10;
                opMax = 2;
                break;
            case 1:
                leftMax = 20;
                rightMax = 20;
                opMax = 2;
                break;
            case 2:
                break;
        }

        int leftNew = random.nextInt(leftMax);
        int rightNew = random.nextInt(rightMax);
        int ansNew = 0;
        int op = random.nextInt(opMax);
        switch (op) {
            case 0:
                ansNew = leftNew + rightNew;
                opSign = "+";

                left = Integer.toString(leftNew);
                right = Integer.toString(rightNew);
                break;
            case 1:
                if(leftNew > rightNew) {
                    ansNew = leftNew - rightNew;

                    left = Integer.toString(leftNew);
                    right = Integer.toString(rightNew);
                } else {
                    ansNew = rightNew - leftNew;

                    //swapped the operation, so swap left and right numbers
                    left = Integer.toString(rightNew);
                    right = Integer.toString(leftNew);
                }
                ansNew = (leftNew > rightNew) ? leftNew - rightNew : rightNew - leftNew;
                opSign = "-";
                break;
        }


        answer = Integer.toString(ansNew);

        System.out.println(LEVEL + ": " + left + opSign + right + " = " + answer);
    }

    /** The toString method for the Question class that returns a string in the following form:
     *      gradeLEVEL: left opSign right = answer
     */
    public String toString() {
        return (LEVEL + ": " + left + opSign + right + "=" + answer);
    }

    /** Main method used for testing the generation of new questions */
    public static void main(String[] args) throws InterruptedException {

        Question q = new Question();

        while(true) {
            q.nextMathQuestion('2');
            System.out.println(q);
            q.nextMathQuestion('3');
            System.out.println(q);
            System.out.println();
            Thread.sleep(2000);
        }

        /*
        while(true) {
            q.nextMathQuestion('P');
            System.out.println(q);
            q.nextMathQuestion('K');
            System.out.println(q);
            q.nextMathQuestion('1');
            System.out.println(q);
            q.nextMathQuestion('2');
            System.out.println(q);
            q.nextMathQuestion('3');
            System.out.println(q);
            System.out.println();
            Thread.sleep(4000);
        }
        */


        /* OLD testing of creating new questions
        for(int i = 0; i < 5; i++) {
            q.create(0);
            q.create(0);
            q.create(1);
            q.create(1);
        }
        */
    }
}
