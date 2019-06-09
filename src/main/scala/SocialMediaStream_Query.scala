import com.datastax.spark.connector._
import com.datastax.spark.connector.rdd.CassandraTableScanRDD
import com.typesafe.config.ConfigFactory
import org.apache.spark.{SparkConf, SparkContext}
import scala.collection.JavaConverters._

class SocialMediaStream_Query extends App {
  val conf = ConfigFactory.load()
  val sparkConf = new SparkConf(true)
    .set("spark.cassandra.connection.host", conf.getString("cassandra.host"))
    .set("spark.cassandra.auth.username", conf.getString("cassandra.username"))
    .set("spark.cassandra.auth.password", conf.getString("cassandra.password"))

  val sc = new SparkContext(conf.getString("spark.master"), "SocialMediaStream_Query", sparkConf)

  val rdd: CassandraTableScanRDD[CassandraRow] = sc.cassandraTable("social", "posts")
  var keywords = conf.getStringList("keywords").asScala.toList
  val stopwords = conf.getStringList("stopwords").asScala.toList
  // Remove stopwords from expressions
  keywords = keywords.map(kw => {
    stopwords.foldLeft(kw)((kw, stop) => kw.replaceAll(stop, ""))
  })
  var mentions = scala.collection.mutable.Map[String, Int]()
  keywords.foreach(kw => mentions += (kw -> 0))

  // Count mentions for each game
  keywords.foreach(kw => {
    rdd.foreach(postRaw => {
      val post = postRaw.getString("post")
      if(post.contains(kw)) { mentions(kw) = mentions(kw) + 1 }
    })
  })
  // Print results
  println("The mentions for each keyword are: ")
  mentions.foreach(t => println(s"${t._1}: ${t._2} times"))
}
