package com.microinvest.app.data.repository;

import com.microinvest.app.data.local.dao.InvestmentDao;
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
public final class InvestmentRepository_Factory implements Factory<InvestmentRepository> {
  private final Provider<InvestmentDao> investmentDaoProvider;

  public InvestmentRepository_Factory(Provider<InvestmentDao> investmentDaoProvider) {
    this.investmentDaoProvider = investmentDaoProvider;
  }

  @Override
  public InvestmentRepository get() {
    return newInstance(investmentDaoProvider.get());
  }

  public static InvestmentRepository_Factory create(Provider<InvestmentDao> investmentDaoProvider) {
    return new InvestmentRepository_Factory(investmentDaoProvider);
  }

  public static InvestmentRepository newInstance(InvestmentDao investmentDao) {
    return new InvestmentRepository(investmentDao);
  }
}
