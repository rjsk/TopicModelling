import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.scalatest.Assertions._
import org.scalatest.FunSuite
import scala.collection.mutable.Stack
import org.scalatest.BeforeAndAfter

class StopWordsHelperTest extends FunSuite with BeforeAndAfter{

  private val master = "local[2]"
  private val appName = "StopWordsTest"

  private var sc: SparkContext = _

  before {
    val conf = new SparkConf()
      .setMaster(master)
      .setAppName(appName)

    sc = new SparkContext(conf)

  }

  test("it should contain list of stopwords") {
    var stopWords = StopWordsHelper.getStopWords()
    assert(stopWords.size > 0)
  }

  test("it should remove stopwords from passed dataset") {
    val sparkSession = SparkSession.builder.getOrCreate()
    import sparkSession.implicits._
    
    val words = Seq("i", "am", "a", "president", "donald", "trump").toDS()
    val result = StopWordsHelper.removeStopWords(words)
    assert(result.count() == 1)
  }

  after {
    if (sc != null) {
      sc.stop()
    }
  }
}