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

        BoundedStack emptyStack = new BoundedStack(50);
        BoundedStack stack = new BoundedStack(50);
        BoundedStack peekStack = new BoundedStack(50);

        //*
        // เขียน test
        // ถ้า capacity ติดลบ → exception
        // capacity=0 → isEmpty() และ isFull() เป็นจริง ถ้า push/pop → exception
        
         /* Push */
        // push แล้ว pop ต้องได้ลำดับย้อนกลับ
        // push เป็น null → exception
        try {
            stack.push(null);
            check("push null throws exception", false);
        } catch (IllegalArgumentException e) {
            check("push null throws exception", true);
        }

        // push ตรวจสอบจำนวนโต๊ะ
        for (int i = 1; i <= 50; i++) {
            stack.push("โต๊ะ" + i);
        }
        
        check("push 50 items makes stack full",
            stack.isFull());
        check("size after 50 push",
            stack.size() == 50);
        
        // push เมื่อเต็ม → exception
        try {
            stack.push("โต๊ะไม่พอ");
            check("push when full throws exception", false);
        } catch (IllegalStateException e) {
            check("push when full throws exception", true);
        }
        
         /* Pop */
        // pop ต้องได้โต๊ะบนสุดและลดขนาดลง
        boolean correct = true;
        
        for (int i = 50; i >= 1; i--) {
            int beforeSize = stack.size();
            String result = stack.pop();
        if (!result.equals("โต๊ะ" + i)) {
            correct = false;
            break;
            }
        if (stack.size() != beforeSize - 1) {
            correct = false;
            break;
            }
        }

        check("pop returns reverse order and decreases size",
            correct);

        check("stack empty after pop all",
            stack.isEmpty());

        check("size after pop all",
            stack.size() == 0);
        
        // pop เมื่อว่าง → exception
         try {
            emptyStack.pop();
            check("pop empty throws exception", false);
         } catch (IllegalStateException e) {
            check("pop empty throws exception", true);
         }
        
         /* Peek */
        // peek ไม่ได้ลบ
        peekStack.push("โต๊ะเก่า");
        peekStack.push("โต๊ะใหม่");
        int beforeSize = peekStack.size();
        
        check("peek gets top element",
            peekStack.peek().equals("โต๊ะใหม่"));

        check("peek does not remove",
            peekStack.size() == beforeSize);
        
        // peek เมื่อว่าง → exception
        try {
            emptyStack.peek();
            check("peek empty throws exception", false);
         } catch (IllegalStateException e) {
            check("peek empty throws exception", true);
         }
        
         /* Other เคสอื่น */
        // clear ต้องรีเซ็ตแต่ไม่เปลี่ยน capacity
        // isEmpty() และ isFull() ต้องเปลี่ยนแปลงตามสถานะ
        // capacity ไม่ควรเปลี่ยนแปลง
        /* Copy เคสสำเนาแยก */
        // copy ต้องเป็นอิสระจาก stack ต้นฉบับ
        // copy ต้องคงเดิมลำดับและสถานะว่าง
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
