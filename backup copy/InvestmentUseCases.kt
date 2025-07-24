package com.microinvest.app.domain.usecase

import com.microinvest.app.domain.model.Investment
import com.microinvest.app.domain.model.Portfolio
import com.microinvest.app.domain.model.SpareChangeTransaction
import com.microinvest.app.domain.repository.InvestmentRepository
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import javax.inject.Inject

class InvestSpareChangeUseCase @Inject constructor(
    private val investmentRepository: InvestmentRepository
) {
    suspend operator fun invoke(amount: BigDecimal, symbol: String): Result<Investment> {
        return investmentRepository.investSpareChange(amount, symbol)
    }
}

class GetPortfolioUseCase @Inject constructor(
    private val investmentRepository: InvestmentRepository
) {
    operator fun invoke(): Flow<Portfolio> {
        return investmentRepository.getPortfolio()
    }
}

class GetInvestmentHistoryUseCase @Inject constructor(
    private val investmentRepository: InvestmentRepository
) {
    operator fun invoke(): Flow<List<Investment>> {
        return investmentRepository.getInvestmentHistory()
    }
}

class ProcessSpareChangeUseCase @Inject constructor(
    private val investmentRepository: InvestmentRepository
) {
    suspend operator fun invoke(transaction: SpareChangeTransaction): Result<Unit> {
        return investmentRepository.processSpareChangeTransaction(transaction)
    }
}

class GetSpareChangeTransactionsUseCase @Inject constructor(
    private val investmentRepository: InvestmentRepository
) {
    operator fun invoke(): Flow<List<SpareChangeTransaction>> {
        return investmentRepository.getSpareChangeTransactions()
    }
}