import java.util.*;

/**
 * 6821600996 ณัฐชา กาลัญญู
 * 6821601631 อรพินทุ์ ศรีเผือด
 * BoundedStack คือ กองรวมโต๊ะว่างของร้านหมาล่า
 * (มี push คือ คืนโต๊ะที่เพิ่งเก็บเสร็จเข้ากอง, pop คือ จ่ายโต๊ะที่ว่างล่าสุดให้คิวถัดไป)
 */

public class BoundedStack {

    //Abstraction Function :
    // AF(elements,capacity) = 
    // ลำดับ [elements.get(0), ..., elements.get(size-1)]
    //   โดย elements.get(0)      คือชื่อโต๊ะที่อยู่ "ล่างสุด" ของกอง (เข้ากองก่อนสุด)
    //       elements.get(size-1) คือชื่อโต๊ะที่อยู่ "บนสุด" ของกอง (เข้าล่าสุด/จะถูก pop ก่อน)
    //   ถ้า elements ว่าง ให้แทนกองที่ไม่มีโต๊ะว่างอยู่เลยตอนนั้น

    //Representation Invariant :RI =
    //elements != null
    //0 <= capacity <= 50
    //0 <= elements.size() <= capacity
    //ไม่มีสมาชิกใดใน elements เป็น null
    
    //Safety from rep exposure:
    //field ทั้งสองเป็น private final, ไม่มี method ใดคืน reference ของ elements ออกไปตรง ๆ
    //checkRep() ตรวจ RI ทุกจุดที่ representation อาจเปลี่ยน (เรียกตอนท้ายของทุก method)

    private final List<String> elements ;
    private final int capacity ;

    /**
     * สร้างกองเปล่าที่มีความจุสูงสุดตามที่กำหนดในตอนสร้าง
     * @param capacity คือ ความจุสูงสุดของกอง
     * @throws IllegalArgumentException ถ้า capacity < 0 หรือ capacity > 50
     */
    public BoundedStack(int capacity){
        if (capacity < 0 || capacity > 50) {
            throw new IllegalArgumentException("capacity out of bounds -> IllegalArgumentException");
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
            throw new IllegalArgumentException("table != null");
        }
        if (isFull()) {
            throw new IllegalStateException("the stack is full");
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
                throw new IllegalStateException("the stack is empty");
                }
                String s = elements.remove(elements.size() - 1);
                checkRep();
            return s;
    }

    /**
     * ดูชื่อโต๊ะบนสุดของกองโดยไม่นำออก
     * @return ชื่อโต๊ะบนสุด
     * @throws IllegalStateException ถ้ากองว่าง
     */
    public String peek() {
        if (elements.isEmpty()) {
        throw new IllegalStateException("the stack is empty");
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

    /**
     * สร้าง copy ของกองนี้
     * @return กองใหม่ที่มีโต๊ะว่างเหมือนกับกองนี้
     */
    public BoundedStack copy() {
        checkRep();
        BoundedStack copy = new BoundedStack(this.capacity);
        copy.elements.addAll(this.elements);
        copy.checkRep();
        return copy;
    }

    /** checkRep */
    private void checkRep() {
        assert elements != null : "elements not null";
        assert capacity >= 0 && capacity <= 50 : "capacity out of bounds";
        assert 0 <= elements.size() && elements.size() <= capacity : "elements size < capacity";
        for (String s : elements) {
            assert s != null : "elements not null";
        }
    }
}
