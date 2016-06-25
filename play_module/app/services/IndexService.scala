package services

import java.util.Date

import actors.StockIndexActor
import model.IndexName.IndexName
import model.{IndexName, Quotation, Result}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
  * @author alisowsk
  */
object IndexService {

  def runIndex(indexName: IndexName, companyNames: Seq[String], start_date: Date, end_date: Date): Future[Result] = indexName match {
    case IndexName.TEST => calculateTest(companyNames, start_date, end_date)

    case IndexName.AVERAGE_TRUE_RANGE => calculateAverageTrueRange(indexName, companyNames, start_date, end_date)

    case IndexName.EASE_OF_MOVEMENT => calculateEaseOfMovement(indexName, companyNames, start_date, end_date)

    case IndexName.MOVING_AVERAGE => calculateMovingAverage(indexName, companyNames, start_date, end_date)

    case default => Future.successful(null)
  }

  private def calculateTest(companyNames: Seq[String], dateFrom: Date, dateTo: Date): Future[Result] = {
    val quotation: Future[Seq[Quotation]] = QuotationService.getByCompanyName(companyNames.head)
    val quotations = QuotationService.getByCompanyNames(companyNames)

    val valuesSingleQuotation = Await.ready(quotation, Duration.Inf).value.get.get.map($ => $.closing toDouble).toList
    val valuesMultipleQuotations = Await.ready(quotations, Duration.Inf).value.get.get.map($ => $.closing toDouble).toList
    val valuesMultipleQuotationsByDate = Await.ready(quotations, Duration.Inf).value.get.get
      .filter(q => (q.date.after(dateFrom) && q.date.before(dateTo)) || q.date.eq(dateFrom) || q.date.equals(dateTo)).map($ => $.closing toDouble).toList

    Future.successful(null)
  }

  private def calculateAverageTrueRange(indexName: IndexName, companyNames: Seq[String], dateFrom: Date, dateTo: Date): Future[Result] = {

    StockIndexActor.calculateUsingActorsWithWorkersAmountOf(workersAmount = 4, indexName, companyNames, dateFrom, dateTo)

  }

  private def calculateEaseOfMovement(indexName: IndexName, companyNames: Seq[String], dateFrom: Date, dateTo: Date): Future[Result] = {

    StockIndexActor.calculateUsingActorsWithWorkersAmountOf(workersAmount = 4, indexName, companyNames, dateFrom, dateTo)

  }

  private def calculateMovingAverage(indexName: IndexName, companyNames: Seq[String], dateFrom: Date, dateTo: Date): Future[Result] = {

    StockIndexActor.calculateUsingActorsWithWorkersAmountOf(workersAmount = 4, indexName, companyNames, dateFrom, dateTo)

    //TODO calculateMovingAveragesOnActorSystem(QuotationService.listAllQuotations , dateFrom, dateTo)
    //TODO invoke calculateMovingAveragesOnActorSystem(companiesData: List[Quotation], dateFrom: java.util.Date, dateTo: java.util.Date)
    //from actor system  - will return list of values for date interval for given stock
    //TODO calculateAverageFromMovingAveragesForIndex() - to powinno zwrocic wynik

  }

}
