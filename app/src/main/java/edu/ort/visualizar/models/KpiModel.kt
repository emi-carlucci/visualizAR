package edu.ort.visualizar.models

data class KpiModel (
        var id: String?,
        var type: String?,
        var category: Category?,
        var dateModified: DateModified?,
        var calculationFrequency: CalculationFrequency?,
        var descritpion: Description?,
        var currentStanding: CurrentStanding?,
        var address: Address?,
        var calculationPeriod: CalculationPeriod?,
        var dateNextCalculation: DateNextCalculation?,
        var calculationMethod: CalculationMethod?,
        var provider: Provider?,
        var organization: Organization?,
        var kpiValue: KpiValue?,
        var name: Name?
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








