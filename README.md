# BoundedStack

BoundedStack คือโครงสร้างข้อมูลแบบ Stack ที่มีความจุจำกัด (Bounded Stack) โดยในที่นี้จำลองเป็น **กองโต๊ะว่างของร้านหมาล่า** ซึ่งใช้หลักการ **LIFO (Last In, First Out)** ซึ่งคือ โต๊ะที่เพิ่งเก็บเสร็จล่าสุดจะถูกนำไปใช้งานก่อน (มีความจุสูงสุดอยู่ที่ 50)

---

## ผู้จัดทำ

* ณัฐชา กาลัญญู — 6821600996
* อรพินทุ์ ศรีเผือด — 6821601631

---
## วิธีคอมไพล์และรัน

คอมไพล์โปรแกรม

```bash
javac BoundedStack.java BoundedStackTest.java
```

รันชุดทดสอบ

```bash
java -ea BoundedStackTest
```

> **หมายเหตุ:** ต้องใช้ `-ea` (Enable Assertions) เพื่อเปิดการทำงานของ `assert` มิฉะนั้น `checkRep()` จะไม่ถูกตรวจสอบระหว่างการรันโปรแกรม

---

## Design Document (D3)

Design Specification ของแต่ละ operation อยู่ในไฟล์ **BoundedStack** และ **Javadoc** ที่อยู่เหนือแต่ละ method ภายในไฟล์ `BoundedStack.java`
ภายในไฟล์ยังระบุ
* Abstraction Function (AF)
* Representation Invariant (RI)
* Safety from Representation Exposure

---

## Test Summary

ชุดทดสอบประกอบด้วย **21 test cases** ครอบคลุมหัวข้อสำคัญ ได้แก่

* Constructor
* Capacity validation
* push()
* pop()
* peek()
* clear()
* size()
* isEmpty()
* isFull()
* copy()
* Boundary cases (capacity = 0, 1, 50)
* Push/Pop สลับกัน
* Full / Almost Full
* Exception handling


## การตัดสินใจออกแบบ

* ใช้ `List<String>` เป็นตัวแทนข้อมูลภายในแทนการใช้ array เพื่อให้การเพิ่มและลบข้อมูลจากด้านบนของ stack ทำได้สะดวก
* Stack มีความจุสูงสุด (Bounded) โดยกำหนดตั้งแต่สร้าง object และไม่สามารถเปลี่ยนแปลงภายหลัง
* ใช้ `checkRep()` ตรวจสอบ Representation Invariant หลังการเปลี่ยนแปลง representation ทุกครั้ง
* ใช้แนวคิด **LIFO (Last In, First Out)** เพื่อจำลองกองโต๊ะว่างของร้านหมาล่า ซึ่งโต๊ะที่เก็บเสร็จล่าสุดจะถูกนำไปใช้งานก่อน
