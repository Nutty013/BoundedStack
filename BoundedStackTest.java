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
        // capacity=0 → isEmpty() และ isFull() เป็นจริง ถ้า push/pop → exception
         /* Push */
        // push แล้ว pop ต้องได้ลำดับย้อนกลับ
        // push เป็น null → exception
        // push เมื่อเต็ม → exception
         /* Pop */
        // pop ต้องได้โต๊ะบนสุดและลดขนาดลง
        // pop เมื่อว่าง → exception
         /* Peek */
        // peek ไม่ได้ลบ
        // peek เมื่อว่าง → exception
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
    private static void testNegativeCapacity() {
        boolean threw = false;
        try {
            new BoundedStack(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("capacity cannot be negative", threw);
    }
}
