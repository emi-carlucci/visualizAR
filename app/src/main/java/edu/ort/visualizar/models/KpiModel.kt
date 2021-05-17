package edu.ort.visualizar.models

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
)

data class Category (
        var value: List<String>?
)

data class DateModified (
        var type: String?,
        var value: String?
)

data class CalculationFrequency (
        var value: String?
)

data class Description (
        var value: String?
)

data class CurrentStanding (
        var value: String?
)

data class Address (
        var type: String?,
        var value: AddressValue?
)

data class AddressValue (
        var addressLocality: String?,
        var addressCountry: String?
)

data class CalculationPeriod (
        var value: CalculationPeriodValue?
)

data class CalculationPeriodValue (
        var to: String?,
        var from: String?
)

data class DateNextCalculation (
        var type: String?,
        var value: String?
)

data class CalculationMethod (
        var value: String?
)

data class Provider (
        var value: ProviderValue?
)

data class ProviderValue (
        var name: String?
)

data class Organization (
        var value: OrganizationValue?
)

data class OrganizationValue (
        var name: String?
)

data class KpiValue (
        var value: String?
)

data class Name (
        var value: String?
)

data class Source (
        var value: String?
)

data class Process (
        var value: String?
)

data class BusinessTarget (
        var value: String?
)

data class CalculationFormula (
        var value: String?
)

data class DateCreated (
        var value: String?
)

data class DateExpires (
        var value: String?
)

data class UpdatedAt (
        var value: String?
)

data class Area (
        var value: String?
)



















