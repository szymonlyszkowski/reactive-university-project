package model

/**
  * @author alisowsk
  */
case class Result(
           indexName: String,
           companyResults: Seq[IndexCalculationResult]
           )

case class IndexCalculationResult(
           companyName: String,
           indexResult: Double
           )
