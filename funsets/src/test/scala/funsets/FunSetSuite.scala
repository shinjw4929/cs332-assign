package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      printSet(s)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }


  test("test intersect") {
    new TestSets {
      val union12 = union(s1, s2)
      val union23 = union(s2, s3)
      val inter12_23 = intersect(union12, union23)
      printSet(inter12_23)
      assert(!contains(inter12_23, 1), "Intersect 1")
      assert(contains(inter12_23, 2), "Intersect 2")
      assert(!contains(inter12_23, 3), "Intersect 3")
    }
  }


  test("test diff") {
    new TestSets {
      val union12 = union(s1, s2)
      val union23 = union(s2, s3)
      val diff12_23 = diff(union12, union23)
      printSet(diff12_23)
      assert(contains(diff12_23, 1), "Diff 1")
      assert(!contains(diff12_23, 2), "Diff 2")
      assert(!contains(diff12_23, 3), "Diff 3")
    }
  }

  test("test filter") {
    new TestSets {
      val union12 = union(s1, s2)
      val union23 = union(s2, s3)
      val union123 = union(union12, union23)
      val filter123 = filter(union123, x => (x % 2) == 1)
      printSet(filter123)
      assert(contains(filter123, 1), "Filter 1")
      assert(!contains(filter123, 2), "Filter 2")
      assert(contains(filter123, 3), "Filter 3")
    }
  }

  test("test forall") {
    new TestSets {
      val union13 = union(s1, s3)
      val forall123 = forall(union13, x => (x % 2) == 1)
      assert(forall123, "Forall")
    }
  }

  test("test exists") {
    new TestSets {
      val union12 = union(s1, s2)
      val union23 = union(s2, s3)
      val union123 = union(union12, union23)
      val exists123 = exists(union123, x => (x % 2) == 1)
      assert(exists123, "Exists")
    }
  }

  test("test map") {
    new TestSets {
      val union12 = union(s1, s2)
      val union23 = union(s2, s3)
      val union123 = union(union12, union23)
      val map123 = map(union123, x => x * x)
      printSet(map123)
      assert(contains(map123, 1), "Map 1")
      assert(!contains(map123, 2), "Map 2")
      assert(!contains(map123, 3), "Map 3")
      assert(contains(map123, 4), "Map 4")
      assert(contains(map123, 9), "Map 9")
    }
  }

}
