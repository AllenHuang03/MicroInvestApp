package com.microinvest.app.domain.usecase

import com.microinvest.app.domain.model.Investment
import com.microinvest.app.domain.model.InvestmentSource
import com.microinvest.app.domain.model.InvestmentType
import com.microinvest.app.domain.repository.InvestmentRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InvestmentUseCasesTest {

    @Mock
    private lateinit var investmentRepository: InvestmentRepository

    private lateinit var investSpareChangeUseCase: InvestSpareChangeUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        investSpareChangeUseCase = InvestSpareChangeUseCase(investmentRepository)
    }

    @Test
    fun `investSpareChange should return success when repository succeeds`() = runTest {
        // Given
        val amount = BigDecimal("10.50")
        val symbol = "SPY"
        val expectedInvestment = Investment(
            id = "test-id",
            userId = "user-123",
            amount = amount,
            symbol = symbol,
            shares = BigDecimal("0.25"),
            pricePerShare = BigDecimal("420.00"),
            type = InvestmentType.ETF,
            timestamp = System.currentTimeMillis(),
            transactionId = "txn-123",
            source = InvestmentSource.SPARE_CHANGE
        )

        whenever(investmentRepository.investSpareChange(amount, symbol))
            .thenReturn(Result.success(expectedInvestment))

        // When
        val result = investSpareChangeUseCase(amount, symbol)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedInvestment, result.getOrNull())
        verify(investmentRepository).investSpareChange(amount, symbol)
    }

    @Test
    fun `investSpareChange should return failure when repository fails`() = runTest {
        // Given
        val amount = BigDecimal("10.50")
        val symbol = "SPY"
        val exception = RuntimeException("Investment failed")

        whenever(investmentRepository.investSpareChange(amount, symbol))
            .thenReturn(Result.failure(exception))

        // When
        val result = investSpareChangeUseCase(amount, symbol)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        verify(investmentRepository).investSpareChange(amount, symbol)
    }
}