public class BoundedStackTest {

    private static int passed = 0;
    private static int failed = 0;

    /** helper กลาง — พิมพ์ PASS/FAIL และนับผลให้เอง */
    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + name);
        } else {
            failed++;
            System.out.println("[FAIL] " + name);
        }
    }

    public static void main(String[] args) {
        boolean assertsOn = false;
        assert assertsOn = true;
        if (!assertsOn) {
            System.out.println("WARNING: assertions disabled"
                    + " - re-run with: java -ea BoundedStackTest\n");
        }
        
        System.out.println("=== Test Suite ===\n");
        
        //*
        // เขียน test
        testNegativeCapacity();// ถ้า capacity ติดลบ → exception
        testZeroCapacityStartsEmptyAndFull();// capacity=0 → isEmpty() และ isFull() เป็นจริง ถ้า push/pop → exception
         /* Push */
        testPushIncreasesSizeAndIsLIFO();// push แล้ว pop ต้องได้ลำดับย้อนกลับ
        testPushNoNull();// push เป็น null → exception
        testPushTable();// push ตรวจสอบจำนวนโต๊ะ
        testPushRejectsWhenFull();// push เมื่อเต็ม → exception
         /* Pop */
        testPopReturnsTopAndShrinks();// pop ต้องได้โต๊ะบนสุดและลดขนาดลง
        testPopRejectsWhenEmpty();// pop เมื่อว่าง → exception
         /* Peek */
        testPeekDoesNotRemove();// peek ไม่ได้ลบ
        testPeekRejectsWhenEmpty();// peek เมื่อว่าง → exception  
         /* Other เคสอื่น */
        // clear ต้องรีเซ็ตแต่ไม่เปลี่ยน capacity
        // isEmpty() และ isFull() ต้องเปลี่ยนแปลงตามสถานะ
        // capacity ไม่ควรเปลี่ยนแปลง
        /* Copy เคสสำเนาแยก */
        testCopyIsFullyIndependent();// copy ต้องเป็นอิสระจาก stack ต้นฉบับ
        testCopyOrderAndEmptyCase();// copy ต้องคงเดิมลำดับและสถานะว่าง
        /* Boundary Cases เคสขอบเขต */
        // capacity=1
        // ทดสอบการรีฟิล (เต็ม-ว่าง-เต็ม)
        // ทดสอบการ push/pop แบบสลับกัน
        // ทดสอบสถานะใกล้เต็ม
        // */

        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");

        if (failed > 0) {
            System.exit(1);
        }
    }
    
    //* โค้ด test  */

    private static void testNegativeCapacity(){
        // ถ้า capacity ติดลบ → exception
        boolean threw = false;
        try {
            new BoundedStack(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("capacity cannot be negative", threw);
    }

    private static void testZeroCapacityStartsEmptyAndFull() {
        //capacity=0 → isEmpty() และ isFull() เป็นจริง ถ้า push/pop → exception
        BoundedStack s = new BoundedStack(0);
        check("capacity=0 -> both isEmpty and isFull", s.isEmpty() && s.isFull());
 
        boolean pushThrew = false;
        try {
            s.push("table1");
        } catch (IllegalStateException e) {
            pushThrew = true;
        }
        check("capacity=0 -> push throws IllegalStateException", pushThrew);
 
        boolean popThrew = false;
        try {
            s.pop();
        } catch (IllegalStateException e) {
            popThrew = true;
        }
        check("capacity=0 -> pop throws IllegalStateException", popThrew);
    }

    private static void testPushIncreasesSizeAndIsLIFO() {
        // push แล้ว pop ต้องได้ลำดับย้อนกลับ
        BoundedStack s = new BoundedStack(3);
        s.push("table1");
        s.push("table2");
        s.push("table3");
        check("push increases size and order is LIFO",
                s.size() == 3
                        && s.pop().equals("table3")
                        && s.pop().equals("table2")
                        && s.pop().equals("table1"));
    }

    private static void testPushNoNull() {
        // push เป็น null → exception
        BoundedStack s = new BoundedStack(50);
        boolean threw = false;
        try {
            s.push(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("push(null) -> IllegalArgumentException", threw);
        }
    
    private static void testPushTable() {
        // push ตรวจสอบจำนวนโต๊ะ
        BoundedStack s = new BoundedStack(50);
        for (int i = 1; i <= 50; i++) {
            s.push("โต๊ะ" + i);
        }
        
        check("push 50 items makes stack full",
            s.isFull());
        check("size after 50 push",
            s.size() == 50);
    }
    private static void testPushRejectsWhenFull() {
        // push เมื่อเต็ม → exception
        BoundedStack s = new BoundedStack(1);
        s.push("table1");
        boolean threw = false;
        try {
            s.push("table2");
        } catch (IllegalStateException e) {
            threw = true;
        }
        check("push when full -> IllegalStateException", threw);
    }
        
         /* Pop */
        private static void testPopReturnsTopAndShrinks() {
        // pop ต้องได้โต๊ะบนสุดและลดขนาดลง
        BoundedStack s = new BoundedStack(2);
        s.push("table10");
        check("pop when not empty -> returns top element and decreases size", s.pop().equals("table10") && s.size() == 0);
    }

        
        private static void testPopRejectsWhenEmpty() {
        // pop เมื่อว่าง → exception
        BoundedStack s = new BoundedStack(2);
        boolean threw = false;
        try {
            s.pop();
        } catch (IllegalStateException e) {
            threw = true;
        }
        check("pop when empty -> IllegalStateException", threw);
    }
        
         /* Peek */
        private static void testPeekDoesNotRemove() {
        // peek ไม่ได้ลบ
        BoundedStack s = new BoundedStack(3);
        s.push("table1");
        s.push("table2");
        check("peek when not empty -> returns top element without decreasing size", s.peek().equals("table2") && s.size() == 2);
    }
        private static void testPeekRejectsWhenEmpty() {
        // peek เมื่อว่าง → exception
        BoundedStack s = new BoundedStack(1);
        boolean threw = false;
        try {
            s.peek();
        } catch (IllegalStateException e) {
            threw = true;
        }
        check("peek when empty -> IllegalStateException", threw);
    }
        /* Copy */
    private static void testCopyIsFullyIndependent() {
        // copy ต้องเป็นอิสระจาก stack ต้นฉบับ
        BoundedStack original = new BoundedStack(3);
        original.push("table1");
        BoundedStack clone = original.copy();
        clone.push("table2");
        original.push("table9");
        check("copy is independent (original and copy can be modified separately)",
                original.size() == 2 && clone.size() == 2 && clone.peek().equals("table2"));
    }
 
    private static void testCopyOrderAndEmptyCase() {
        // copy ต้องคงเดิมลำดับและสถานะว่าง
        BoundedStack original = new BoundedStack(3);
        original.push("table7");
        original.push("table8");
        BoundedStack clone = original.copy();
        boolean ok = clone.pop().equals("table8") && clone.pop().equals("table7");
        BoundedStack emptyClone = new BoundedStack(3).copy();
        check("copy preserves order and can create an empty copy", ok && emptyClone.isEmpty());
    }
}
