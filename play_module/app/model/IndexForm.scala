package model
import java.util.Date
import play.api.data.Form
import play.api.data.Forms._
/**
  * Created by Aga on 2016-06-13.
  */
case class IndexFormData(
                          indexName: String,
                          companyNames: Seq[String],
                          start_date: Date,
                          end_date: Date
                        )

object IndexForm {
  val form = Form(
    mapping(
      "index_name" -> nonEmptyText,
      "company_names" -> seq(nonEmptyText),
      "start_date" -> date,
      "end_date" -> date
    )(IndexFormData.apply)(IndexFormData.unapply)
  )
}