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
     * สร้างกองเปล่าที่มีความจุสูงสุดตามที่กำหนดในตอนสร้าง
     * @param capacity คือ ความจุสูงสุดของกอง
     * @throws IllegalArgumentException ถ้า capacity < 0
     */
    public BoundedStack(int capacity){
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity ห้ามติดลบ: " + capacity);
        }
        this.elements = new ArrayList<>();
        this.capacity = capacity;
        checkRep();
    }

    /**
     * เพิ่มโต๊ะใหม่เข้ากอง
     * @param s ชื่อโต๊ะที่จะเพิ่ม
     * @throws IllegalArgumentException ถ้า s เป็น null
     * @throws IllegalStateException ถ้ากองเต็ม
     */
    public void push(String s){
        if (s == null) {
            throw new IllegalArgumentException("โต๊ะห้ามเป็น null");
        }
        if (isFull()) {
            throw new IllegalStateException("กองเต็ม");
        }
        elements.add(s);
        checkRep();
    }
    /**
     * เอาโต๊ะที่ว่างล่าสุดออกจากกอง
     * @return ชื่อโต๊ะที่ว่างล่าสุด
     * @throws IllegalStateException ถ้ากองว่าง
     */
    public String pop() {
        if (elements.isEmpty()) {
        throw new IllegalStateException("กองว่าง");
        }
        String s = elements.remove(elements.size() - 1);
        checkRep();
        return s;
    }

    /**
     * ดูว่ากองเต็มหรือไม่โดยไม่เอาโต๊ะออก
     * @return true ถ้ากองเต็ม, false ถ้าไม่เต็ม
     * @throws IllegalStateException ถ้ากองว่าง
     */
    public String peek() {
        if (elements.isEmpty()) {
        throw new IllegalStateException("กองว่าง");
        }
        return elements.get(elements.size() - 1);
    }

    /** ล้างกอง */
    public void clear() {
        elements.clear();
        checkRep();
    }

    /** เช็คจำนวนโต๊ะว่าง */
    public int size() {
        return elements.size();
    }

    /** เช็คความจุของกอง */
    public int capacity() {
        return capacity;
    }

    /** ตรวจสอบว่ากองว่างหรือไม่ */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /** ตรวจสอบกองเต็มหรือไม่ */
    public boolean isFull() {
        return size() == capacity();
    }

    /** checkRep */
    private void checkRep() {
        assert elements != null : "elements ต้องไม่เป็น null";
        assert capacity >= 0 : "capacity ต้องไม่ติดลบ";
        assert 0 <= elements.size() && elements.size() <= capacity : "จำนวนโต๊ะว่างต้องไม่เกิน capacity";
        for (String s : elements) {
            assert s != null : "โต๊ะว่างแต่ละตัวต้องไม่เป็น null";
        }
    }
}
