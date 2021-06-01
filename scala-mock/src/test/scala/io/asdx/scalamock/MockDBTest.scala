package io.asdx.scalamock

import java.io.File
import java.sql.{Connection, DriverManager, Statement}

import org.scalatest.BeforeAndAfterAll
import org.scalatest.funspec.AnyFunSpec

import scala.reflect.io.Directory


/**
 * @auther: liansheng
 * @Date: 2021/4/7 14:14
 * @Description:
 */
class MockDBTest extends AnyFunSpec with BeforeAndAfterAll{

  val PARENT_DIR: String = "./data-dir"
  val DATABASE_NAME: String = "my-h2-db"
  val DATABASE_DIR: String = s"$PARENT_DIR/$DATABASE_NAME"
  val DATABASE_URL: String = s"jdbc:h2:$DATABASE_DIR"

  val con: Connection = DriverManager.getConnection(DATABASE_URL)
  val stm: Statement = con.createStatement

  override def afterAll() {
    jdbcDisconnect()
    clearDatabaseDataDir()
  }

  it("embedded H2 database example") {
    var count = 0
    val rs = stm.executeQuery("SELECT 1+1")
    try {
      if (rs.next) {
        count = rs.getInt(1)
      }
    }
    catch {
      case ex: Exception => ex.printStackTrace()

    } finally {
      if (rs != null) rs.close()
    }

    assert(count == 2)
  }

  it("embedded H2 database create table") {
    /**
     * By default table & column names will get capitalized in H2 DB if no quotes are provided,
     * Relevant setting is DATABASE_TO_UPPER inside [[org.h2.engine.DbSettings]] databaseToUpper()
     **/
    var isTableCreated = false
    val sql: String =
      """create table TEST1(ID INT PRIMARY KEY,NAME VARCHAR(500));"""
    try {
      stm.execute(sql)
      val rs = stm.executeQuery(
        """
          |select count(*) as count_of_table
          |from information_schema.tables
          |where table_type='TABLE' and table_name = 'TEST1'""".stripMargin)
      try {
        if (rs.next) {
          isTableCreated = 1 == rs.getInt("count_of_table")
        }
      }
      catch {
        case ex: Exception =>
          println(ex.getMessage)
      } finally {
        if (rs != null) rs.close()
      }
    }
    catch {
      case ex: Exception =>
        println("Unable to create the table")
        println(ex.getMessage)
    }
    assert(isTableCreated)
  }

  it("embedded H2 database create table with insert") {
    var row1Insertion = false
    var row2Insertion = false
    var row3Insertion = false

    try {
      val sql: String =
        """
          |create table test2(ID INT PRIMARY KEY,NAME VARCHAR(500));
          |insert into test2 values (1,'A');
          |insert into test2 values (2,'B');
          |insert into test2 values (3,'C');""".stripMargin
      stm.execute(sql)
      val rs = stm.executeQuery("""select * from test2""")
      try {
        if (rs.next) {
          row1Insertion = (1 == rs.getInt("ID")) && ("A" == rs.getString("NAME"))

          rs.next
          row2Insertion = (2 == rs.getInt("ID")) && ("B" == rs.getString("NAME"))

          rs.next
          row3Insertion = (3 == rs.getInt("ID")) && ("C" == rs.getString("NAME"))
        }
      }
      catch {
        case ex: Exception => ex.printStackTrace()
      } finally {
        if (rs != null) rs.close()
      }
    }
    catch {
      case e: Exception => println(e.getMessage)
    }
    assert(row1Insertion && row2Insertion && row3Insertion, "Data not inserted")
  }

  it("embedded H2 database create table and then drop it") {
    var isTableCreated: Boolean = false
    var isTableDropped: Boolean = false

    try {
      val sql: String =
        """ create table test3(ID INT PRIMARY KEY,
          |NAME VARCHAR(500));""".stripMargin
      stm.execute(sql)
      val rs = stm.executeQuery("""select count(*) as count_of_table from information_schema.tables where table_name = 'TEST3'""")
      try {
        if (rs.next) {
          isTableCreated = 1 == rs.getInt("count_of_table")
        }
      }
      catch {
        case ex: Exception => println(ex.getMessage)
      } finally {
        if (rs != null) rs.close()
      }

      stm.execute("drop table test3")
      val rs2 = stm.executeQuery("""select count(*) as count_of_table from information_schema.tables where table_name = 'TEST3'""")
      try {
        if (rs2.next) {
          isTableDropped = 0 == rs2.getInt("count_of_table")
        }
      }
      catch {
        case ex: Exception => println(ex.getMessage)
      } finally {
        if (rs2 != null) rs2.close()
      }

    }
    catch {
      case e: Exception => println(e.getMessage)
    }
    assert(isTableCreated)
    assert(isTableDropped)
  }

  private def clearDatabaseDataDir(): Unit = {
    new Directory(new File(PARENT_DIR)).deleteRecursively()
  }

  private def jdbcDisconnect(): Unit = {
    if (con != null) con.close()
    if (stm != null) stm.close()
  }

}
