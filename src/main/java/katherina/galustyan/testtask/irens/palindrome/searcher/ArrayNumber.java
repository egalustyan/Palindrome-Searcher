package katherina.galustyan.testtask.irens.palindrome.searcher;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by kate on 04.04.2019.
 */
public class ArrayNumber implements Cloneable, Comparable<ArrayNumber.OperationResult>, Serializable {
    public static short NORMAL_INIT = 1;
    public static short NOT_INIT = 0;
    public static short INIT_EMPTY_STRING = -1;
    public static short INIT_NOT_NUMBER = -2;

    private short[] arrayNumber;
    private short initResultType = NOT_INIT;
    private String wrongInitString = "";



    public ArrayNumber(String initValue){
        initNumber(initValue);
    }
    private ArrayNumber(short initResultType, short[] arrayNumber) {
        this.initResultType = initResultType;
        this.arrayNumber = Arrays.copyOf(arrayNumber, arrayNumber.length);
    }

    /*Check string if it contains only digits*/
    private boolean isWholeNumber(String value){
        return (value.matches("\\d+"));
    }

    /*Get array of digits from string. Cuts zero digits from the beginning of string */
    private short[] getArrayOfShort(String strNumber){
        int arrSize = strNumber.length();
        int startIndex = 0;
        for (int i = 0; (i < strNumber.length() - 1) && (strNumber.charAt(i)=='0'); i++ ){
            startIndex++;
            arrSize--;
        }
        short[] shortArr = new short[arrSize];
        for (int i = 0; i < arrSize ; i++){
            shortArr[i] = Short.parseShort(strNumber.substring(i + startIndex,i + startIndex + 1));
        }
        return shortArr;
    }

    /*Symmetrical copying left array half to right array half. Return new array of digits, that is palindrome.*/
    private short[] copyLeftHalfToRightHalf(short[] arrayNumber, boolean doCopy){
        int digitAmount = arrayNumber.length;

        short[] newNumber = (doCopy) ? Arrays.copyOf(arrayNumber,digitAmount )
                : arrayNumber;
        for (int i = 0; i < digitAmount/2; i++){
            newNumber[digitAmount - i - 1] = newNumber[i];
        }
        return newNumber;
    }

    /*Increment array length and move all digits to right by one position. Return new increased array*/
    private short[] addNewLeadingDigit(short[] arrNumber){
        int oldSize = arrNumber.length;
        short[] newArrNumber = new short[oldSize + 1];
        for (int i = 1; i < oldSize + 1; i++){
            newArrNumber[i] = arrNumber[i-1];
        }
        return newArrNumber;
    }

    /*Remove zero leading digits from array. Return new truncated array.*/
    private short[] removeZeroLeadingDigits(short[] arrNumber){
        int oldSize = arrNumber.length;
        if (oldSize == 1) {
            return arrNumber;
        }
        int startIndex = 0;

        for (int i = 0; (i < oldSize - 1) && (arrNumber[i] == 0); i++){
            startIndex++;
        }

        if (startIndex != 0){
            short[] newArrNumber = Arrays.copyOfRange(arrNumber, startIndex, oldSize);
            return  newArrNumber;
        }
        return arrNumber;
    }

    /*Check if array contains only zero digits */
    private boolean isZero(short[] arrNumber){
        for (int i = arrNumber.length - 1; i >= 0; i-- ){
            if (arrNumber[i]!= 0) {
                return false;
            }
        }
        return true;
    }

    /*Increment digit with digitIndex index  in array and, if it is required,
      change other leading digits by left side of incremented digit  */
    private OperationResult incDigitInNumber(short[] arrNumber, int digitIndex){
        int oldSize = arrNumber.length;
        int sum;
        short addNumb = 1;
        for (int i = digitIndex; (i >= 0) && (addNumb != 0); i-- ){
            sum = arrNumber[i] + addNumb;
            arrNumber[i] = (short)(sum % 10);
            addNumb = (short)(sum / 10);
        }
        if (addNumb != 0){
            if (oldSize == Integer.MAX_VALUE){
                return new OperationResult(arrNumber, true);
            }
            short[] newArrNumb = addNewLeadingDigit(arrNumber);
            newArrNumb[0] = addNumb;
            return  new OperationResult(newArrNumb, false);
        }
        return new OperationResult(arrNumber, false);
    }

    /*Decrement digit with digitIndex index  in array and, if it is required,
      change other leading digits by left side of decremented digit  */
    private OperationResult decDigitInNumber(short[] arrNumber, int digitIndex){
        if (isZero(arrNumber)){
            return new OperationResult(arrNumber, true);
        }
        int oldSize = arrNumber.length;
        int diff, decNumb = 1;
        for (int i = digitIndex; (i >= 0) && (decNumb != 0); i-- ){
            diff = arrNumber[i] - decNumb;
            if (diff < 0){
                decNumb = 1;
                arrNumber[i] =(short)(diff + 10);
            } else {
                decNumb = 0;
                arrNumber[i] =(short) diff;
            }
        }

        short[] newArrNumb = removeZeroLeadingDigits(arrNumber);
        return new OperationResult(newArrNumb, false);
    }

    /*Get index of central digit in array, if array length is odd number,
    *  or index of last digit in left array half, if array length isn't odd number*/
    private int getCentralDigitIndex(int digitAmount){
        return ((digitAmount % 2 != 0) ? digitAmount/2 : digitAmount/2 - 1);
    }

    /*----------------Public methods-------------------*/

    /*Init array number by its string representation. You can check init result by call isNormalInit method*/
    public short initNumber(String initValue){
        if (initValue != null){
            initValue = initValue.trim();
        }
        if (initValue == null || initValue.isEmpty()) {
            arrayNumber = null;
            initResultType = INIT_EMPTY_STRING;
            wrongInitString = "";
        } else if (isWholeNumber(initValue)){
            arrayNumber = getArrayOfShort(initValue);
            initResultType = NORMAL_INIT;
        } else{
            arrayNumber = new short[1];
            initResultType = INIT_NOT_NUMBER;
            wrongInitString = initValue;
        }
        return initResultType;
    }

    /*Check if array number was successfully initialized*/
    public boolean isNormalInit(){
        return (initResultType == NORMAL_INIT);
    }


     /*Check is current array number palindrome or not */
    public boolean isPalindrome(){
        if (!isNormalInit()){
            return false;
        }
        int arrSize = arrayNumber.length;
        for (int i = 0; i < arrSize / 2; i++){
            if (arrayNumber[i] != arrayNumber[arrSize - 1 - i]){
                return false;
            }
        }
        return true;
    }

    /*Increment central digit in arrayNumber and do symmetrical copying left array half to right array half.
    * For not odd length of array number, central digit is the last digit in left array half.
    * */
    public OperationResult getPalindromeWithIncCentralDigit(){
        OperationResult opResult = getPalindromeWithOperation(arrayNumber, this::incDigitInNumber);
        return opResult;

    }

    /*Decrement central digit in arrayNumber and do symmetrical copying left array half to right array half.
    * For not odd length of array number, central digit is the last digit in left array half.
    * */
    public OperationResult getPalindromeWithDecCentralDigit(){
        OperationResult opResult = getPalindromeWithOperation(arrayNumber, this::decDigitInNumber);
        return opResult;
    }

    /*Get nearest palindrome for current arrayNumber.
    * This is doing by symmetrical copying left array half to right array half.
    * */
    public OperationResult getNearestPalindrome(){
        int size = arrayNumber.length;
        short[] newArrayNumber = copyLeftHalfToRightHalf(arrayNumber, true);
        return new OperationResult(newArrayNumber, false);
    }

    /*Do some operation with central digit of array and do symmetrical copying left array half to right array half.
     * For not odd length of array number, central digit is the last digit in left array half.
      * */
    public OperationResult getPalindromeWithOperation(short[] arrayNumber, BiFunction<short[], Integer, OperationResult> funcOperation){
        int oldSize = arrayNumber.length;
        OperationResult opResult = funcOperation.apply(Arrays.copyOf(arrayNumber, oldSize),
                new Integer(getCentralDigitIndex(oldSize)));

        copyLeftHalfToRightHalf(opResult.getArrNumber(),false);
        return opResult;

    }

    /*Return string, that was used for arrayNumber error initialization.*/
    public String getWrongInitString(){
        return wrongInitString;
    }

    @Override
    public String toString() {
        if (!isNormalInit()){
            return "";
        }
        return arrToString(arrayNumber);
    }

    /*Convert array of digits to String*/
    public static String arrToString(short[] arrayNumber) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < arrayNumber.length; i++){
            result.append(arrayNumber[i]);
        }

        return (result.toString());
    }

    @Override
    public int compareTo(OperationResult operationResult) {
        short[] anotherArrNumber = operationResult.getArrNumber();
        int thisSize = this.arrayNumber.length;
        if (thisSize != anotherArrNumber.length){
            return (thisSize - anotherArrNumber.length);
        }

        for (int i = 0; i < thisSize; i++){
            if (this.arrayNumber[i] != anotherArrNumber[i]){
                return (this.arrayNumber[i] - anotherArrNumber[i]);
            }
        }

        return 0;
    }

    public boolean isLessThen(OperationResult anotherNumber) {
        return (this.compareTo(anotherNumber) < 0);
    }

    public class OperationResult{

        private short[] arrNumber;
        private boolean exitFromBound;

        public OperationResult(short[] arrNumber, boolean exitFromBound){
            this.arrNumber = arrNumber;
            this.exitFromBound = exitFromBound;
        }

        public short[] getArrNumber() {
            return arrNumber;
        }

        public boolean isExitFromBound() {
            return exitFromBound;
        }

        public String arrToString() {
            return ArrayNumber.arrToString(this.arrNumber);
        }
    }

    public ArrayNumber clone() {
        return new ArrayNumber(this.initResultType, this.arrayNumber);
    }
}
