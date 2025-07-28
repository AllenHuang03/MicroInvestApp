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
public final class BudgetRepository_Factory implements Factory<BudgetRepository> {
  private final Provider<BudgetDao> budgetDaoProvider;

  private final Provider<ExpenseDao> expenseDaoProvider;

  public BudgetRepository_Factory(Provider<BudgetDao> budgetDaoProvider,
      Provider<ExpenseDao> expenseDaoProvider) {
    this.budgetDaoProvider = budgetDaoProvider;
    this.expenseDaoProvider = expenseDaoProvider;
  }

  @Override
  public BudgetRepository get() {
    return newInstance(budgetDaoProvider.get(), expenseDaoProvider.get());
  }

  public static BudgetRepository_Factory create(Provider<BudgetDao> budgetDaoProvider,
      Provider<ExpenseDao> expenseDaoProvider) {
    return new BudgetRepository_Factory(budgetDaoProvider, expenseDaoProvider);
  }

  public static BudgetRepository newInstance(BudgetDao budgetDao, ExpenseDao expenseDao) {
    return new BudgetRepository(budgetDao, expenseDao);
  }
}
