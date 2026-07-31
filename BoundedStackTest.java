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
        testCapacityAboveMax();// capacity > 50 → exception
        testCapacityExactlyMax();// capacity = 50
        testZeroCapacityStartsEmptyAndFull();// capacity=0 เมื่อว่างและเต็มจะเป็นจริง ถ้า push/pop → exception
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
        testClear();// clear ต้องรีเซ็ตแต่ไม่เปลี่ยน capacity
        testEmptyFullStateChanges();// isEmpty() และ isFull() ต้องเปลี่ยนแปลงตามสถานะ
        testCapacityNeverChanges();// capacity ไม่ควรเปลี่ยนแปลง
        /* Copy เคสสำเนาแยก */
        testCopyIsFullyIndependent();// copy ต้องเป็นอิสระจาก stack ต้นฉบับ
        testCopyOrderAndEmptyCase();// copy ต้องคงเดิมลำดับและสถานะว่าง
        /* Boundary Cases เคสขอบเขต */
        testCapacityOne();// capacity=1
        testCapacityOneRefill();// ทดสอบการรีฟิล (เต็ม-ว่าง-เต็ม)
        testPushAndPop();// ทดสอบการ push/pop แบบสลับกัน
        testAlmostFull();// ทดสอบสถานะใกล้เต็ม
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

   private static void testCapacityAboveMax() {
        // capacity > 50 → exception
        boolean threw = false;
        try {
            new BoundedStack(51);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("capacity=51 (above 50) -> IllegalArgumentException", threw);
    }

    private static void testCapacityExactlyMax() {
        // capacity = 50 
        BoundedStack s = new BoundedStack(50);
        check("capacity=50 (at max) -> pass", s.capacity() == 50 && s.isEmpty());
    }

    private static void testZeroCapacityStartsEmptyAndFull() {
        //capacity=0 → เมื่อว่างและเต็มจะเป็นจริง ถ้า push/pop → exception
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

    /* อื่นๆ */

        /* Clear */
    private static void testClear() {
        // clear ต้องรีเซ็ตแต่ไม่เปลี่ยน capacity
        BoundedStack s = new BoundedStack(5);
        s.push("table1");
        s.push("table2");
        int oldCapacity = s.capacity();
        s.clear();
        check("clear resets stack but keeps capacity",
            s.isEmpty()
            && s.size() == 0
            && s.capacity() == oldCapacity);
    }

        /* isEmpty&isFull */
    private static void testEmptyFullStateChanges() {
        // isEmpty() และ isFull() ต้องเปลี่ยนแปลงตามสถานะ
        BoundedStack s = new BoundedStack(2);
        boolean start = s.isEmpty() && !s.isFull();
        s.push("table1");
        boolean middle = !s.isEmpty() && !s.isFull();
        s.push("table2");
        boolean full = !s.isEmpty() && s.isFull();
        s.pop();
        boolean afterPop = !s.isEmpty() && !s.isFull();
        check("isEmpty and isFull change correctly",
            start && middle && full && afterPop);
    }

        /* Capacity */
    private static void testCapacityNeverChanges() {
        // capacity ไม่ควรเปลี่ยนแปลง
        BoundedStack s = new BoundedStack(10);
        int c1 = s.capacity();
        s.push("A");
        s.push("B");
        s.pop();
        s.clear();
        int c2 = s.capacity();
        check("capacity remains unchanged",
            c1 == c2);
    }

    /* Boundary Cases */

        /* Capacity=1 */
    private static void testCapacityOne() {
        // capacity=1
        BoundedStack s = new BoundedStack(1);
        boolean startEmpty = s.isEmpty() && !s.isFull();
        s.push("table1");
        boolean afterPush = s.size() == 1 && s.isFull();
        boolean threw = false;
        try {
            s.push("table2");
        } catch (IllegalStateException e) {
            threw = true;
        }
        check("capacity=1 works correctly",
            startEmpty
            && afterPush
            && threw);
    }

        /* รีฟิล */
    private static void testCapacityOneRefill() {
        // ทดสอบการรีฟิล (เต็ม-ว่าง-เต็ม)
        BoundedStack s = new BoundedStack(1);
        boolean start = s.isEmpty();
        s.push("table1");
        boolean full = s.isFull();
        s.pop();
        boolean empty = s.isEmpty();
        s.push("table2");
        boolean fullAgain = s.isFull() && s.peek().equals("table2");
        check("capacity=1 refill full-empty-full",
            start && full && empty && fullAgain);
    }

    private static void testPushAndPop() {
        // ทดสอบการ push/pop แบบสลับกัน
        BoundedStack s = new BoundedStack(3);
        s.push("table1");
        s.push("table2");
        s.pop();
        s.push("table3");
        s.push("table4");
        check("swapping push/pop remains correct order and size",
                s.size() == 3
                        && s.pop().equals("table4")
                        && s.pop().equals("table3")
                        && s.pop().equals("table1"));
    }
    private static void testAlmostFull() {
            // ทดสอบสถานะใกล้เต็ม
            BoundedStack s = new BoundedStack(3);
            s.push("table1");
            s.push("table2");
            check("size almost full", s.size() == 2 && !s.isFull());
            s.push("table3");
            check("stack is full", s.isFull());
            check("full stack size is correct",
                s.size() == 3);
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
