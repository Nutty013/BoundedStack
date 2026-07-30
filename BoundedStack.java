import java.util.*;

/**
 * 6821600996 ณัฐชา กาลัญญู
 * 6821601631 อรพินทุ์ ศรีเผือด
 * BoundedStack คือ กองรวมโต๊ะว่างของร้านหมาล่า
 * (มี push คือ คืนโต๊ะที่เพิ่งเก็บเสร็จเข้ากอง, pop คือ จ่ายโต๊ะที่ว่างล่าสุดให้คิวถัดไป)
 */

public class BoundedStack {

    private final List<String> elements ;
    private final int capacity ;

    //Abstraction Function :
    // AF(elements,capacity) = 
    // ลำดับ [elements.get(0), ..., elements.get(size-1)]
    //   โดย elements.get(0)      คือชื่อโต๊ะที่อยู่ "ล่างสุด" ของกอง (เข้ากองก่อนสุด)
    //       elements.get(size-1) คือชื่อโต๊ะที่อยู่ "บนสุด" ของกอง (เข้าล่าสุด/จะถูก pop ก่อน)
    //   ถ้า elements ว่าง ให้แทนกองที่ไม่มีโต๊ะว่างอยู่เลยตอนนั้น

    //Representation Invariant :RI =
    //elements != null
    //capacity >= 0
    //0 <= elements.size() <= capacity
    //ไม่มีสมาชิกใดใน elements เป็น null
    
    //Safety from rep exposure:
    //field ทั้งสองเป็น private final, ไม่มี method ใดคืน reference ของ elements ออกไปตรง ๆ
    //checkRep() ตรวจ RI ทุกจุดที่ representation อาจเปลี่ยน (เรียกตอนต้น/ท้ายของทุก method)

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
