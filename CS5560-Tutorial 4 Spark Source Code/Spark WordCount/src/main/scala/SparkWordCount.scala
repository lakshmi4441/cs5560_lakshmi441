

import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Mayanka on 09-Sep-15.
 */
object SparkWordCount {

  def main(args: Array[String]) {

    //System.setProperty("hadoop.home.dir","D:\\winutils");

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc=new SparkContext(sparkConf)
     //Input : 10 abstracts Folder
     //Output : Word Count of all the 10 abstracts

    val inputf =sc.wholeTextFiles("abstracts",minPartitions=10)
    //Example on how to refer within wholeTextFiles
    inputf.flatMap( abs=> {
      abs._1 // File Path
     abs._2 // Fle Content
    })


    //val input=sc.textFile("input")

    val wc=inputf.flatMap(abs=>{
     abs._2.split(" ")}).map(word=>(word,1)).cache()

    val output=wc.reduceByKey(_+_)

    output.saveAsTextFile("output")

    val o=output.collect()

    var s:String="Words:Count \n"
    o.foreach{case(word,count)=>{

      s+=word+" : "+count+"\n"

    }}

  }

}
