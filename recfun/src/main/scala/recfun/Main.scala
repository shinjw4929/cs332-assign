package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if(r == 0 || r == c || c == 0){
      1
    }
    else{
      pascal(c, r - 1) + pascal(c - 1, r - 1)
    }
  }
  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def inner(chars: List[Char], open : Int, close : Int) : Boolean = {
      if(chars.isEmpty)
        open == close && (open > 0)
      else if(close > open)
        false
      else if(chars.head == '(')
        inner(chars.tail, open + 1, close)
      else if(chars.head == ')')
        inner(chars.tail, open, close + 1)
      else
        inner(chars.tail, open, close)
    }
    inner(chars, 0, 0);
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if(money == 0)
      1
    else if(money > 0 && coins.isEmpty)
      0
    else if(money < 0)
      0
    else {
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
    }
  }

}
