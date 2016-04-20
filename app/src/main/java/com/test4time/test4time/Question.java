package com.test4time.test4time;

import java.util.ArrayList;
import java.util.LinkedList;
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
    char level;

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

    }

    /** Initialize a new question with a starting question.
     *  (leftNew opNew rightNew = answerNew) */
    public Question(String leftNew, String rightNew, String opNew, String answerNew, char level) {
        left = leftNew;
        right = rightNew;
        opSign = opNew;
        answer = answerNew;
        this.level = level;
    }

    /** Should nextMathQuestion return a Question?
     *      > Question q = new Question();
     *      > q = nextMathQuestion(..)
     *  Or, will this be called by the created Question object?
     *      > Question q = new question();
     *      > q.nextMathQuestion(..)*/

    public Question obsoletenextMathQuestion(char level) {
        Random random = new Random();
        this.level = level;
        String left = "-1";
        String right = "-1";
        String op = "+";
        String answer = "-1";

        //set values for left, right, op, and answer according to grade level

        switch(level) {
            case 'K':
                break;
            case '1':
                break;
            case '2':
                break;
            case '3':
                break;
            case '4':
                break;
            case '5':
                break;
            case '6':
                break;
        }

        Question q = new Question(left, right, op, answer, level);
        return null;
    }

    public static LinkedList<Question> generateQuestionPool(char level) {

        Random random = new Random();
        LinkedList<Question> questions;
        int index = 0;

        String leftNew, rightNew, ansNew;
        char opNew;
        int leftMax = 10, rightMax = 10;
        int leftVal, rightVal;

        switch(level) {
            /*
            K-  Addition and subtraction with 10 as the largest sum or addend.
            5 addition problems followed by 5 subtraction.
             */
            case 'K':
                //generate 10 questions
                questions  = new LinkedList<Question>();

                while(index < 10) {
                    leftVal = random.nextInt(11);
                    rightVal = random.nextInt(11);

                    //generate 5 addition problems first
                    if(index < 5) {
                        leftNew = Integer.toString(leftVal);
                        rightNew = Integer.toString(rightVal);
                        ansNew = Integer.toString(leftVal + rightVal);
                        //questions[index] = new Question(leftNew, rightNew, "+", ansNew, level);
                        questions.add(new Question(leftNew, rightNew, "+", ansNew, level));
                    } else {
                        //then generate 5 subtraction problems
                        //if left < right, swap the values
                        if(leftVal >= rightVal) {
                            leftNew = Integer.toString(leftVal);
                            rightNew = Integer.toString(rightVal);
                            ansNew = Integer.toString(leftVal - rightVal);
                        } else {
                            leftNew = Integer.toString(rightVal);
                            rightNew = Integer.toString(leftVal);
                            ansNew = Integer.toString(rightVal - leftVal);
                        }
                        //questions[index] = new Question(leftNew, rightNew, "-", ansNew, level);
                        questions.add(new Question(leftNew, rightNew, "-", ansNew, level));
                    }
                    index++;
                }
                //char newOp = ((random.nextInt(10) + 1 ) % 2 == 0) ? '+' : '-';
                break;
            /*
            1st grade-  Addition and subtraction, no carrying or borrowing.  99 is the top.
            Alternate + and -.  Have half of them have a sum 20 or less.  Every fourth problem do
            the opposite of the previous one.  So if the third problem is 6+3=9, then the fourth
            one should be 9-3=6 or 9-6=3.  I want to show how the two are connected.
            Every fifth problem have them add 3 digits (5+4+2) where the sum is 20 or less.
            */

            /*
                This is working for alternating Addition and subtraction

             */
            case '1':
                questions  = new LinkedList<Question>();

                int addOrS = random.nextInt();


                while (index < 20) {

                    if (index < 10) {
                        leftVal = random.nextInt(20);
                        if(leftVal == 20 && addOrS % 2 == 0) {
                            rightVal = 0;
                        } else {
                            int limit = 21 - leftVal;
                            rightVal = random.nextInt(limit);
                        }

                    } else {
                        leftVal = 1 + random.nextInt(100);
                        int limit = 100 - leftVal;
                        rightVal = random.nextInt(limit);
                    }
                    if (addOrS % 2 == 0) {
                        leftNew = Integer.toString(leftVal);
                        rightNew = Integer.toString(rightVal);
                        ansNew = Integer.toString(leftVal + rightVal);

                        questions.add(new Question(leftNew, rightNew, "+", ansNew, level));
                        //questions[index] = new Question(leftNew, rightNew, "+", ansNew, level);
                    } else {
                        if (addOrS % 2 != 0) {
                            rightVal = random.nextInt(leftVal);
                            //Swap if left bigger than right
                            if (leftVal >= rightVal) {
                                leftNew = Integer.toString(leftVal);
                                rightNew = Integer.toString(rightVal);
                                ansNew = Integer.toString(leftVal - rightVal);
                            } else {
                                leftNew = Integer.toString(rightVal);
                                rightNew = Integer.toString(leftVal);
                                ansNew = Integer.toString(rightVal - leftVal);
                            }
                            questions.add(new Question(leftNew, rightNew, "-", ansNew, level));
                            //questions[index] = new Question(leftNew, rightNew, "-", ansNew, level);
                        }
                    }
                    addOrS++;
                    index++;

                }


                break;

            /*2nd grade-  Same as 1st but we are going to include 3 digit problems (124+232).
                Carrying and borrowing only on two digit problems.  Again 99 is the top.
                Alternate + and -.  3 digit problem every 10th problem.
             */

            case '2':
                questions  = new LinkedList<Question>();

                int addOrSubtract = random.nextInt();


                while (index < 20) {

                    if (index < 8) {
                        leftVal = random.nextInt(20);
                        if(leftVal == 20 && addOrSubtract % 2 == 0) {
                            rightVal = 0;
                        } else {
                            int limit = 21 - leftVal;
                            rightVal = random.nextInt(limit);
                        }


                    } else {
                        if (index == 9 || index == 19) {
                            leftVal = 99 + (random.nextInt() * 1000);
                            int limit = 1000 - leftVal;
                            rightVal = random.nextInt(limit);

                        }else {
                            leftVal = 1 + random.nextInt(100);
                            int limit = 100 - leftVal;
                            rightVal = random.nextInt(limit);
                        }
                    }
                    if (addOrSubtract % 2 == 0) {
                        leftNew = Integer.toString(leftVal);
                        rightNew = Integer.toString(rightVal);
                        ansNew = Integer.toString(leftVal + rightVal);

                        questions.add(new Question(leftNew, rightNew, "+", ansNew, level));
                        //questions[index] = new Question(leftNew, rightNew, "+", ansNew, level);
                    } else {
                        if (addOrSubtract % 2 != 0) {
                            rightVal = random.nextInt(leftVal);
                            //Swap if left bigger than right
                            if (leftVal >= rightVal) {
                                leftNew = Integer.toString(leftVal);
                                rightNew = Integer.toString(rightVal);
                                ansNew = Integer.toString(leftVal - rightVal);
                            } else {
                                leftNew = Integer.toString(rightVal);
                                rightNew = Integer.toString(leftVal);
                                ansNew = Integer.toString(rightVal - leftVal);
                            }
                            questions.add(new Question(leftNew, rightNew, "-", ansNew, level));
                            //questions[index] = new Question(leftNew, rightNew, "-", ansNew, level);
                        }
                    }
                    addOrSubtract++;
                    index++;

                }
                break;


            /*
            3rd grade-  Times tables through 10.  Every 10th problem a 2 digit addition or
            subtraction problem.  Borrowing and carrying is fine.
             */
            case '3':
                questions  = new LinkedList<Question>();

                while (index < 10) {
                    leftVal = random.nextInt(11);
                    rightVal = random.nextInt(11);

                    if (index < 9) {
                        leftNew = Integer.toString(leftVal);
                        rightNew = Integer.toString(rightVal);
                        ansNew = Integer.toString(leftVal * rightVal);
                        questions.add(new Question(leftNew, rightNew, "x", ansNew, level));
                        //questions[index] = new Question(leftNew, rightNew, "x", ansNew, level);
                    } else {
                        int max = 99;
                        int min = 10;

                        //Generate random number to determine addition or subtraction
                        int addOrSub = random.nextInt(2);

                        //specify values that the addition/subtraction must be between (two digit)
                        leftVal = random.nextInt((max - min) + 1) + min;
                        rightVal = random.nextInt((max - min) + 1) + min;

                        if (addOrSub % 2 != 0) {
                            leftNew = Integer.toString(leftVal);
                            rightNew = Integer.toString(rightVal);
                            ansNew = Integer.toString(leftVal + rightVal);

                            questions.add(new Question(leftNew, rightNew, "+", ansNew, level));
                            //questions[index] = new Question(leftNew, rightNew, "+", ansNew, level);
                        } else {

                            //Swap if left bigger than right
                            if(leftVal >= rightVal) {
                                leftNew = Integer.toString(leftVal);
                                rightNew = Integer.toString(rightVal);
                                ansNew = Integer.toString(leftVal - rightVal);
                            } else {
                                leftNew = Integer.toString(rightVal);
                                rightNew = Integer.toString(leftVal);
                                ansNew = Integer.toString(rightVal - leftVal);
                            }
                            questions.add(new Question(leftNew, rightNew, "-", ansNew, level));
                            //questions[index] = new Question(leftNew, rightNew, "-", ansNew, level);
                        }

                    }
                    index ++;
                }
                break;
            /*
            Times tables through 12.  Every 4th problem a division problem that's the inverse of
            the previous multiplication problem (problem 3 is 6x3=18, problem 4 is 18 divided by 6
            or 18 divided by 3).  Every 10th problem a 2 digit addition or subtraction problem.
            Every 20th problem multiply 3 numbers with a product below 100 (7x3x2).
             */
            case '4':
                questions  = new LinkedList<Question>();

                index = 0;
                while(index < 10) {
                    if(index == 9) {
                        //make the 10th problem a 2 digit addition/subtraction problem
                        //TODO: copy over add/sub from 1st grade
                    } else if(index % 4 < 3) {
                        //times tables from 0..12
                        leftVal = random.nextInt(13);
                        rightVal = random.nextInt(13);
                        leftNew = Integer.toString(leftVal);
                        rightNew = Integer.toString(rightVal);
                        ansNew = Integer.toString(leftVal * rightVal);
                        questions.add(new Question(leftNew, rightNew, "x", ansNew, level));
                    } else {
                        // make the 4th problem inverse of 3rd
                        // 3rd: 6x3=18  becomes  4th: 18 / 6 = 3
                        Question third = questions.peekLast();
                        //check if previous ans was 0, to avoid (0 / 0)
                        // 0 x 6 = 0; 6 x 0 = 0; 0 x 0 = 0
                        if(third.right.equals("0")) {
                            //check if both left and right of previous Q were 0
                            if(third.left.equals("0")) {
                                //generate a new problem?
                                //for now, just give a consistent problem
                                leftNew = third.right;
                                rightNew = "4";
                                ansNew = third.answer;
                            } else {
                                //prev problem: non-zero * zero = 0
                                //new problem:  0 / non-zero = 0
                                leftNew = third.right;
                                rightNew = third.left;
                                ansNew = third.answer;
                            }
                        } else if(third.left.equals("0")) {
                            //prev problem: 0 * non-zero = 0
                            //new problem:  0 / non-zero = 0
                            leftNew = third.left;
                            rightNew = third.right;
                            ansNew = third.answer;
                        }
                        //otherwise, generate reverse of previous problem
                        else {
                            leftNew = third.answer;
                            rightNew = third.left;
                            ansNew = third.right;
                        }

                        // display the character for the division symbol
                        String testOpStr = "\u00F7";
                        //questions.add(new Question(leftNew, rightNew, "/", ansNew, level));
                        questions.add(new Question(leftNew, rightNew, testOpStr, ansNew, level));
                    }
                    index++;
                }
                break;
            /*
           Multiplication and division thru 12 alternating mult and div. First 6 random,
           second 6 alternate the mult with a division prob that's the inverse of the previous
           mult problem.  Second 6 example (6x4=24, 24/6=4, 9x3=27, 27/9=3, 8x7=56, 56/8=7).
           7th problem a simple fraction addition problem (same denominator, sum <1).  14th problem
           fraction multiplication (single digit 1/3 x 3/4=3/12).  21st problem fraction subtraction
           (same denominator 3/4-1/4=2/4).  Then repeat the cycle, 28th is frac addition,
           35th mult, 42nd subtraction.  If the answer box can't do a fraction then can we do mult
           choice?  Or maybe we can use the / bar in the answer box?
             */
            case '5':
                questions  = new LinkedList<Question>();
                //TODO: Implement 5th Grade Questions
                //TEMPORARILY GENERATE 4th GRADE QUESTIONS
                questions = generateQuestionPool('4');
                index = 0;
                break;
            case '6':
                questions  = new LinkedList<Question>();
                //TODO: Implement 6th Grade Questions
                // TEMPORARILY GENERATE 4th GRADE QUESTIONS
                questions = generateQuestionPool('4');
                break;
            default:
                // By default, generate Kindergarten questions (or the simplest questions)
                questions = generateQuestionPool('K');
                //questions = null;
                break;
        }


        return questions;
    }

    /** The toString method for the Question class that returns a string in the following form:
     *      gradeLEVEL: left opSign right = answer
     */
    public String toString() {
        return (level + ": " + left + opSign + right + "=" + answer);
    }

    /** Main method used for testing the generation of new questions */
    public static void main(String[] args) throws InterruptedException {

        Question q = new Question();

        System.out.println("Start to generate questions");

        LinkedList<Question> questions = generateQuestionPool('2');

        while(questions.size() > 0) {
            System.out.println(questions.remove());
            Thread.sleep(2000);
        }
        System.out.println(questions);
        System.out.println("Load more questions");

        /*
        while(true) {
            q.nextMathQuestion('2');
            System.out.println(q);
            q.nextMathQuestion('3');
            System.out.println(q);
            System.out.println();
            Thread.sleep(2000);
        }
        */
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
