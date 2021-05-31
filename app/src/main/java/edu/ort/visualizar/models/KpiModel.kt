package edu.ort.visualizar.models

import java.io.Serializable


data class KpiModel (
        var id: String? = null,
        var type: String? = null,
        var category: Category? = null,
        var dateModified: DateModified? = null,
        var calculationFrequency: CalculationFrequency? = null,
        var description: Description? = null,
        var currentStanding: CurrentStanding? = null,
        var address: Address? = null,
        var calculationPeriod: CalculationPeriod? = null,
        var dateNextCalculation: DateNextCalculation? = null,
        var calculationMethod: CalculationMethod? = null,
        var provider: Provider? = null,
        var organization: Organization? = null,
        var kpiValue: KpiValue? = null,
        var name: Name? = null,
        var source: Source? = null,
        var process: Process? = null,
        var businessTarget: BusinessTarget? = null,
        var calculationFormula: CalculationFormula? = null,
        var dateCreated: DateCreated? = null,
        var dateExpires: DateExpires? = null,
        var updatedAt: UpdatedAt? = null,
        var area: Area? = null
): Serializable

data class Category (
        var value: List<String>?
): Serializable

data class DateModified (
        var type: String?,
        var value: String?
): Serializable

data class CalculationFrequency (
        var value: String?
): Serializable

data class Description (
        var value: String?
): Serializable

data class CurrentStanding (
        var value: String?
): Serializable

data class Address (
        var type: String?,
        var value: AddressValue?
): Serializable

data class AddressValue (
        var addressLocality: String?,
        var addressCountry: String?
): Serializable

data class CalculationPeriod (
        var value: CalculationPeriodValue?
): Serializable

data class CalculationPeriodValue (
        var to: String?,
        var from: String?
): Serializable

data class DateNextCalculation (
        var type: String?,
        var value: String?
): Serializable

data class CalculationMethod (
        var value: String?
): Serializable

data class Provider (
        var value: ProviderValue?
): Serializable

data class ProviderValue (
        var name: String?
): Serializable

data class Organization (
        var value: OrganizationValue?
): Serializable

data class OrganizationValue (
        var name: String?
): Serializable

data class KpiValue (
        var value: Double?
): Serializable

data class Name (
        var value: String?
): Serializable

data class Source (
        var value: String?
): Serializable

data class Process (
        var value: String?
): Serializable

data class BusinessTarget (
        var value: String?
): Serializable

data class CalculationFormula (
        var value: String?
): Serializable

data class DateCreated (
        var value: String?
): Serializable

data class DateExpires (
        var type: String?,
        var value: String?
): Serializable

data class UpdatedAt (
        var value: String?
): Serializable

data class Area (
        var value: String?
): Serializable



















