import java.util.*;

/**
 * 6821600996 ณัฐชา กาลัญญู
 * 6821601631 อรพินทุ์ ศรีเผือด
 * BoundedStack คือ...(สิ่งที่เราจะทำกัน)
 */

public class BoundedStack {

    private final List<String> elements ;
    private final int capacity ;

    //Abstraction Function :AF(elements,capacity) = 
    //Representation Invariant :RI
    //Safety from rep exposure:
    //checkRep

    /**
     * 
     * @param capacity คือ
     */
    public BoundedStack(int capacity){
        elements = new ArrayList<>();
        this.capacity = capacity ;
    }

    /**
     * 
     * @param s คือ
     * 
     */
    public void push(String s){

    }








}
