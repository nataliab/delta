/**
 * Generated by API Builder - https://www.apibuilder.io
 * Service version: 0.1.59
 * apibuilder 0.14.3 app.apibuilder.io/flow/common/0.1.59/anorm_2_6_parsers
 */
package io.flow.common.v0.anorm.conversions {

  import anorm.{Column, MetaDataItem, TypeDoesNotMatch}
  import play.api.libs.json.{JsArray, JsObject, JsValue}
  import scala.util.{Failure, Success, Try}
  import play.api.libs.json.JodaReads._

  /**
    * Conversions to collections of objects using JSON.
    */
  object Util {

    def parser[T](
      f: play.api.libs.json.JsValue => T
    ) = anorm.Column.nonNull { (value, meta) =>
      val MetaDataItem(columnName, nullable, clazz) = meta
      value match {
        case json: org.postgresql.util.PGobject => parseJson(f, columnName.qualified, json.getValue)
        case json: java.lang.String => parseJson(f, columnName.qualified, json)
        case _=> {
          Left(
            TypeDoesNotMatch(
              s"Column[${columnName.qualified}] error converting $value to Json. Expected instance of type[org.postgresql.util.PGobject] and not[${value.asInstanceOf[AnyRef].getClass}]"
            )
          )
        }


      }
    }

    private[this] def parseJson[T](f: play.api.libs.json.JsValue => T, columnName: String, value: String) = {
      Try {
        f(
          play.api.libs.json.Json.parse(value)
        )
      } match {
        case Success(result) => Right(result)
        case Failure(ex) => Left(
          TypeDoesNotMatch(
            s"Column[$columnName] error parsing json $value: $ex"
          )
        )
      }
    }

  }

  object Types {
    import io.flow.common.v0.models.json._
    implicit val columnToSeqCommonCalendar: Column[Seq[_root_.io.flow.common.v0.models.Calendar]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Calendar]] }
    implicit val columnToMapCommonCalendar: Column[Map[String, _root_.io.flow.common.v0.models.Calendar]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Calendar]] }
    implicit val columnToSeqCommonCapability: Column[Seq[_root_.io.flow.common.v0.models.Capability]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Capability]] }
    implicit val columnToMapCommonCapability: Column[Map[String, _root_.io.flow.common.v0.models.Capability]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Capability]] }
    implicit val columnToSeqCommonChangeType: Column[Seq[_root_.io.flow.common.v0.models.ChangeType]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.ChangeType]] }
    implicit val columnToMapCommonChangeType: Column[Map[String, _root_.io.flow.common.v0.models.ChangeType]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.ChangeType]] }
    implicit val columnToSeqCommonDeliveredDuty: Column[Seq[_root_.io.flow.common.v0.models.DeliveredDuty]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.DeliveredDuty]] }
    implicit val columnToMapCommonDeliveredDuty: Column[Map[String, _root_.io.flow.common.v0.models.DeliveredDuty]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.DeliveredDuty]] }
    implicit val columnToSeqCommonEnvironment: Column[Seq[_root_.io.flow.common.v0.models.Environment]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Environment]] }
    implicit val columnToMapCommonEnvironment: Column[Map[String, _root_.io.flow.common.v0.models.Environment]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Environment]] }
    implicit val columnToSeqCommonExceptionType: Column[Seq[_root_.io.flow.common.v0.models.ExceptionType]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.ExceptionType]] }
    implicit val columnToMapCommonExceptionType: Column[Map[String, _root_.io.flow.common.v0.models.ExceptionType]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.ExceptionType]] }
    implicit val columnToSeqCommonHolidayCalendar: Column[Seq[_root_.io.flow.common.v0.models.HolidayCalendar]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.HolidayCalendar]] }
    implicit val columnToMapCommonHolidayCalendar: Column[Map[String, _root_.io.flow.common.v0.models.HolidayCalendar]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.HolidayCalendar]] }
    implicit val columnToSeqCommonMarginType: Column[Seq[_root_.io.flow.common.v0.models.MarginType]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.MarginType]] }
    implicit val columnToMapCommonMarginType: Column[Map[String, _root_.io.flow.common.v0.models.MarginType]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.MarginType]] }
    implicit val columnToSeqCommonMeasurementSystem: Column[Seq[_root_.io.flow.common.v0.models.MeasurementSystem]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.MeasurementSystem]] }
    implicit val columnToMapCommonMeasurementSystem: Column[Map[String, _root_.io.flow.common.v0.models.MeasurementSystem]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.MeasurementSystem]] }
    implicit val columnToSeqCommonRole: Column[Seq[_root_.io.flow.common.v0.models.Role]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Role]] }
    implicit val columnToMapCommonRole: Column[Map[String, _root_.io.flow.common.v0.models.Role]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Role]] }
    implicit val columnToSeqCommonRoundingMethod: Column[Seq[_root_.io.flow.common.v0.models.RoundingMethod]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.RoundingMethod]] }
    implicit val columnToMapCommonRoundingMethod: Column[Map[String, _root_.io.flow.common.v0.models.RoundingMethod]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.RoundingMethod]] }
    implicit val columnToSeqCommonRoundingType: Column[Seq[_root_.io.flow.common.v0.models.RoundingType]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.RoundingType]] }
    implicit val columnToMapCommonRoundingType: Column[Map[String, _root_.io.flow.common.v0.models.RoundingType]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.RoundingType]] }
    implicit val columnToSeqCommonScheduleExceptionStatus: Column[Seq[_root_.io.flow.common.v0.models.ScheduleExceptionStatus]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.ScheduleExceptionStatus]] }
    implicit val columnToMapCommonScheduleExceptionStatus: Column[Map[String, _root_.io.flow.common.v0.models.ScheduleExceptionStatus]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.ScheduleExceptionStatus]] }
    implicit val columnToSeqCommonSortDirection: Column[Seq[_root_.io.flow.common.v0.models.SortDirection]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.SortDirection]] }
    implicit val columnToMapCommonSortDirection: Column[Map[String, _root_.io.flow.common.v0.models.SortDirection]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.SortDirection]] }
    implicit val columnToSeqCommonUnitOfMeasurement: Column[Seq[_root_.io.flow.common.v0.models.UnitOfMeasurement]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.UnitOfMeasurement]] }
    implicit val columnToMapCommonUnitOfMeasurement: Column[Map[String, _root_.io.flow.common.v0.models.UnitOfMeasurement]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.UnitOfMeasurement]] }
    implicit val columnToSeqCommonUnitOfTime: Column[Seq[_root_.io.flow.common.v0.models.UnitOfTime]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.UnitOfTime]] }
    implicit val columnToMapCommonUnitOfTime: Column[Map[String, _root_.io.flow.common.v0.models.UnitOfTime]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.UnitOfTime]] }
    implicit val columnToSeqCommonValueAddedService: Column[Seq[_root_.io.flow.common.v0.models.ValueAddedService]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.ValueAddedService]] }
    implicit val columnToMapCommonValueAddedService: Column[Map[String, _root_.io.flow.common.v0.models.ValueAddedService]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.ValueAddedService]] }
    implicit val columnToSeqCommonVisibility: Column[Seq[_root_.io.flow.common.v0.models.Visibility]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Visibility]] }
    implicit val columnToMapCommonVisibility: Column[Map[String, _root_.io.flow.common.v0.models.Visibility]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Visibility]] }
    implicit val columnToSeqCommonAddress: Column[Seq[_root_.io.flow.common.v0.models.Address]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Address]] }
    implicit val columnToMapCommonAddress: Column[Map[String, _root_.io.flow.common.v0.models.Address]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Address]] }
    implicit val columnToSeqCommonContact: Column[Seq[_root_.io.flow.common.v0.models.Contact]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Contact]] }
    implicit val columnToMapCommonContact: Column[Map[String, _root_.io.flow.common.v0.models.Contact]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Contact]] }
    implicit val columnToSeqCommonCustomer: Column[Seq[_root_.io.flow.common.v0.models.Customer]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Customer]] }
    implicit val columnToMapCommonCustomer: Column[Map[String, _root_.io.flow.common.v0.models.Customer]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Customer]] }
    implicit val columnToSeqCommonDatetimeRange: Column[Seq[_root_.io.flow.common.v0.models.DatetimeRange]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.DatetimeRange]] }
    implicit val columnToMapCommonDatetimeRange: Column[Map[String, _root_.io.flow.common.v0.models.DatetimeRange]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.DatetimeRange]] }
    implicit val columnToSeqCommonDimension: Column[Seq[_root_.io.flow.common.v0.models.Dimension]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Dimension]] }
    implicit val columnToMapCommonDimension: Column[Map[String, _root_.io.flow.common.v0.models.Dimension]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Dimension]] }
    implicit val columnToSeqCommonDimensions: Column[Seq[_root_.io.flow.common.v0.models.Dimensions]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Dimensions]] }
    implicit val columnToMapCommonDimensions: Column[Map[String, _root_.io.flow.common.v0.models.Dimensions]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Dimensions]] }
    implicit val columnToSeqCommonError: Column[Seq[_root_.io.flow.common.v0.models.Error]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Error]] }
    implicit val columnToMapCommonError: Column[Map[String, _root_.io.flow.common.v0.models.Error]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Error]] }
    implicit val columnToSeqCommonException: Column[Seq[_root_.io.flow.common.v0.models.Exception]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Exception]] }
    implicit val columnToMapCommonException: Column[Map[String, _root_.io.flow.common.v0.models.Exception]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Exception]] }
    implicit val columnToSeqCommonExperienceSummary: Column[Seq[_root_.io.flow.common.v0.models.ExperienceSummary]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.ExperienceSummary]] }
    implicit val columnToMapCommonExperienceSummary: Column[Map[String, _root_.io.flow.common.v0.models.ExperienceSummary]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.ExperienceSummary]] }
    implicit val columnToSeqCommonHealthcheck: Column[Seq[_root_.io.flow.common.v0.models.Healthcheck]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Healthcheck]] }
    implicit val columnToMapCommonHealthcheck: Column[Map[String, _root_.io.flow.common.v0.models.Healthcheck]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Healthcheck]] }
    implicit val columnToSeqCommonLineItem: Column[Seq[_root_.io.flow.common.v0.models.LineItem]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.LineItem]] }
    implicit val columnToMapCommonLineItem: Column[Map[String, _root_.io.flow.common.v0.models.LineItem]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.LineItem]] }
    implicit val columnToSeqCommonLineItemForm: Column[Seq[_root_.io.flow.common.v0.models.LineItemForm]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.LineItemForm]] }
    implicit val columnToMapCommonLineItemForm: Column[Map[String, _root_.io.flow.common.v0.models.LineItemForm]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.LineItemForm]] }
    implicit val columnToSeqCommonMargin: Column[Seq[_root_.io.flow.common.v0.models.Margin]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Margin]] }
    implicit val columnToMapCommonMargin: Column[Map[String, _root_.io.flow.common.v0.models.Margin]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Margin]] }
    implicit val columnToSeqCommonMeasurement: Column[Seq[_root_.io.flow.common.v0.models.Measurement]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Measurement]] }
    implicit val columnToMapCommonMeasurement: Column[Map[String, _root_.io.flow.common.v0.models.Measurement]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Measurement]] }
    implicit val columnToSeqCommonMoney: Column[Seq[_root_.io.flow.common.v0.models.Money]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Money]] }
    implicit val columnToMapCommonMoney: Column[Map[String, _root_.io.flow.common.v0.models.Money]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Money]] }
    implicit val columnToSeqCommonName: Column[Seq[_root_.io.flow.common.v0.models.Name]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Name]] }
    implicit val columnToMapCommonName: Column[Map[String, _root_.io.flow.common.v0.models.Name]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Name]] }
    implicit val columnToSeqCommonOrganization: Column[Seq[_root_.io.flow.common.v0.models.Organization]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Organization]] }
    implicit val columnToMapCommonOrganization: Column[Map[String, _root_.io.flow.common.v0.models.Organization]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Organization]] }
    implicit val columnToSeqCommonOrganizationReference: Column[Seq[_root_.io.flow.common.v0.models.OrganizationReference]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.OrganizationReference]] }
    implicit val columnToMapCommonOrganizationReference: Column[Map[String, _root_.io.flow.common.v0.models.OrganizationReference]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.OrganizationReference]] }
    implicit val columnToSeqCommonOrganizationSummary: Column[Seq[_root_.io.flow.common.v0.models.OrganizationSummary]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.OrganizationSummary]] }
    implicit val columnToMapCommonOrganizationSummary: Column[Map[String, _root_.io.flow.common.v0.models.OrganizationSummary]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.OrganizationSummary]] }
    implicit val columnToSeqCommonPrice: Column[Seq[_root_.io.flow.common.v0.models.Price]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Price]] }
    implicit val columnToMapCommonPrice: Column[Map[String, _root_.io.flow.common.v0.models.Price]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Price]] }
    implicit val columnToSeqCommonPriceForm: Column[Seq[_root_.io.flow.common.v0.models.PriceForm]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.PriceForm]] }
    implicit val columnToMapCommonPriceForm: Column[Map[String, _root_.io.flow.common.v0.models.PriceForm]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.PriceForm]] }
    implicit val columnToSeqCommonPriceWithBase: Column[Seq[_root_.io.flow.common.v0.models.PriceWithBase]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.PriceWithBase]] }
    implicit val columnToMapCommonPriceWithBase: Column[Map[String, _root_.io.flow.common.v0.models.PriceWithBase]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.PriceWithBase]] }
    implicit val columnToSeqCommonRounding: Column[Seq[_root_.io.flow.common.v0.models.Rounding]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Rounding]] }
    implicit val columnToMapCommonRounding: Column[Map[String, _root_.io.flow.common.v0.models.Rounding]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Rounding]] }
    implicit val columnToSeqCommonSchedule: Column[Seq[_root_.io.flow.common.v0.models.Schedule]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Schedule]] }
    implicit val columnToMapCommonSchedule: Column[Map[String, _root_.io.flow.common.v0.models.Schedule]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Schedule]] }
    implicit val columnToSeqCommonUser: Column[Seq[_root_.io.flow.common.v0.models.User]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.User]] }
    implicit val columnToMapCommonUser: Column[Map[String, _root_.io.flow.common.v0.models.User]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.User]] }
    implicit val columnToSeqCommonUserReference: Column[Seq[_root_.io.flow.common.v0.models.UserReference]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.UserReference]] }
    implicit val columnToMapCommonUserReference: Column[Map[String, _root_.io.flow.common.v0.models.UserReference]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.UserReference]] }
    implicit val columnToSeqCommonZone: Column[Seq[_root_.io.flow.common.v0.models.Zone]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.Zone]] }
    implicit val columnToMapCommonZone: Column[Map[String, _root_.io.flow.common.v0.models.Zone]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.Zone]] }
    implicit val columnToSeqCommonExpandableOrganization: Column[Seq[_root_.io.flow.common.v0.models.ExpandableOrganization]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.ExpandableOrganization]] }
    implicit val columnToMapCommonExpandableOrganization: Column[Map[String, _root_.io.flow.common.v0.models.ExpandableOrganization]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.ExpandableOrganization]] }
    implicit val columnToSeqCommonExpandableUser: Column[Seq[_root_.io.flow.common.v0.models.ExpandableUser]] = Util.parser { _.as[Seq[_root_.io.flow.common.v0.models.ExpandableUser]] }
    implicit val columnToMapCommonExpandableUser: Column[Map[String, _root_.io.flow.common.v0.models.ExpandableUser]] = Util.parser { _.as[Map[String, _root_.io.flow.common.v0.models.ExpandableUser]] }
  }

  object Standard {
    implicit val columnToJsObject: Column[play.api.libs.json.JsObject] = Util.parser { _.as[play.api.libs.json.JsObject] }
    implicit val columnToSeqBoolean: Column[Seq[Boolean]] = Util.parser { _.as[Seq[Boolean]] }
    implicit val columnToMapBoolean: Column[Map[String, Boolean]] = Util.parser { _.as[Map[String, Boolean]] }
    implicit val columnToSeqDouble: Column[Seq[Double]] = Util.parser { _.as[Seq[Double]] }
    implicit val columnToMapDouble: Column[Map[String, Double]] = Util.parser { _.as[Map[String, Double]] }
    implicit val columnToSeqInt: Column[Seq[Int]] = Util.parser { _.as[Seq[Int]] }
    implicit val columnToMapInt: Column[Map[String, Int]] = Util.parser { _.as[Map[String, Int]] }
    implicit val columnToSeqLong: Column[Seq[Long]] = Util.parser { _.as[Seq[Long]] }
    implicit val columnToMapLong: Column[Map[String, Long]] = Util.parser { _.as[Map[String, Long]] }
    implicit val columnToSeqLocalDate: Column[Seq[_root_.org.joda.time.LocalDate]] = Util.parser { _.as[Seq[_root_.org.joda.time.LocalDate]] }
    implicit val columnToMapLocalDate: Column[Map[String, _root_.org.joda.time.LocalDate]] = Util.parser { _.as[Map[String, _root_.org.joda.time.LocalDate]] }
    implicit val columnToSeqDateTime: Column[Seq[_root_.org.joda.time.DateTime]] = Util.parser { _.as[Seq[_root_.org.joda.time.DateTime]] }
    implicit val columnToMapDateTime: Column[Map[String, _root_.org.joda.time.DateTime]] = Util.parser { _.as[Map[String, _root_.org.joda.time.DateTime]] }
    implicit val columnToSeqBigDecimal: Column[Seq[BigDecimal]] = Util.parser { _.as[Seq[BigDecimal]] }
    implicit val columnToMapBigDecimal: Column[Map[String, BigDecimal]] = Util.parser { _.as[Map[String, BigDecimal]] }
    implicit val columnToSeqJsObject: Column[Seq[_root_.play.api.libs.json.JsObject]] = Util.parser { _.as[Seq[_root_.play.api.libs.json.JsObject]] }
    implicit val columnToMapJsObject: Column[Map[String, _root_.play.api.libs.json.JsObject]] = Util.parser { _.as[Map[String, _root_.play.api.libs.json.JsObject]] }
    implicit val columnToSeqJsValue: Column[Seq[_root_.play.api.libs.json.JsValue]] = Util.parser { _.as[Seq[_root_.play.api.libs.json.JsValue]] }
    implicit val columnToMapJsValue: Column[Map[String, _root_.play.api.libs.json.JsValue]] = Util.parser { _.as[Map[String, _root_.play.api.libs.json.JsValue]] }
    implicit val columnToSeqString: Column[Seq[String]] = Util.parser { _.as[Seq[String]] }
    implicit val columnToMapString: Column[Map[String, String]] = Util.parser { _.as[Map[String, String]] }
    implicit val columnToSeqUUID: Column[Seq[_root_.java.util.UUID]] = Util.parser { _.as[Seq[_root_.java.util.UUID]] }
    implicit val columnToMapUUID: Column[Map[String, _root_.java.util.UUID]] = Util.parser { _.as[Map[String, _root_.java.util.UUID]] }
  }

}