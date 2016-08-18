/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.LinkedList;

/**
 * Used to create an array of tokens from a string.
 * Flow:
 *      remove Spaces -> count words ->
 * @author Jack
 */
public class TokenMachine {
    
    public static String[] tokenize(String message){
        
      char[] charArray = message.toCharArray();
      charArray = deleteLeadingSpaces(charArray);
      charArray = deleteTrailingSpaces(charArray);
      int tokens = countTokens(charArray);
      return fillTokenArray(tokens, charArray); 
    }
    
    private static String charArrayToString(char[] arr){
        String text = "";
        for (int i = 0; i < arr.length; i++) {
            text += arr[i];
        }
        return text;
    }
    
    private static String[] fillTokenArray(int tokens, char[] charArray){
        String[] tokenArr = new String[tokens];
        // Loop through tokens.
        int j = 0;
        for (int i = 0; i < tokens; i++) {
            String text = "";
            while(j < charArray.length) {
                while(isValidTokenChar(charArray[j])){
                     text += charArray[j];
                     j++;
                     if(j == charArray.length) break;
                }
                if(text.length() > 0){
                    tokenArr[i] = text; 
                }
                j++;
                break;
            }
        }
        return tokenArr;
    }
    /**
     * Determines if a given char is a valid character to be in a word.
     * @param c a character to be determined if it is a break in tokens.
     * @return 
     */
    private static boolean isValidTokenChar(char c){
        return !((c == '\n') || (c == ' '));
    }    
    /**
     * Count the number of words in given input.
     * @param charArray 
     * @return Returns the number of tokens in a given string.
     */
    private static int countTokens(char[] charArray){
      
        int wordCount = 0;
        if (charArray.length > 0) wordCount = 1;
         
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == ' ' && charArray[i+1] != ' ') wordCount++;           
        }       
        return wordCount;
    }
    /**
     * Creates char array from a given string
     * @param text
     * @return text as a char array
     */
    private static char[] createCharArray(String text){       
        char[] charArray = text.toCharArray();
        return charArray;
    }
    /**
     * Takes a string as a char array and removes leading spaces.
     * @param array Takes text as a char array
     * @return array without leading spaces.
     */
    private static char[] deleteLeadingSpaces(char[] array){        
        LinkedList<Character> list = linkedListFromArray(array);
        for (int i = 0; i < list.size(); i++) {
            if(list.getFirst() == ' '){
                list.removeFirst();
            }           
            else {           
                return listToCharArray(list);
            }
        }       
        return listToCharArray(list);
    }
    /**
     * Converts list<Strring> to a char array.
     * @param list
     * @return 
     */
    private static char[] listToCharArray(LinkedList list){
        char[] array = new char[list.size()];
        
        
        for (int i = 0; i < array.length; i++) {
            array[i] = (char)list.get(i);
        }
        return array;
    }
    /**
     * Takes a string as a char array and removes trailing spaces.
     * @param array: Takes text as a char array
     * @return  array without trailing spaces.
     */
    private static char[] deleteTrailingSpaces(char[] array){
        
        LinkedList<Character> list = linkedListFromArray(array);
        int length = list.size();
        for (int i = 0; i < length; i++) {
            if(list.getLast()== ' '){
                list.removeLast();
            }
            else return listToCharArray(list);
        }
        
        return listToCharArray(list);
    }
    /**
     * Converts array to linkedlist
     * @param array
     * @return array as a linked list
     */
    private static LinkedList linkedListFromArray(char[] array){
        
        LinkedList<Character> list = new LinkedList<>();
        
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        } 
        return list;
    }
}
