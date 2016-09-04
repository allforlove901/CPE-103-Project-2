/**
 * JUnit acceptance tests for BinHeap. Adapted from Paul Hatalsky's
 * CPE 103 Program 3.
 *
 * @author Christopher Siu
 * @version 2/4/2016 Developed for Theresa Migler's CPE 103 Program 2.
 */
import static org.junit.Assert.*;
import org.junit.*;
import java.lang.reflect.*;
import java.util.Random;
import org.junit.runners.MethodSorters;
import org.junit.rules.*;
import org.junit.runner.Description;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.util.Scanner;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BinHeapAcceptanceTests
{
   @Rule
   public TestRule watcher = new TestWatcher() {
      protected void starting(Description description) {
         //System.out.print("Starting: " + description.getMethodName() + "...");
      }
   };
   @Rule
   public Stopwatch sw = new Stopwatch() {
      protected void finished(long nanos, Description description) {
         //System.out.println(" (" + runtime(TimeUnit.MILLISECONDS) + " ms)");
      }
      protected void succeeded(long nanos, Description description) {
         //System.out.print("Passed");
      }
      protected void failed(long nanos, Throwable e, Description description) {
         //System.out.print("Failed");
      }
   };

   @Test
   public void test01_verifySuperClass()
   {
      assertTrue(BinHeap.class.getSuperclass() == Object.class);
   }

   @Test
   public void test02_verifyFields()
   {
      int arrayCount = 0;
      int intCount = 0;
      Field[] fields = BinHeap.class.getDeclaredFields();

      assertTrue(fields.length == 2);

      for (int i = 0; i < fields.length; i++)
      {
         assertTrue(Modifier.isPrivate(fields[i].getModifiers()));

         if (fields[i].getType().isArray())
         {
            arrayCount++;
         }
         else if (fields[i].getType() == int.class)
         {
            intCount++;
         }
      }

      assertTrue(arrayCount == 1);
      assertTrue(intCount == 1);
   }

   @Test
   public void test03_verifyConstructors()
   {
      int count = 0;
      Constructor[] cons = BinHeap.class.getDeclaredConstructors();

      assertTrue(cons.length == 2);

      assertTrue(Modifier.isPublic(cons[0].getModifiers()));
      assertTrue(Modifier.isPublic(cons[1].getModifiers()));

      if (cons[0].getParameterTypes().length == 0)
      {
         assertEquals(1, cons[1].getParameterTypes().length);
      }
      else
      {
         assertEquals(0, cons[1].getParameterTypes().length);
      }
   }

   @Test
   public void test04_verifyMethods()
   {
      int countPublic = 0;
      int countProtected = 0;
      int countPackage = 0;
      int countPrivate = 0;

      Method[] methods = BinHeap.class.getDeclaredMethods();

      for (Method m : methods)
      {
         if (Modifier.isPublic(m.getModifiers()))
         {
            countPublic++;
         }
         else if (Modifier.isProtected(m.getModifiers()))
         {
            countProtected++;
         }
         else if (Modifier.isPrivate(m.getModifiers()))
         {
            countPrivate++;
         }
         else
         {
            countPackage++;
         }
      }

      assertTrue(countPublic == 5);
      assertTrue(countProtected == 0);
      assertTrue(countPackage == 0);
   }

   @Test
   public void test05_verifySubclass()
   {
      Class[] subclasses = BinHeap.class.getClasses();
      int numClasses = 0;

      for (Class sc : subclasses) 
      {
         if (sc.toString().contains("BinHeap$"))
         {
            numClasses++;
            assertTrue(Modifier.isPublic(sc.getModifiers()));
            assertTrue(Modifier.isStatic(sc.getModifiers()));
         }
      }
      assertEquals(1, numClasses);
   }

   @Test
   public void test06_InitialValues()
   {
      BinHeap<Integer> q = new BinHeap<Integer>();
      assertTrue(q.size() == 0);
      assertEquals("", q.toString().replace(" ", ""));
      assertTrue(q.isEmpty());

      q = new BinHeap<Integer>(94);
      assertTrue(q.size() == 0);
      assertEquals("", q.toString().replace(" ", ""));
      assertTrue(q.isEmpty());
   }

   @Test(expected = BinHeap.MyException.class)
   public void test07_dequeueAtConstruction1()
   {
      BinHeap<String> q = new BinHeap<String>();
      q.deleteMin();
   }

   @Test(expected = BinHeap.MyException.class)
   public void test08_dequeueAtConstruction2()
   {
      BinHeap<String> q = new BinHeap<String>(327);
      q.deleteMin();
   }

   @Test
   public void test09_enqueueDequeueSimple1()
   {
      BinHeap<Integer> q = new BinHeap<Integer>();
      q.insert(0);
      assertEquals(1, q.size());
      assertFalse(q.isEmpty());
      assertEquals(new Integer(0), q.deleteMin());
      assertTrue(q.isEmpty());
   }

   @Test
   public void test10_enqueueDequeueSimple2()
   {
      BinHeap<String> q = new BinHeap<String>();
      q.insert("Hello");
      assertEquals(1, q.size());
      assertFalse(q.isEmpty());
      assertEquals("Hello", q.deleteMin());
      assertTrue(q.isEmpty());
   }

   @Test
   public void test11_enqueueDequeueSimple3()
   {
      BinHeap<Student> q = new BinHeap<Student>();
      Student stdt1 = new Student(1337, "Foo Bar");

      q.insert(stdt1);
      assertEquals(1, q.size());
      assertFalse(q.isEmpty());
      assertTrue(stdt1 == q.deleteMin());
      assertTrue(q.isEmpty());
   }

   @Test
   public void test12_enqueueDequeue1()
   {
      BinHeap<Integer> q = new BinHeap<Integer>();

      q.insert(17);
      q.insert(8);
      q.insert(55);
      q.insert(-7);
      q.insert(39);
      assertEquals(5, q.size());

      assertEquals(new Integer(-7), q.deleteMin());
      assertEquals(new Integer(8), q.deleteMin());
      assertEquals(new Integer(17), q.deleteMin());
      assertEquals(new Integer(39), q.deleteMin());
      assertEquals(new Integer(55), q.deleteMin());
      assertEquals(0, q.size());
   }

   @Test
   public void test13_enqueueDequeue2()
   {
      BinHeap<Integer> q = new BinHeap<Integer>();

      q.insert(0);
      q.insert(0);
      q.insert(0);
      q.insert(0);
      assertEquals(4, q.size());

      assertEquals(new Integer(0), q.deleteMin());
      assertEquals(new Integer(0), q.deleteMin());
      assertEquals(new Integer(0), q.deleteMin());
      assertEquals(new Integer(0), q.deleteMin());
      assertEquals(0, q.size());
   }

   @Test
   public void test14_enqueueDequeue3()
   {
      BinHeap<Integer> q = new BinHeap<Integer>();

      q.insert(-937);

      assertEquals(1, q.size());

      assertEquals(new Integer(-937), q.deleteMin());
      assertEquals(0, q.size());

      q.insert(-999);
      q.insert(33);

      assertEquals(2, q.size());

      q.insert(-1111);

      assertEquals(3, q.size());

      assertEquals(new Integer(-1111), q.deleteMin());
      assertEquals(2, q.size());

      q.insert(-1000);
      assertEquals(3, q.size());

      q.insert(-1001);
      assertEquals(4, q.size());

      assertEquals(new Integer(-1001), q.deleteMin());
      assertEquals(3, q.size());

      assertEquals(new Integer(-1000), q.deleteMin());
      assertEquals(2, q.size());

      assertEquals(new Integer(-999), q.deleteMin());
      assertEquals(1, q.size());

      assertEquals(new Integer(33), q.deleteMin());
      assertEquals(0, q.size());
   }

   @Test
   public void test15_enqueueWithResize1()
   {
      BinHeap<Integer> q = new BinHeap<Integer>(1);

      q.insert(55);
      q.insert(17);
      assertEquals(2, q.size());
      assertEquals(new Integer(17), q.deleteMin());
      assertEquals(new Integer(55), q.deleteMin());
      assertEquals(0, q.size());
   }

   @Test
   public void test16_enqueueWithResize2()
   {
      BinHeap<String> q = new BinHeap<String>(1);

      q.insert("Hello");
      q.insert("World");
      assertEquals(2, q.size());
      assertEquals("Hello", q.deleteMin());
      assertEquals("World", q.deleteMin());
      assertEquals(0, q.size());
   }

   @Test
   public void test17_enqueueWithResize3()
   {
      BinHeap<Student> q = new BinHeap<Student>(1);
      Student stdt1 = new Student(5, "a");
      Student stdt2 = new Student(4, "b");

      q.insert(stdt1);
      q.insert(stdt2);
      assertEquals(2, q.size());
      assertEquals(stdt2, q.deleteMin());
      assertEquals(stdt1, q.deleteMin());
      assertEquals(0, q.size());
   }

   @Test
   public void test18_enqueueWithResize4()
   {
      BinHeap<Integer> q = new BinHeap<Integer>(1);

      q.insert(13);
      q.insert(26);
      q.insert(9);
      q.insert(27);
      q.insert(5);
      q.insert(3);
      q.insert(33);
      q.insert(8);
      q.insert(99);
      assertEquals(9, q.size());

      assertEquals(new Integer(3), q.deleteMin());
      assertEquals(new Integer(5), q.deleteMin());
      assertEquals(new Integer(8), q.deleteMin());
      assertEquals(new Integer(9), q.deleteMin());
      assertEquals(new Integer(13), q.deleteMin());
      assertEquals(new Integer(26), q.deleteMin());
      assertEquals(new Integer(27), q.deleteMin());
      assertEquals(new Integer(33), q.deleteMin());
      assertEquals(new Integer(99), q.deleteMin());
   }

   @Test
   public void test19_toString1()
   {
      BinHeap<Integer> q = new BinHeap<Integer>();

      q.insert(13);
      q.insert(26);
      q.insert(9);
      q.insert(27);
      q.insert(5);
      q.insert(3);
      q.insert(33);
      q.insert(8);
      q.insert(99);
      assertEquals("38592613332799", q.toString().replace(" ", ""));
   }

   @Test
   public void test20_toString2()
   {
      BinHeap<Integer> q = new BinHeap<Integer>();

      q.insert(13);
      q.insert(26);
      q.insert(9);
      q.insert(27);
      q.insert(5);
      q.insert(3);
      q.insert(33);
      q.insert(8);
      q.insert(99);
      q.deleteMin();
      q.deleteMin();
      q.deleteMin();
      assertEquals("92613273399", q.toString().replace(" ", ""));
   }

   @Test(timeout = 80)
   public void test21_enqueueBigOh()
   {
      int size = 5000;
      BinHeap<Integer> q = new BinHeap<Integer>();
      Random rand = new Random(-13555);

      for (int i = 0; i < size; i++)
      {
         q.insert(rand.nextInt());
      }

      assertEquals(size, q.size());
   }

   @Test(timeout = 120)
   public void test22_dequeueBigOh()
   {
      int size = 4000;
      BinHeap<Integer> q = new BinHeap<Integer>();
      Random rand = new Random(17519);

      for (int i = 0; i < size; i++)
      {
         q.insert(rand.nextInt());
      }

      assertEquals(size, q.size());
      
      for (int i = 0; i < size; i++)
      {
         q.deleteMin();
      }

      assertEquals(0, q.size());
   }

   @Test(timeout = 160)
   public void test23_sortBigOh()
   {
      int size = 8000;
      BinHeap<Integer> q = new BinHeap<Integer>();
      int lastVal, curVal;
      Random rand = new Random(-8723);

      for (int i = 0; i < size; i++)
      {
         q.insert(rand.nextInt());
      }

      lastVal = q.deleteMin();
      for (int i = 1; i < size; i++)
      {
         curVal = q.deleteMin();
         assertTrue(lastVal <= curVal);
         lastVal = curVal;
      }
   }

   @Test(timeout = 10000)
   public void test24_runHeapTest()
   {
      try 
      {
         Process proc = Runtime.getRuntime().exec("java HeapTest");
         OutputStreamWriter procIn = new OutputStreamWriter(proc.getOutputStream());
         procIn.write("5\na\n1\nd\ne\ns\np\nx\nq\n", 0, 18);
         procIn.flush();
         proc.waitFor();
         assertEquals(0, proc.exitValue());
      }
      catch (Exception e)
      {
      }
   }

   @Test(timeout = 10000)
   public void test25_runListPrinter()
   {
      try
      {
         PrintWriter inFile = new PrintWriter("in1");
         inFile.printf("00001 Workman\n00423 Siu\n<invalid!> Kuzmich\n" + 
                       "05623 KerngTan\n38753 Levitsky\n82398 Forster\n" +
                       "29327 Nguyen\n90482 Henson\n64202 Hudgins\n67342 Winn\n");
         inFile.close();

         Process proc = Runtime.getRuntime().exec("java ListPrinter");
         OutputStreamWriter procIn = new OutputStreamWriter(proc.getOutputStream());
         Scanner procOut = new Scanner(new BufferedReader(new InputStreamReader(proc.getInputStream())));

         procIn.write("in1\n", 0, 4);
         procIn.flush();
         proc.waitFor();

         while (procOut.hasNextLine())
            assertFalse(procOut.nextLine().contains("Kuzmich"));

         assertEquals(0, proc.exitValue());
      }
      catch (Exception e)
      {
      }
   }
}
