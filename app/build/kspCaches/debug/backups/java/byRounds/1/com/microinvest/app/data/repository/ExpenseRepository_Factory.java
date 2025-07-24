package com.microinvest.app.data.repository;

import com.microinvest.app.data.local.dao.BudgetDao;
import com.microinvest.app.data.local.dao.ExpenseDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class ExpenseRepository_Factory implements Factory<ExpenseRepository> {
  private final Provider<ExpenseDao> expenseDaoProvider;

  private final Provider<BudgetDao> budgetDaoProvider;

  public ExpenseRepository_Factory(Provider<ExpenseDao> expenseDaoProvider,
      Provider<BudgetDao> budgetDaoProvider) {
    this.expenseDaoProvider = expenseDaoProvider;
    this.budgetDaoProvider = budgetDaoProvider;
  }

  @Override
  public ExpenseRepository get() {
    return newInstance(expenseDaoProvider.get(), budgetDaoProvider.get());
  }

  public static ExpenseRepository_Factory create(Provider<ExpenseDao> expenseDaoProvider,
      Provider<BudgetDao> budgetDaoProvider) {
    return new ExpenseRepository_Factory(expenseDaoProvider, budgetDaoProvider);
  }

  public static ExpenseRepository newInstance(ExpenseDao expenseDao, BudgetDao budgetDao) {
    return new ExpenseRepository(expenseDao, budgetDao);
  }
}
